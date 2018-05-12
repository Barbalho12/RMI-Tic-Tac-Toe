package javafx;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import interfaces.IGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Response;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class TabHandler implements Initializable {

	@FXML
	private Label labelState;

	@FXML
	private Button p1_1, p1_2, p1_3, p1_4, p1_5, p1_6, p1_7, p1_8, p1_9;

	@FXML
	private Button p2_1, p2_2, p2_3, p2_4, p2_5, p2_6, p2_7, p2_8, p2_9;

	@FXML
	private Button p3_1, p3_2, p3_3, p3_4, p3_5, p3_6, p3_7, p3_8, p3_9;

	@FXML
	private Button p4_1, p4_2, p4_3, p4_4, p4_5, p4_6, p4_7, p4_8, p4_9;

	@FXML
	private Button p5_1, p5_2, p5_3, p5_4, p5_5, p5_6, p5_7, p5_8, p5_9;

	@FXML
	private Button p6_1, p6_2, p6_3, p6_4, p6_5, p6_6, p6_7, p6_8, p6_9;

	@FXML
	private Button p7_1, p7_2, p7_3, p7_4, p7_5, p7_6, p7_7, p7_8, p7_9;

	@FXML
	private Button p8_1, p8_2, p8_3, p8_4, p8_5, p8_6, p8_7, p8_8, p8_9;

	@FXML
	private Button p9_1, p9_2, p9_3, p9_4, p9_5, p9_6, p9_7, p9_8, p9_9;

	@FXML
	private Pane pane_1, pane_2, pane_3, pane_4, pane_5, pane_6, pane_7, pane_8, pane_9;

	@FXML
	private MenuItem mi_quit;

	@FXML
	private AnchorPane allpane;
	
	private void taskWait(Task<Object> tarefaCargaPg) {
		Task<Object> tarefa2 = new Task<Object>() {
			String response;

			@Override
			protected String call() throws Exception {
				while (!tarefaCargaPg.isDone()) {
					sleep(100);
				}
				response = game.check(player);
				return response;
			}

			@Override
			protected void succeeded() {
				labelState.setText("");
				if (response.equals("\nO jogo foi encerrado pelo oponente!\n") || game.checkQuited()) {
					alert("\nO jogo foi encerrado pelo oponente!\n");
					return;
				}
				try {
					updateButtons(new Response(response));
				} catch (RemoteException e) {
					e.printStackTrace();
				} finally {
					setDisableButtons(false);
				}

			}
		};
		Thread t2 = new Thread(tarefa2);
		t2.setDaemon(true);
		t2.start();
		
	}

	public void taskPlay(int board, int position) {

		Task<Object> tarefaCargaPg = new Task<Object>() {
			String response;

			@Override
			protected String call() throws Exception {
				response = game.play(player, board, position);
				if (response.length() < 100) {
					return response;
				}
				setDisableButtons(true);
				return response;
			}

			@Override
			protected void succeeded() {
				if (response.equals("\nO jogo foi encerrado pelo oponente!\n") || game.checkQuited()) {
					alert("\nO jogo foi encerrado pelo oponente!\n");
					return;
				}
				labelState.setText("Esperando oponente...");
				taskWait(this);
				try {
					updateButtons(new Response(response));
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();

		
	}

	@FXML
	private void click(ActionEvent event) throws RemoteException {
		Button node = (Button) event.getSource();

		String value = node.getId();

		int board = Integer.valueOf(value.split("_")[0].substring(1));
		int position = Integer.valueOf(value.split("_")[1]);
		taskPlay(board, position);
	}

	Button getButton(int board, int position) {
		Pane pane = (Pane) allpane.getChildren().get(board);
		Button button = (Button) pane.getChildren().get(position);
		return button;
	}

	void updateButtons(Response response) throws RemoteException {
		if (response.getSettings() != null) {
			int i = 0;
			for (Node npane : allpane.getChildren()) {
				try {
					Pane pane = (Pane) npane;
					for (Node nbut : pane.getChildren()) {
						Button button = (Button) nbut;
						if (response.getSettings()[i].equals("X") || response.getSettings()[i].equals("O"))
							button.setText(response.getSettings()[i]);
						i++;
					}
				} catch (Exception e) {
				}

			}

		}
		if (response.getMessage() != null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Message");
			alert.setContentText(response.getMessage());
			alert.show();
		}

	}

	void setDisableButtons(boolean mode) {
		for (Node npane : allpane.getChildren()) {
			try {
				Pane pane = (Pane) npane;
				for (Node nbut : pane.getChildren()) {
					Button button = (Button) nbut;
					button.setDisable(mode);
				}
			} catch (Exception e) {
			}
		}
	}

	public int player;
	public GameREST game;

	public TabHandler() throws NotBoundException {

	}

	public void execute(IGame game) throws RemoteException {

	}

	@FXML
	private void quit(ActionEvent event) {
		game.quit(player);
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		game = new GameREST();

		Task<Object> tarefaCargaPg = new Task<Object>() {

			String response;

			@Override
			protected String call() throws Exception {
				while (allpane == null) {
					sleep(20);
				}
				setDisableButtons(true);
				player = game.init();
				labelState.setText("Esperando oponente...");
				response = game.check(player);
				return null;
			}

			@Override
			protected void succeeded() {
				labelState.setText("");
				if (response.equals("\nO jogo foi encerrado pelo oponente!\n") || game.checkQuited()) {
					alert("\nO jogo foi encerrado pelo oponente!\n");
					return;
				}
				try {
					
					setDisableButtons(false);
					updateButtons(new Response(response));
					alert("Iniciado! pode jogar");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();

	}

	void alert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setContentText(message);
		alert.show();
	}

	public static void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {

		}
	}

}
