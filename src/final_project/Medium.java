package final_project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;

/*
* File: Medium.java
* Author: Zuefeng Xiong and Daniel Grib
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/10/2025
*
* Description: Class used to describe a medium level of difficulty
* 				of a game. It extends the main GUI, which is
* 				MemoryGame.java
*/

public class Medium extends MemoryGame {

    private Answer answerManager;
    private int score = 0;
    private Label scoreLabel;

    private Stage primaryStage;  // needed to swap scenes

    public Medium(Stage primaryStage, Label scoreLabel) {
        super(); // builds MemoryGame grid
        this.primaryStage = primaryStage;
        this.scoreLabel = scoreLabel;

        answerManager = new Answer();
        loadMCQuestions();
        
        updateScore();
        buildTopBar();
    }

    private void loadMCQuestions() {
        try {
            answerManager.loadQuestions(
                new File("MCQuestions.txt"),
                Question.Type.MULTIPLE_CHOICE
            );
        } catch (Exception e) {
            System.out.println("Error loading MC questions: " + e.getMessage());
        }
    }
    
    private void buildTopBar() {
        HBox topBar = new HBox(30);
        topBar.setPadding(new Insets(50, 0, 20, 0));
        topBar.setAlignment(Pos.CENTER);

        scoreLabel.setText("Score: 0");
        scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        topBar.getChildren().add(scoreLabel);

        this.setTop(topBar); // ← Using the MemoryGame method!
    }

    @Override
    public Scene getScene() {
        return gameScene;
    }
    
    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void askQuestion() {

        // Select a random multiple-choice question
        answerManager.selectQuestion(Question.Type.MULTIPLE_CHOICE);
        Question q = answerManager.getQuestion();

        // Build QuestionDisplay scene (MC version)
        QuestionDisplay qd = new QuestionDisplay(
        		primaryStage, 
        		gameScene, 
        		() -> {
            // Callback when returning to the grid
            score += 100;     // ← award points
            updateScore();
        });

        // switch to question scene
        primaryStage.setScene(qd.getScene());
    }
}