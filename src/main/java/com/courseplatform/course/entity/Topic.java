package com.courseplatform.course.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @Column(length = 100)
    private String id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(
            mappedBy = "topic",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Subtopic> subtopics = new ArrayList<>();
}

