package View;

import Controller.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameGUI extends GameMenuGUI {

	Game game;
	public static Dialog<Object> dialog = new Dialog<Object>();
	private VBox leftVbox;

	public String playerName;
	public String playerNickName;
	public double prize;

	TextField guessedNumberField = new TextField();
	Label displayGuessesLbl = new Label("Last 10 Guesses");
	Label wheelsNumberLabel;
	Label totalPrize;
	Label prizeLabel;
	Label winningMessageLbl;

	public void updateStage(Stage stage) {

		StackPane rootPane = new StackPane();
		BorderPane mainPane = new BorderPane();

		mainPane.setStyle("-fx-background-color: black");
		mainPane.setPadding(new Insets(10, 20, 10, 20));

		topPane(mainPane);
		leftPane(mainPane);
		bottomPane(mainPane);
		centerPane(mainPane);

		rootPane.getChildren().addAll(mainPane);
		Scene scene = new Scene(rootPane, 900, 700);
		stage.setScene(scene);
		stage.show();
	}

	private void topPane(BorderPane mainPane) {
		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color: darkgreen");

		pane.setHgap(30);
		pane.setVgap(30);
		pane.setAlignment(Pos.CENTER);
		mainPane.setTop(pane);

		Label userNameDisplay = new Label(playerName);
		userNameDisplay.setTextFill(Color.web("#ffd700"));

		Label nicknameDisplay = new Label(playerNickName);
		nicknameDisplay.setTextFill(Color.web("#ffd700"));

		Label name = new Label("Name: ");
		name.setTextFill(Color.web("#ffffff"));

		Label nickname = new Label("Player Nickname:");
		nickname.setTextFill(Color.web("#ffffff"));

		Label labelPrize = new Label("Total Prize:");
		labelPrize.setTextFill(Color.web("#ffffff"));

		totalPrize = new Label("" + game.getTotalPrize());
		totalPrize.setTextFill(Color.web("#ffd700"));

		pane.add(name, 1, 2);
		pane.add(userNameDisplay, 3, 2);
		pane.add(nickname, 5, 2);
		pane.add(nicknameDisplay, 6, 2);
		pane.add(labelPrize, 8, 2);
		pane.add(totalPrize, 9, 2);
	}

	private void leftPane(BorderPane mainPane) {
		leftVbox = new VBox(10);
		leftVbox.setPrefSize(200, 700);
		leftVbox.setAlignment(Pos.CENTER);
		leftVbox.setStyle(
				"-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 2; -fx-background-color: white; -fx-border-color: darkolivegreen");
		mainPane.setLeft(leftVbox);
		leftVbox.getChildren().add(displayGuessesLbl);

	}

	private void bottomPane(BorderPane mainPane) {
		VBox bottomPane = new VBox(20);
		bottomPane.setPrefSize(400, 200);
		bottomPane.setStyle("-fx-padding: 10; -fx-background-color: #003300;");
		mainPane.setBottom(bottomPane);

		Button startGameBtn = new Button("Start Game");
		startGameBtn.setStyle("-fx-background-color:gold; ");
		startGameBtn.setOnAction(e -> startGameTapped(dialog));

		Button displayGuessesButton = new Button("Display");
		displayGuessesButton.setStyle("-fx-background-color: #00FFCC; ");
		displayGuessesButton.setOnAction(e -> displayLastGuesses());

		bottomPane.setAlignment(Pos.CENTER);

		bottomPane.getChildren().add(startGameBtn);
		bottomPane.getChildren().add(displayGuessesButton);
	}

	private void centerPane(BorderPane mainPane) {
		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color: darkgreen");

		pane.setHgap(10);
		pane.setVgap(10);
		pane.setAlignment(Pos.CENTER);
		mainPane.setCenter(pane);

		prizeLabel = new Label();
		wheelsNumberLabel = new Label();
		winningMessageLbl = new Label();

		wheelsNumberLabel.setStyle("-fx-font-size: 35pt; -fx-font-weight: bold");
		wheelsNumberLabel.setTextFill(Color.web("#ffd700"));
		pane.add(winningMessageLbl, 0, 0);
		pane.add(prizeLabel, 0, 1);
		pane.add(wheelsNumberLabel, 0, 2);
	}

	private void displayDialog() {
		synchronized (GameGUI.class) {
			dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
			closeButton.managedProperty().bind(closeButton.visibleProperty());
			closeButton.setVisible(false);

			dialog.setHeaderText("Enter your guess");

			GridPane pane = new GridPane();
			pane.setHgap(10);
			pane.setVgap(10);
			pane.setPadding(new Insets(20, 15, 10, 10));

			guessedNumberField.setPromptText("Guess a number");
			Button guessBtn = new Button("Guess");
			guessBtn.setOnAction(e -> guessButtonTapped(dialog, guessedNumberField));

			pane.add(new Label("Enter your guess from 1 to 10"), 0, 0);
			pane.add(guessedNumberField, 2, 0);
			pane.add(guessBtn, 3, 0);

			dialog.getDialogPane().setContent(pane);
			dialog.showAndWait();
		}

	}

	// ----------------------------------Buttons Action-----------------------------

	// The user choose a number in the dialog window
	private void guessButtonTapped(Dialog<?> dialog, TextField number) {

		// Getting the user's number choice
		int guessedNumber = Integer.parseInt(guessedNumberField.getText().toString());
		game.setGuessedNumber(guessedNumber);
		displayLastGuesses();
		System.out.println("closing");
		dialog.close();
	}

	public static void closeDialog() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				dialog.close();
			}
		});

	}

	// Start game tapped
	private void startGameTapped(Dialog<?> dialog) {
		prizeLabel.setText("");
		winningMessageLbl.setText("");
		wheelsNumberLabel.setText("");
		prize = 0;

		try {
			game.startGame();
			displayDialog();
			Thread.sleep(10000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		updateView();
	}

	// Display button is tapped
	public void displayLastGuesses() {
		leftVbox.getChildren().clear();
		leftVbox.getChildren().add(displayGuessesLbl);

		for (int i = 0; i < game.lastTenGuesses().size(); i++) {

			leftVbox.getChildren().add(new Label("Guess" + " : " + game.lastTenGuesses().get(i)));
		}
	}

	// ------------------------Update the view ---------------------------------

	// update the view, buttons and labels
	public void updateView() {

		// Getting the wheel number generated
		wheelsNumberLabel.setText("  " + game.getwheelsNumber() + "  ");
		if (game.getwheelsNumber() % 2 == 0) {
			wheelsNumberLabel.setStyle(
					"-fx-font-size: 120; -fx-text-fill: white; -fx-background-color: black;  -fx-width: 100px\n");
		} else {
			wheelsNumberLabel.setStyle("-fx-font-size: 120; -fx-text-fill: black; -fx-background-color: firebrick");
		}

		// checking if the user is the winner, and changing the labels.
		if (game.isWinner()) {
			winningMessageLbl.setText("YOU WON!");
			winningMessageLbl.setStyle("-fx-font-size: 30; -fx-text-fill: white");

			prize = game.winnerPrize();
			if (prize > 0) {
				prizeLabel.setText("$" + prize);
				prizeLabel.setStyle("-fx-font-size: 45; -fx-text-fill: gold");
			}
		} else {
			winningMessageLbl.setText("YOU LOST. TRY AGAIN!");
			winningMessageLbl.setStyle("-fx-font-size: 30; -fx-text-fill: white");
		}

		// Set the total price
		totalPrize.setText("$" + game.getTotalPrize());
	}

}
