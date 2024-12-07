package com.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quiz.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	public List<Question> findByCategory(String category);

	public Optional<Question> findByQuestionTitle(String questionTitle);
	
	@Query(value = "SELECT * FROM question WHERE category = :category ORDER BY RANDOM() LIMIT " +":numQ", nativeQuery = true)
	public List<Question> findByCategory(@Param("category") String category, @Param("numQ") int numQ);


}
