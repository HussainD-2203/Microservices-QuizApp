package com.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.entity.Question;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.repository.QuestionRepository;

import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepo;

	// Get All Questions
	public List<Question> getAllQuestions() {
		List<Question> questions = questionRepo.findAll();
		if (questions.isEmpty()) {
			return null;
		}
		return questions;
	}

	// Get Question by category
	public List<Question> getByCategory(String category) {
		List<Question> questions = questionRepo.findByCategory(category);
		if (questions.isEmpty()) {
			return null;
		}
		return questions;
	}

	// Add question
	public Question addQuestion(Question body) {
		Question question = questionRepo.save(body);
		return question;
	}

	//Method to add or update
	@Transactional
	public Question addOrUpdateQuestion(Question body) {
		if (body == null) {
	        return null;
	    }
		
		// Save
		if (body.getQuestionId() == null || body.getQuestionId() == 0) { 
			Question question = questionRepo.save(body);
			return question;
		} 
		// Update
		else { 
			Question existingQuestion = questionRepo.findById(body.getQuestionId())
					.orElseThrow(() -> new ResourceNotFoundException("Invalid question ID"));
			existingQuestion.setQuestionTitle(body.getQuestionTitle());
			existingQuestion.setOption1(body.getOption1());
			existingQuestion.setOption2(body.getOption2());
			existingQuestion.setOption3(body.getOption3());
			existingQuestion.setOption4(body.getOption4());
			existingQuestion.setCategory(body.getCategory());
			existingQuestion.setDifficultyLevel(body.getDifficultyLevel());
			Question updated = questionRepo.save(existingQuestion);
			return updated;
		}
	}

}
