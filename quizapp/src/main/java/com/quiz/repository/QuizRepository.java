package com.quiz.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{

	public Quiz findByTitle(String title);
	
}
