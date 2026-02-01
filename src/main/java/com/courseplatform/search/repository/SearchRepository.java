package com.courseplatform.search.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> search(String query) {

        String sql = """
            SELECT 
                c.id           AS course_id,
                c.title        AS course_title,
                t.title        AS topic_title,
                s.id           AS subtopic_id,
                s.title        AS subtopic_title,
                s.content      AS content
            FROM courses c
            JOIN topics t ON t.course_id = c.id
            JOIN subtopics s ON s.topic_id = t.id
            WHERE
                LOWER(c.title) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(c.description) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(s.title) LIKE LOWER(CONCAT('%', :q, '%'))
                OR LOWER(s.content) LIKE LOWER(CONCAT('%', :q, '%'))
        """;

        return entityManager
                .createNativeQuery(sql)
                .setParameter("q", query)
                .getResultList();
    }
}
