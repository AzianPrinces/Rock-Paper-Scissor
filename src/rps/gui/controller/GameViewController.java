package rps.gui.controller;

// Java imports
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.awt.Color.RED;
import static java.awt.SystemColor.text;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {

    @FXML
    private MFXListView<String> log;
    @FXML
    private MFXTextField nameField;
    private GameManager ge;
    @FXML
    private MFXButton btnRock;
    @FXML
    private MFXButton btnPaper;
    @FXML
    private MFXButton btnScissor;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        welcomeMessage();
        btnRock.setDisable(true);
        btnPaper.setDisable(true);
        btnScissor.setDisable(true);


    }




    public void onButtonSave(javafx.event.ActionEvent event) {

        String playerName = nameField.getText();

        if(playerName != null && !playerName.isEmpty()) {
            log.getItems().add("Welcome player " + playerName);
            nameField.clear();
        } else {
            log.getItems().add("Please enter a player name");
        }

        // Create the Player objects
        IPlayer human = new Player(playerName, PlayerType.Human);
        IPlayer bot = new Player(getRandomBotName(), PlayerType.AI);

        // Initialize the GameManager with these players
        ge = new GameManager(human, bot);

        // Once we have a valid game, we can enable the move buttons
        btnRock.setDisable(false);
        btnPaper.setDisable(false);
        btnScissor.setDisable(false);

        // Clear any old results
        log.getItems().clear();
        // Optionally, you could also show a "Game started" message
        log.getItems().add("Game started! Playing against " + bot.getPlayerName());


    }

    @FXML
    private void handleRock(javafx.event.ActionEvent event) {
        if (ge == null) return; // In case user didn't click Save yet
        Result result = ge.playRound(Move.Rock);
        log.getItems().add(formatResult(result));
    }

    @FXML
    private void handlePaper(javafx.event.ActionEvent event) {
        if (ge == null) return;
        Result result = ge.playRound(Move.Paper);
        log.getItems().add(formatResult(result));

    }

    @FXML
    private void handleScissor(javafx.event.ActionEvent event) {
        if (ge == null) return;
        Result result = ge.playRound(Move.Scissor);
        log.getItems().add(formatResult(result));
    }

    private void welcomeMessage() {
        String welcome = "Welcome to Rock Paper Scissor game!" +
                " \n Please enter a player name :) ";
        log.getItems().add(welcome);

        log.setCellFactory(MFXListView -> new MFXListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }else {
                    Text text = new Text(item);

                    if(item.equals(welcome)) {
                        text.setFill(Color.GREEN);
                    }else{
                        text.setFill(Color.BLACK);
                    }
                    setText(null);
                    setGraphic(text);
                }
            }
        });
    }

    private String getRandomBotName() {
        String[] botNames = new String[] {
                "R2D2",
                "Mr. Data",
                "3PO",
                "Bender",
                "Marvin the Paranoid Android",
                "Bishop",
                "Robot B-9",
                "HAL"
        };
        int randomNumber = new Random().nextInt(botNames.length - 1);
        return botNames[randomNumber];
    }

    // 8. Helper method to build a custom string for the round result
    private String formatResult(Result result) {
        if (result.getType() == ResultType.Tie) {
            return String.format(
                    "Round #%d: %s (%s) ties with %s (%s)!",
                    result.getRoundNumber(),
                    result.getWinnerPlayer().getPlayerName(),
                    result.getWinnerMove(),
                    result.getLoserPlayer().getPlayerName(),
                    result.getLoserMove()
            );
        } else {
            // It's a win
            String winnerName = result.getWinnerPlayer().getPlayerName();
            String winnerMove = result.getWinnerMove().toString();
            String loserName = result.getLoserPlayer().getPlayerName();
            String loserMove = result.getLoserMove().toString();

            return String.format(
                    "Round #%d: %s (%s) wins over %s (%s)!",
                    result.getRoundNumber(),
                    winnerName,
                    winnerMove,
                    loserName,
                    loserMove
            );
        }
    }

}



