package core;

import java.rmi.RemoteException;
import java.util.Scanner;

public class GameClient {
	
	private Scanner scanIn;

	public void execute(IGame game) throws RemoteException  {
		
//		String ip = "localhost";
//		int port = 1099;
//		String name = "Game";
//		
//		if(args.length > 0) {
//			ip = args[0];
//		}
//		if(args.length > 1) {
//			port = Integer.parseInt(args[1]);
//		}
//		
//		String address = "rmi://"+ip+":"+port+"/"+name;
//		System.out.println(address);
//		
//		Registry reg = LocateRegistry.getRegistry(ip,port);
//		
//		// Recuperando o objeto remoto via o servidor de nomes
//		IGame game = (IGame) reg.lookup(address);
//
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