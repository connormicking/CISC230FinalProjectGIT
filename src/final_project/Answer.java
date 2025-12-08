package final_project;
/*
* File: 
* Author: Connor King
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/7/2025
*
* Description:	
* 
*/

import java.util.ArrayList;
public class Answer {
	private Question currentQuestion;
	private ArrayList<Question> questions;
	
	public Answer() {
		questions = new ArrayList<Question>();
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
}
