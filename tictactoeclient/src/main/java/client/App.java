package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
	
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

		} catch (IOException e ) {
			e.printStackTrace();
		}
		return message;
	}

	public int getID() throws Exception {
		String s = requestGET("http://localhost:8080/tictactoews/rest/init?game_option=NORMAL");
		if(!s.equals("ERROR")) {
			return Integer.valueOf(s);
		}else {
			throw new Exception("Partida cheia!");
		}	
	}

	// http://localhost:8080/tictactoews/rest/init?game_option=NORMAL
	public static void main(String[] args) {
		
		try {
			App a = new App();
			int id = a.getID();
			System.out.println(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
