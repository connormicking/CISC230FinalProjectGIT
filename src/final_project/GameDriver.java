package final_project;
/*
* File: GameDriver.java
* Author: Connor King
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/7/2025
*
* Description:	
* 
*/
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;

public class GameDriver extends Application {
	Scene menu; // menu scene stored globally; can be accessed later
	RadioButton easyButton;
	RadioButton mediumButton;
	RadioButton hardButton;
	public void start(Stage ps) {
		Text titleText = new Text("Sustainability Match Game");
		Text subtitleText = new Text("A memory matching game involving sustainability trivia");
		Text difficultyText = new Text("Select a difficulty:");
		
		Button startButton = new Button("Start Game");
		Button infoButton = new Button("Info");
		
		FontWeight weight = FontWeight.BOLD;
		titleText.setFont(Font.font("times",weight,24));
		
		ToggleGroup difficultyGroup = new ToggleGroup();
		
		easyButton = new RadioButton("Easy");
		mediumButton = new RadioButton("Medium");
		hardButton = new RadioButton("Hard");
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(startButton,infoButton);
		
		VBox difficultyBox = new VBox();
		difficultyBox.setSpacing(10);
		difficultyBox.setAlignment(Pos.CENTER);
		difficultyBox.getChildren().addAll(difficultyText,easyButton,mediumButton,hardButton,buttonBox);
		
		easyButton.setToggleGroup(difficultyGroup);
		mediumButton.setToggleGroup(difficultyGroup);
		hardButton.setToggleGroup(difficultyGroup);
		
		easyButton.setSelected(true);
		
		startButton.setOnAction(this :: difficultyHandler);
		
		VBox topTextBox = new VBox();
		topTextBox.setSpacing(10);
		topTextBox.setAlignment(Pos.CENTER);
		
		topTextBox.getChildren().addAll(titleText,subtitleText);
		
		VBox menuBox = new VBox();
		menuBox.setSpacing(30);
		menuBox.setAlignment(Pos.TOP_CENTER);
		
		menuBox.getChildren().addAll(topTextBox, difficultyBox);
		
		
		menu = new Scene(menuBox,400,300);
		ps.setScene(menu);
		ps.setTitle("Sustainability Match Game");
		ps.show();
	}


	public void difficultyHandler(ActionEvent event) {
		
	}


}
