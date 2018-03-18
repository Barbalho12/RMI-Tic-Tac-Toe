package core;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.Scanner;

public class GameClient {
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        
		// Recuperando o objeto remoto via o servidor de nomes
		IGame game = (IGame) Naming.lookup("Game");

		int credential = game.getCredential();

		String msg = game.init(credential);

		System.out.println(msg);

		Scanner scanIn = new Scanner(System.in);

		while (true){

			System.out.print("> ");
	        int board = scanIn.nextInt();
	        int position = scanIn.nextInt();

	        String response = game.play(credential, board, position );

	        System.out.println(response);

			try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
		}

	}
}