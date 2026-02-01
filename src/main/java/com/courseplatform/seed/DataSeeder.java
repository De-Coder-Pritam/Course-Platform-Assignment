package com.courseplatform.seed;

import com.courseplatform.course.entity.Course;
import com.courseplatform.course.entity.Topic;
import com.courseplatform.course.entity.Subtopic;
import com.courseplatform.course.repository.CourseRepository;
import com.courseplatform.seed.dto.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        // ✅ Idempotency check
        if (courseRepository.count() > 0) {
            System.out.println("Seed data already exists. Skipping.");
            return;
        }

        System.out.println("Loading seed data...");

        InputStream inputStream =
                new ClassPathResource("data/courses.json").getInputStream();

        CoursesSeedWrapper wrapper =
                objectMapper.readValue(inputStream, CoursesSeedWrapper.class);

        for (CourseSeedDto courseDto : wrapper.getCourses()) {

            Course course = new Course();
            course.setId(courseDto.getId());
            course.setTitle(courseDto.getTitle());
            course.setDescription(courseDto.getDescription());

            for (TopicSeedDto topicDto : courseDto.getTopics()) {

                Topic topic = new Topic();
                topic.setId(topicDto.getId());
                topic.setTitle(topicDto.getTitle());
                topic.setCourse(course);

                for (SubtopicSeedDto subDto : topicDto.getSubtopics()) {

                    Subtopic subtopic = new Subtopic();
                    subtopic.setId(subDto.getId());
                    subtopic.setTitle(subDto.getTitle());
                    subtopic.setContent(subDto.getContent());
                    subtopic.setTopic(topic);

                    topic.getSubtopics().add(subtopic);
                }

                course.getTopics().add(topic);
            }

            courseRepository.save(course);
        }

        System.out.println("Seed data loaded successfully.");
    }
}
