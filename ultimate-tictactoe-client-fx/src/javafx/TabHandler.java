package javafx;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import enums.GameOptions;
import interfaces.IGame;
import interfaces.IPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Player;
import model.Response;
import javafx.fxml.Initializable;

public class TabHandler implements Initializable{

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

	@FXML
	private void click(ActionEvent event) throws RemoteException {
		Button node = (Button) event.getSource();

		String value = node.getId();

		// node.setText("X");
		int board = Integer.valueOf(value.split("_")[0].substring(1));
		int position = Integer.valueOf(value.split("_")[1]);

		setDisableButtons(true);

		Task<Object> tarefaCargaPg = new Task<Object>() {
			String response;

			@Override
			protected String call() throws Exception {
				response = game.play(player, board, position);
				return response;
			}

			@Override
			protected void succeeded() {
				try {
					updateButtons(new Response(response));
				} catch (RemoteException e) {
					e.printStackTrace();
				} finally {
					setDisableButtons(false);
				}

			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();

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
				Pane pane = (Pane) npane;
				for (Node nbut : pane.getChildren()) {
					Button button = (Button) nbut;
					if (response.getSettings()[i].equals("X") || response.getSettings()[i].equals("O"))
						button.setText(response.getSettings()[i]);
					i++;
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
			Pane pane = (Pane) npane;
			for (Node nbut : pane.getChildren()) {
				Button button = (Button) nbut;
				button.setDisable(mode);
			}
		}
	}

	public IPlayer player;
	public IGame game;

	public TabHandler() throws NotBoundException {
		

	}

	public void execute(IGame game) throws RemoteException {

	}

	@FXML
	private void quit(ActionEvent event) {
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {

			String address = "rmi://" + FXMain.ip + ":" + FXMain.port + "/" + FXMain.NAME;

			System.out.println(address);

			Registry reg = LocateRegistry.getRegistry(FXMain.ip, FXMain.port);

			// Recuperando o objeto remoto via o servidor de nomes
			game = (IGame) reg.lookup(address);

			player = new Player();

			

			Task<Object> tarefaCargaPg = new Task<Object>() {

				String response;
				
				@Override
				protected String call() throws Exception {
					while (allpane == null) {
					}
					setDisableButtons(true);
//					alert("Iniciado! Esperando adversário");
					response = game.init(player, GameOptions.values()[0]);
					return null;
				}

				@Override
				protected void succeeded() {
					try {
						setDisableButtons(false);
						updateButtons(new Response(response));
						alert("Iniciado! pode jogar");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

				}
			};
			Thread t = new Thread(tarefaCargaPg);
			t.setDaemon(true);
			t.start();
			
			
			
			

		} catch (RemoteException e) {
			alert("Ocorreu um erro no servidor");
			setDisableButtons(false);
//			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		} catch (NotBoundException e1) {
			alert("Erro ao tentar identificar "+ FXMain.NAME +" in "+ FXMain.ip +":"+FXMain.port);
//			e1.printStackTrace();
		}
		
	}
	
	void alert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);

		alert.setTitle("Message");

		alert.setContentText(message);

		alert.show();
		
	}

}
