package com.quiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.exception.ResourceNotFoundException;
import com.quiz.payload.QuestionWrapper;
import com.quiz.payload.Result;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;

@Service
public class QuizService {

	@Autowired
	QuizRepository quizRepo;

	@Autowired
	QuestionRepository questionRepo;

	// Create Quiz
	public Quiz createQuiz(String category, int numQ, String title) {
		// Validate inputs
		if (category == null || category.isEmpty() || title == null || title.isEmpty() || numQ <= 0) {
			throw new ResourceNotFoundException("Invalid input parameters");
		}

		// Check if the title is already in use
		if (checkTitle(title)) {
			throw new ResourceNotFoundException("Title alraedy exist");// Title already exists
		}

		List<Question> questions = questionRepo.findByCategory(category, numQ);
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		Quiz createdQuiz = quizRepo.save(quiz);
		if (questions == null || questions.size() < numQ) {
			throw new ResourceNotFoundException("Not enough questions available for the given category");
		}
		return createdQuiz;

	}

	// Get All Quiz
	public List<Quiz> getAllQuiz() {
		List<Quiz> quizzes = quizRepo.findAll();
		if (quizzes.isEmpty()) {
			return null;
		}
		return quizzes;

	}

	// Get Quiz By Id
	public Quiz getbyId(Quiz body) {
		Quiz quiz = quizRepo.findById(body.getQuizId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Quiz Id"));
		if (quiz.equals(null)) {
			return null;
		}
		return quiz;
	}

	// Get only questions from quiz
	public List<QuestionWrapper> getQuizQuestion(Quiz body) {
		Quiz quiz = quizRepo.findById(body.getQuizId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Quiz Id"));
		List<Question> questions = quiz.getQuestions();
		List<QuestionWrapper> questionsOnly = new ArrayList<>();
		for (Question q : questions) {
			QuestionWrapper qw = new QuestionWrapper(q.getQuestionId(), q.getQuestionTitle(), q.getOption1(),
					q.getOption2(), q.getOption3(), q.getOption4());
			questionsOnly.add(qw);
		}
		return questionsOnly;
	}

	public boolean checkTitle(String title) {
		Quiz quiz = quizRepo.findByTitle(title);
		if (quiz == null) {
			return false;
		}
		return true;
	}

	public Result getResultOfQuiz(Integer quizId, List<Question> body) {		
		Quiz quiz =quizRepo.findById(quizId).orElseThrow(()-> new ResourceNotFoundException("Invalid Quiz Id"));
		List<Question> questions =quiz.getQuestions();
		int right=0;
		int i=0;
		for(Question q:body) {
			if(q.getRightAnswer().equals(questions.get(i).getRightAnswer())) {
				right++;
			}
			i++;
		}
		Result result= new Result(quizId,i,right,right*1);		
		return result;
	}

}
