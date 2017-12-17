package View;

import Controller.Game;
import Model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameMenuGUI {

	TextField nameField = new TextField();
	TextField nickNameField = new TextField();
	Button newGameBtn = new Button("New Game");

	public void mainStage(Stage stage) {

		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color: darkgreen");

		pane.setHgap(10);
		pane.setVgap(10);
		pane.setAlignment(Pos.CENTER);

		newGameBtn.setOnAction(e -> newGameBtnTapped(stage));
		newGameBtn.setStyle("-fx-background-color: #00FFCC; ");

		Label label1 = new Label("Enter Your Name: ");
		label1.setTextFill(Color.web("#ffffff"));
		Label label2 = new Label("Enter you player Nickname: ");
		label2.setTextFill(Color.web("#ffffff"));

		pane.add(label1, 0, 0);
		pane.add(nameField, 2, 0);
		pane.add(label2, 0, 1);
		pane.add(nickNameField, 2, 1);
		pane.add(newGameBtn, 1, 3);

		Scene scene = new Scene(pane, 900, 700);
		stage.setScene(scene);
		stage.show();
	}

	private void newGameBtnTapped(Stage stage) {

		Player player = new Player();
		player.setName(nameField.getText().toString());
		player.setNickname(nickNameField.getText().toString());

		Game game = new Game();

		GameGUI destinationStage = new GameGUI();
		destinationStage.playerName = player.getName();
		destinationStage.playerNickName = player.getNickname();
		destinationStage.game = game;
		destinationStage.updateStage(stage);

	}

}
