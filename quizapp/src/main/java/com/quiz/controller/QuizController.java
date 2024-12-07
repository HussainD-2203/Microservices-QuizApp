package com.quiz.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.payload.ErrorResponse;
import com.quiz.payload.QuestionWrapper;
import com.quiz.payload.Result;
import com.quiz.service.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	//Test Api
	@GetMapping("/test")
	public String helloworld() {
		return "hello World";
	}
	
	//Create Quiz
	@PostMapping("/create")
	public ResponseEntity<Object> createQuiz(@RequestParam String category
			, @RequestParam int numQ, @RequestParam String title){
		
		Quiz quiz = quizService.createQuiz(category,numQ,title); 
		
		if(quiz==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
					"Quiz creation failed ",HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(quiz);
	}
	
	
	//Get All Quiz
	@GetMapping("/getAll")
	public ResponseEntity<Object> getALlQuiz(){
		List<Quiz> quizzes = quizService.getAllQuiz(); 
		if(quizzes==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
					"Data Not Found",HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(quizzes);
	}
	
	//Get Quiz by id
	@PostMapping("/getById")
	public ResponseEntity<Object> getById(@RequestBody Quiz body){
		Quiz quiz = quizService.getbyId(body);	
		if(quiz==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
					"Data Not Found",HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(quiz);
	}
	
	//Get Questions only from Quiz
	@PostMapping("/getQuizQuestion")
	public ResponseEntity<Object> getQuizQuestion(@RequestBody Quiz body){
		List <QuestionWrapper> questions = quizService.getQuizQuestion(body);
		if(questions.isEmpty()) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
					"Data Not Found",HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(questions);
	}
	
	//Submit APi for Quiz answers
	@PostMapping("/submit/{quizId}")
	public ResponseEntity<Object> getResultOfQuiz(@PathVariable Integer quizId, @RequestBody List<Question> body){
		Result result = quizService.getResultOfQuiz(quizId,body);
		if(result==null) {
			ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
					"Data Not Found",HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
}
