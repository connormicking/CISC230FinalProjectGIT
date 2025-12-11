package final_project;

import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
/*
* File: QuestionDisplay
* Author: Matt
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/7/2025
*
* Description:	Class for storing all questions and checking if the user has the correct answer. Selects a current question randomly for then removes it from the
* 				ArrayList to avoid repeating the question.
* 
*/
public class QuestionDisplay implements Showable{
	


@Override
public Scene getScene() {
	Button buttonA = new Button();
	Button buttonB = new Button();
	Button buttonC = new Button();
	Button buttonD = new Button();
	Pane frame = new Pane(buttonA, buttonB, buttonC, buttonD);
	
	Scene scene = new Scene (frame, 500, 500);

	return scene;

}}
