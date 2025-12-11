package final_project;
/*
* File: GameDriver.java
* Author: Connor King
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/7/2025
*
* Description:	Test class to verify that file reading works. Currently prints out all the questions after reading them from a file within the Answer class.
* 				Note: Delete this class later.
* 
*/
import java.io.File;
import java.io.FileNotFoundException;
public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		Answer answerManager = new Answer();
		File file = new File("MCQuestions.txt");
		answerManager.loadQuestions(file, Question.Type.MULTIPLE_CHOICE);
		file = new File("TFQuestions.txt");
		answerManager.loadQuestions(file, Question.Type.TRUE_FALSE);
		
		for (Question question : answerManager.getMultipleChoiceQuestions()) {
			System.out.println(question.getTopic() + ":");
			System.out.println(question);
			for (String answerLine : question.getAnswers()) {
				System.out.println(answerLine);
			}
			System.out.println("===============================================");
		}
	
		for (Question question : answerManager.getTrueFalseQuestions()) {
			System.out.println(question.getTopic() + ":");
			System.out.println(question);
			for (String answerLine : question.getAnswers()) {
				System.out.println(answerLine);
			}
			System.out.println("===============================================");
		}
	}
}
