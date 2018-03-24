package model;

import java.rmi.RemoteException;
import java.util.Scanner;

import enums.GameOptions;
import interfaces.IGame;
import interfaces.IPlayer;

public class PlayerControl {
	
	private Scanner scanIn;

	public void execute(IGame game) throws RemoteException  {
//		scanIn = new Scanner(System.in);		
//		IPlayer player = new Player();
//		int board, position;
//		
//		System.out.print("\nEscolha o modo de jogo.\n0 - Online contra outro jogador.\n1 - Contra uma I.A.\n\n> ");
//		
//        int option = scanIn.nextInt();
//        
//        while (option != 0  && option != 1) {
//        	option = scanIn.nextInt();
//        }
//        
//		String msg = game.init(player, GameOptions.values()[option]);
//
//		System.out.print("\nTutorial> Digite 2 números separados por um espaço.\n");
//		System.out.print("Tutorial> O 1º é a posição no tabuleiro externo.\n");
//		System.out.println("Tutorial> O 2º é a posição no tabuleiro interno.\n");
//
//		System.out.println(msg);
//
//
//		while (true){
//
//			System.out.print( player.getName() + "> ");
//	        board = scanIn.nextInt();
//	        position = scanIn.nextInt();
//
//	        String[] response = game.play(player, board, position );
//
//	        System.out.println(response[81]);
//
//			try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//
//            }
//		}

	}
	
	
}