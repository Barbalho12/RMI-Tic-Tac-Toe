package core;

import java.rmi.RemoteException;
import java.util.Scanner;

import interfaces.IGame;

public class GameClient {
	
	private Scanner scanIn;

	public void execute(IGame game) throws RemoteException  {
		
		int credential = game.getCredential();

		String msg = game.init(credential);

		System.out.println(msg);

		scanIn = new Scanner(System.in);

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