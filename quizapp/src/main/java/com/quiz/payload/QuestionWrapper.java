package com.quiz.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWrapper {

	private Integer questionId;

	private String questionTitle;

	private String option1;

	private String option2;

	private String option3;

	private String option4;

}
