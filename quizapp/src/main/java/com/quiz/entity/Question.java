package com.quiz.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer questionId;

	@NotEmpty(message = "Question title should not be empty")
	@Size(max = 300, min = 1, message = "Question title should be between 5 to 300 characters")
	private String questionTitle;

	@NotEmpty(message = "Option 1 should not be empty")
	@Size(max = 100, min = 1, message = "Option 1 should be between 5 to 100 characters")
	private String option1;

	@NotEmpty(message = "Option 2 should not be empty")
	@Size(max = 100, min = 1, message = "Option 2 should be between 5 to 100 characters")
	private String option2;

	@NotEmpty(message = "Option 3 should not be empty")
	@Size(max = 100, min = 1, message = "Option 3 should be between 5 to 100 characters")
	private String option3;

	@NotEmpty(message = "Option 4 should not be empty")
	@Size(max = 100, min = 1, message = "Option 4 should be between 5 to 100 characters")
	private String option4;

	@NotEmpty(message = "Right answer field should not be empty")
	@Size(max = 100, min = 1, message = "Right answer should be between 5 to 100 characters")
	private String rightAnswer;

	@NotEmpty(message = "Difficulty level field should not be empty")
	private String difficultyLevel; 

	@NotEmpty(message = "Category field should not be empty")
	private String category;
	
}
