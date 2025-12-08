package final_project;
/*
* File: 
* Author: Connor King
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/7/2025
*
* Description:	Class for storing all questions and checking if the user has the correct answer. Selects a current question randomly for then removes it from the
* 				ArrayList to avoid repeating the question.
* 
*/
import java.util.ArrayList;
import java.util.Random;
public class Answer {
	private Question1 currentQuestion;
	private ArrayList<Question1> trueFalseQuestions;
	private ArrayList<Question1> multipleChoiceQuestions;
	
	public Answer() {
		trueFalseQuestions = new ArrayList<Question1>();
		multipleChoiceQuestions = new ArrayList<Question1>();
	}
	
	/// Method for adding questions one by one
	public void addQuestion(Question1 question) {
		if (question.getType() == Question1.Type.TRUE_FALSE)
			trueFalseQuestions.add(question);
		else
			multipleChoiceQuestions.add(question);
	}
	
	/// Method for adding all questions via a single question ArrayList
	public void loadQuestions(ArrayList<Question1> questions) {
		for (Question1 question : questions) {
			if (question.getType() == Question1.Type.TRUE_FALSE)
				trueFalseQuestions.add(question);
			else
				multipleChoiceQuestions.add(question);
		}
	}
	
	/// Method for checking a given answer, returning true for correct and false for wrong
	public boolean checkAnswer(char ans) {
		if (ans == currentQuestion.getCorrectAnswer())
			return true;
		else
			return false;
	}
	
	/// Getter method that returns the current selected question
	public Question1 getQuestion() {
		return currentQuestion;
	}
	
	/// Getter method that returns the list of true / false questions	
	public ArrayList<Question1> getTrueFalseQuestions() {
		return trueFalseQuestions;
	}
	
	/// Getter method that returns the list of multiple-choice questions	
	public ArrayList<Question1> getMultipleChoiceQuestions() {
		return multipleChoiceQuestions;
	}
	
	/// Method that randomly selects a question of a given type before removing it from the answer pool (i.e. its respective ArrayList).
	public void selectQuestion(Question1.Type type) {
		Random rand = new Random();
		if (type == Question1.Type.TRUE_FALSE) {
			currentQuestion  = trueFalseQuestions.get(rand.nextInt(trueFalseQuestions.size()));
			trueFalseQuestions.remove(currentQuestion);
		}
		else {
			currentQuestion  = multipleChoiceQuestions.get(rand.nextInt(multipleChoiceQuestions.size()));
			multipleChoiceQuestions.remove(currentQuestion);
		}
	}
}
