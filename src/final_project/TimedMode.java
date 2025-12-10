package final_project;

/*
* File: TimedMode.java
* Author: Daniel Grib
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/10/2025
*
* Description: Class for determining the timed mode for this game.
*              This is the hard difficulty for the game.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimedMode extends MemoryGame {

    private int totalTime;
    private int timeRemaining;
    private int score;

    private Timeline timer;
    private boolean timerRunning;

    private Random rand = new Random();

    private Label timerLabel;
    private Label scoreLabel;

    private Scene gameScene;

    private Answer answerManager;

    public TimedMode(Scene scene, Label timerLabel, Label scoreLabel, int totalTimeSeconds) {
        super();
        this.gameScene = scene;
        this.timerLabel = timerLabel;
        this.scoreLabel = scoreLabel;

        this.totalTime = totalTimeSeconds;
        this.timeRemaining = totalTimeSeconds;

        this.score = 0;
        this.timerRunning = false;

        this.answerManager = new Answer();
        loadAllQuestions();

        updateTimerLabel();
        updateScoreLabel();
    }

    @Override
    public Scene getScene() {
        return gameScene;
    }

    private void loadAllQuestions() {
        try {
            answerManager.loadQuestions(new File("MCQuestions.txt"), Question1.Type.MULTIPLE_CHOICE);
            answerManager.loadQuestions(new File("TFQuestions.txt"), Question1.Type.TRUE_FALSE);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error loading question files: " + e.getMessage());
        }
    }

    public void start() {
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

    private void updateTimerLabel() {
        timerLabel.setText("Time Left: " + Math.max(timeRemaining, 0) + "s");
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void askQuestion(Question ignored) {
        if (!timerRunning) return;

        // Select MC question using the Answer system
        answerManager.selectQuestion(Question1.Type.MULTIPLE_CHOICE);
        Question1 selectedQ = answerManager.getQuestion();

        showMCQuestionPopup(selectedQ);
    }

    public void showMCQuestionPopup(Question1 q) {

        Stage stage = new Stage();
        stage.setTitle("Question");

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        Label questionText = new Label(q.getQuestionString());
        questionText.setWrapText(true);

        String[] ops = q.getAnswers();

        ToggleGroup group = new ToggleGroup();

        RadioButton A = new RadioButton("A) " + ops[0]);
        RadioButton B = new RadioButton("B) " + ops[1]);
        RadioButton C = new RadioButton("C) " + ops[2]);
        RadioButton D = new RadioButton("D) " + ops[3]);

        A.setToggleGroup(group);
        B.setToggleGroup(group);
        C.setToggleGroup(group);
        D.setToggleGroup(group);

        Button submit = new Button("Submit");

        submit.setOnAction(e -> {

            if (!timerRunning) { stage.close(); return; }

            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected == null) return;

            char response = selected.getText().charAt(0);  // 'A', 'B', 'C', 'D'
            boolean correct = answerManager.checkAnswer(response);

            if (correct) {
                int base = 100;
                int bonus = Math.max(0, timeRemaining / 4);
                score += base + bonus;
                updateScoreLabel();
                showAlert("Correct!", "You earned 100 + " + bonus + " bonus!");
            }
            else {
                showAlert("Incorrect", "Try again next time!");
            }

            stage.close();
        });

        layout.getChildren().addAll(questionText, A, B, C, D, submit);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void endGame() {
        showAlert("Time's Up!", "Final Score: " + score);
        // TODO: transition to leaderboard or summary screen
    }
}
