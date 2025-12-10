package final_project;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.geometry.Pos;


/*
* File: MemoryGame.java
* Author: Zuefeng Xiong and Daniel Grib
* Course: CISC230
* Lab: Group Sustainability Lab
* Date: 12/10/2025
*
* Description: This class generates the grid itself and
* 				does most of the function controls.
* 				Easy, Medium, and Hard are all classes that alter the MemoryGame class.
*/

public abstract class MemoryGame implements Showable {
    protected Scene gameScene;
    protected GridPane grid;

    protected Rectangle firstCard = null;
    protected Rectangle secondCard = null;

    protected HashMap<Rectangle, Integer> cardValues = new HashMap<>();
    protected HashMap<Rectangle, Text> symbols = new HashMap<>();
    protected int[] shuffledValues = new int[16]; // 8 pairs (1-8 twice)

    protected BorderPane root;
    
    public MemoryGame() {
        generateCardPairs();
        buildGridUI();
    }
    
    public void setTop(Node n) {
        root.setTop(n);
    }

    // -----------------------
    // GRID + SCENE CREATION
    // -----------------------

    private void generateCardPairs() {
        // Values 1–8 twice (pairs)
        for (int i = 0; i < 16; i++) {
            shuffledValues[i] = (i / 2) + 1;
        }

        // Shuffle
        Random rand = new Random();
        for (int i = 15; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = shuffledValues[i];
            shuffledValues[i] = shuffledValues[j];
            shuffledValues[j] = temp;
        }
    }
    
    private String getSymbolForValue(int v) {
        switch (v) {
            case 1: return "★";   // star
            case 2: return "◆";   // diamond
            case 3: return "♥";   // heart
            case 4: return "♣";   // club
            case 5: return "♠";   // spade
            case 6: return "☀";   // sun
            case 7: return "⚑";   // flag
            case 8: return "✿";   // flower
            default: return "?";
        }
    }

    protected void buildGridUI() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);

        int index = 0;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {

                Rectangle card = new Rectangle(100, 100);
                card.setFill(Color.DARKGRAY);
                card.setStroke(Color.BLACK);

                int val = shuffledValues[index];
                cardValues.put(card, val);

                card.setOnMouseClicked(e -> handleCardClick(card));

                StackPane cardPane = new StackPane();
                cardPane.getChildren().add(card);

                // Create symbol text (hidden at start)
                Text symbol = new Text(getSymbolForValue(val));
                symbol.setVisible(false);
                symbol.setFont(Font.font("System", FontWeight.BOLD, 60));

                cardPane.getChildren().add(symbol);

                // Store symbol in a map so we can reveal/hide it later
                symbols.put(card, symbol);
                
                grid.add(cardPane, col, row);
                
                index++;
            }
        }

        root = new BorderPane();
        root.setCenter(grid);

        gameScene = new Scene(root, 600, 600);
    }

    @Override
    public Scene getScene() {
        return gameScene;
    }

    // -----------------------
    // CARD FLIP + MATCH LOGIC
    // -----------------------

    private void handleCardClick(Rectangle card) {
        if (firstCard == null) {
            firstCard = card;
            reveal(card);
            return;
        }
        else if (secondCard == null && card != firstCard) {
            secondCard = card;
            reveal(card);
            checkMatch();
        }
    }

    protected void reveal(Rectangle card) {
        card.setFill(Color.LIGHTBLUE);
        symbols.get(card).setVisible(true);
    }

    protected void hide(Rectangle card) {
        card.setFill(Color.DARKGRAY);
        symbols.get(card).setVisible(false);
    }

    private void checkMatch() {
        int v1 = cardValues.get(firstCard);
        int v2 = cardValues.get(secondCard);

        if (v1 == v2) {
            askQuestion();

            firstCard = null;
            secondCard = null;
        } else {
            // not a match → hide both after short delay
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> {
                        hide(firstCard);
                        hide(secondCard);
                        firstCard = null;
                        secondCard = null;
                    });
                }
            }, 600);
        }
    }

    public abstract void askQuestion();
}
