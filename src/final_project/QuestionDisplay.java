package final_project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

/*
* File: QuestionDisplay.java
* Author:
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/7/2025
*
* Description:
*   A blank placeholder scene for displaying questions.
*   Contains a button to return to the MemoryGame scene.
*/

public class QuestionDisplay implements Showable {

    private Scene questionScene;

    // Add a Runnable callback that executes when returning to the game
    public QuestionDisplay(Stage primaryStage, Scene gameScene, Runnable onReturn) {

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back to Game");

        backButton.setOnAction(e -> {
            primaryStage.setScene(gameScene);

            // Restart the timer (or do any needed action)
            if (onReturn != null) {
                onReturn.run();
            }
        });

        root.getChildren().add(backButton);

        questionScene = new Scene(root, 500, 400);
    }

    @Override
    public Scene getScene() {
        return questionScene;
    }
}
