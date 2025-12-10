package final_project;

/*
* File: Hard.java
* Author: Daniel Grib
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/10/2025
*
* Description: Class used to describe a hard level of difficulty
* 				of a game. It extends the main GUI, which is
* 				MemoryGame.java
*/

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class Hard extends MemoryGame {

    private Answer answerManager;

    private int score = 0;
    private Label scoreLabel;

    private Stage primaryStage;

    private int totalTime = 60;       // 60-second round by default
    private int timeRemaining = 60;
    private Label timerLabel;

    private Timeline timer;
    private boolean timerRunning = false;


    public Hard(Stage primaryStage, Label scoreLabel, Label timerLabel) {
        super();    // builds grid
        this.primaryStage = primaryStage;
        this.scoreLabel = scoreLabel;
        this.timerLabel = timerLabel;

        answerManager = new Answer();
        loadMCQuestions();

        updateScoreLabel();
        updateTimerLabel();
        buildTopBar();
        startTimer();
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
        timerLabel.setText("Time: " + timeRemaining);
        timerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        topBar.getChildren().addAll(scoreLabel, timerLabel);

        this.setTop(topBar); // ← Using the MemoryGame method!
    }

    // -----------------------------
    //  Timer Control
    // -----------------------------
    private void startTimer() {
        timerRunning = true;

        timer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                timeRemaining--;
                updateTimerLabel();

                if (timeRemaining <= 0) {
                    timerRunning = false;
                    timer.stop();
                    endGame();
                }
            })
        );

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


    private void stopTimer() {
        if (timer != null) timer.stop();
    }


    private void resumeTimer() {
        if (timer != null && timerRunning) timer.play();
    }


    private void updateTimerLabel() {
        timerLabel.setText("Time: " + Math.max(timeRemaining, 0) + "s");
    }


    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }


    @Override
    public Scene getScene() {
        return gameScene;
    }


    // -----------------------------
    //  Question Handling
    // -----------------------------
    @Override
    public void askQuestion() {
        if (!timerRunning) return;

        // Pause timer while answering
        stopTimer();

        answerManager.selectQuestion(Question.Type.MULTIPLE_CHOICE);
        Question q = answerManager.getQuestion();

        QuestionDisplay qd = new QuestionDisplay(
        		primaryStage, 
        		gameScene,
        		() -> resumeTimer()
        		);


        primaryStage.setScene(qd.getScene());
    }
    
    private void endGame() {
        stopTimer();
    }
}