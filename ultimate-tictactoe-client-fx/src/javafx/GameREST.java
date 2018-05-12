package javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GameREST {

	public String requestGET(String urls) {
		String message = "";
		try {
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				message += output;
			}
			conn.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	// public int getID() throws Exception {
	// String s =
	// requestGET("http://localhost:8080/tictactoews/rest/init?game_option=NORMAL");
	// if(!s.equals("ERROR")) {
	// return Integer.valueOf(s);
	// }else {
	// throw new Exception("Partida cheia!");
	// }
	// }

	// http://localhost:8080/tictactoews/rest/init?game_option=NORMAL
	// public static void main(String[] args) {
	//
	// try {
	// GameREST a = new GameREST();
	// int id = a.getID();
	// System.out.println(id);
	// }catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	public int init() throws Exception {
		String s = requestGET("http://localhost:8080/tictactoews/rest/init?game_option=NORMAL");
		if (!s.equals("ERROR")) {
			return Integer.valueOf(s);
		} else {
			throw new Exception("Partida cheia!");
		}
	}

	public String play(int id_player, int board, int position) throws Exception {
		String s = "http://localhost:8080/tictactoews/rest/play?id_player=" + id_player + "&board=" + board
				+ "&position=" + position;
		String response = requestGET(s);
		if (!response.equals("ERROR")) {
			return response;
		} else {
			throw new Exception("Erro ao jogar");
		}
	}

	public void quit(int player) {
		// TODO Auto-generated method stub

	}

	public int getCredential() {
		// TODO Auto-generated method stub
		return 0;
	}

	// http://localhost:8080/tictactoews/rest/checkTurn?id_player=1

	public boolean checkTurn(int id_player) throws Exception {
		String s = "http://localhost:8080/tictactoews/rest/checkTurn?id_player=" + id_player;
		String response = requestGET(s);
		
		if (response.equals("true")) {
			return true;	
		}else if (response.equals("false")) {
			return false;
		} else {
//			System.err.println(response+": Erro ao jogar");
//			System.exit(0);
			throw new Exception("Erro ao jogar");
			
		}

	}

	public String check(int id_player) throws Exception {
		
		while (!checkTurn(id_player)) {
			Thread.sleep(1000);
		}
		return board();

	}

	public String board() {
		return requestGET("http://localhost:8080/tictactoews/rest/board");
	}

}
