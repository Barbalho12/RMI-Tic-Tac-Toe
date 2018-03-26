package model;

import java.rmi.RemoteException;
import java.util.Scanner;

import enums.GameOptions;
import interfaces.IGame;
import interfaces.IPlayer;

public class PlayerControl {

	private static Scanner scanIn;

	public void execute(IGame game) throws RemoteException {
		scanIn = new Scanner(System.in);
		IPlayer player = new Player();
		int board, position;

		System.out.print("\nEscolha o modo de jogo.\n0 - Online contra outro jogador.\n1 - Contra uma I.A.\n\n> ");

		int option = scanIn.nextInt();

		while (option != 0 && option != 1) {
			option = scanIn.nextInt();
		}

		String msg = game.init(player, GameOptions.values()[option]);

		if (msg.equals("\nO jogo foi encerrado pelo oponente!\n")) {
			System.out.println("\nO jogo foi encerrado pelo oponente!\n");
			System.exit(0);
		}

		System.out.print("\nTutorial> Digite 2 números separados por um espaço.\n");
		System.out.print("Tutorial> O 1º é a posição no tabuleiro externo.\n");
		System.out.println("Tutorial> O 2º é a posição no tabuleiro interno.\n");

		System.out.println(msg);

		while (true) {

			System.out.print(player.getName() + ">> ");

			board = tryReadValue(game, player);
			position = tryReadValue(game, player);

			String response = game.play(player, board, position);

			if (response.equals("\nO jogo foi encerrado pelo oponente!\n")) {
				System.out.println("\nO jogo foi encerrado pelo oponente!\n");
				System.exit(0);
			}

			System.out.println(response);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}

	}

	static int tryReadValue(IGame game, IPlayer player) {
		int value = 0;
		String sboard = scanIn.next();
		if (sboard.equalsIgnoreCase("q")) {
			try {
				game.quit(player);
				System.exit(0);
			} catch (RemoteException e) {
			}
		}
		sboard = sboard.trim().replace(" ", "");
		
		while ((sboard.charAt(0) < '1' || sboard.charAt(0) > '9')) {
			sboard = scanIn.next();
			if (sboard.equalsIgnoreCase("q")) {
				try {
					game.quit(player);
					System.exit(0);
				} catch (RemoteException e) {
				}

			}
			sboard = sboard.trim().replace(" ", "");
		}
		value = Integer.valueOf(sboard);
		return value;

	}
}