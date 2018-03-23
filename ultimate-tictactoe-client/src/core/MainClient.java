package core;

import java.rmi.Naming;
import java.util.Scanner;

import interfaces.IGame;
import interfaces.IPlayer;
import model.PlayerControl;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;

public class MainClient {
	
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

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
				
		try {
			
			tryReadArgs(args);
			
			
			String address = "rmi://"+ip+":"+port+"/"+NAME;
			
			System.out.println(address);

			Registry reg = LocateRegistry.getRegistry(ip,port);
			
			// Recuperando o objeto remoto via o servidor de nomes
			IGame game = (IGame) reg.lookup(address);
			
			PlayerControl gameClient = new PlayerControl();
			
			gameClient.execute(game);
			
			
		} catch (RemoteException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}	}

}
