package core;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import game.GameServer;
import interfaces.IGame;

public class Main {

	static String ip = "localhost";
	static int port = 1099;
	static final String NAME = "Game";
	
	static void tryReadArgs(String args[]){
		if(args.length > 0) {
			ip = args[0];
		}
		if(args.length > 1) {
			port = Integer.parseInt(args[1]);
		}
	}
	

	public static void main (String args[]) {
		
		try {
			
			tryReadArgs(args);
			
			
			String address = "rmi://"+ip+":"+port+"/"+NAME;
			
			System.out.println(address);

			// Criando o objeto remoto
			IGame game = new GameServer(); 

			System.setProperty("java.rmi.server.hostname", ip);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(port);
            
            registry.rebind(address, game);

		} catch (RemoteException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
