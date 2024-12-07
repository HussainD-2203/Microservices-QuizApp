package com.quiz.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entity.Question;
import com.quiz.payload.ErrorResponse;
import com.quiz.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	QuestionService questionservice;
	
	//get all questions
	@GetMapping("/allquestions")
	public ResponseEntity<Object> getAllQuestion(){
		List<Question> questions = questionservice.getAllQuestions();
		if(questions==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),"Data Not Found",HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(questions);
	}
	
	// get questions by category
	@GetMapping("/questionByCategory")
	public ResponseEntity<Object> getByCategory(@RequestBody Question body){
		List<Question> questions = questionservice.getByCategory(body.getCategory());
		if(questions==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),"Enter valid category",HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
		}		
		return ResponseEntity.status(HttpStatus.OK).body(questions);		
	}
	
	//Add question
	@PostMapping("/addOrUpdateQuestion")
	public ResponseEntity<Object> addQuestion(@Valid @RequestBody Question body){
		Question question = questionservice.addOrUpdateQuestion(body);
		if(question==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),"Question addition failed",HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(question);
	}
	
}
