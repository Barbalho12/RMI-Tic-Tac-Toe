package com.ufrn.game;

//import com.ufrn.ai.AIPlayer;

import com.ufrn.enums.GameOptions;
import com.ufrn.enums.GameStatus;

import com.ufrn.interfaces.IGame;
import com.ufrn.interfaces.IBoard;
import com.ufrn.interfaces.IPlayer;

public class GameControl implements IGame, IBoard {

	private UltimateBoard tab = new UltimateBoard();

	public boolean quited = false;

	private IPlayer p1 = null;
	
	public IPlayer getPlayer(int id) {
		if(id == 1) return p1;
		if(id == 2) return p2;
		return null;
	}

	public IPlayer getP1() {
		return p1;
	}

	public void setP1(IPlayer p1) {
		this.p1 = p1;
	}

	private IPlayer p2 = null;

	public static boolean endGame = false;

	private int countCredentials = 0;
	private int countPlays = 0;

	public int getCountPlays() {
		return countPlays;
	}

	public void setCountPlays(int countPlays) {
		this.countPlays = countPlays;
	}

	private GameOptions option;

	public String init(IPlayer player, GameOptions option) {
		this.option = option;

		switch (option) {
		case NORMAL:
			return initNormal(player);
		case AI:
			// return initAI(player);
		default:
			return "ERROR";
		}
	}

	public String play(IPlayer player, int board, int position) {
		switch (option) {
		case NORMAL:
			return playNormal(player, board, position);
		case AI:
			// return playAI(player, board, position);
		default:
			return "ERROR";
		}
	}

	public void exit(IPlayer player) {
		quited = true;
		
		p1.setBlocked(false);
		if(p2 != null)
			p2.setBlocked(false);
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public int getCredential() {
		return ++countCredentials;
	}

	private String initNormal(IPlayer player) {
		// First player
		if (p1 == null) {
			p1 = player;
			p1.init(this.getCredential(), X, false);

//			while (p2 == null) {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//				}
//			}
		}
		// Second player
		else if (p2 == null) {
			p2 = player;
			p2.init(this.getCredential(), O, true);

//			while (p2.isBlocked()) {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//				}
//			}
		}
		if (quited)
			return "\nO jogo foi encerrado pelo oponente!\n";

		return tab.getState();
	}
	
	public IPlayer lastPlayer = null;

	private String playNormal(IPlayer player, int board, int position) {
		
		
	
		if (quited)
			return "\nO jogo foi encerrado pelo oponente!\n";
		// If the game already ended
		if (tab.check_win() != 0) {
			endGame = true;
			return "O jogo acabou. O jogador " + tab.getWinner() + " ganhou!\n";
		} else if (tab.check_draw() == 1) {
			endGame = true;
			return "O jogo acabou. Deu velha!\n";
		} else if(player.equals(lastPlayer)){
				return "\nVoce ja fez sua jogada!\n";
		}

		// if this is the player 1
		else if (p1.getId() == player.getId()) {

			// If the movement is valid
			if (GameCheck.checkValidPlay(p1, board, position, tab, p1, p2)) {
				lastPlayer = p1;
				return makePlay(p1, board, position, p2);
			} else
				return "Posicao ou jogada invalida.\n";
		}
		// if this is the player 2
		else if (p2.getId() == player.getId()) {

			if (GameCheck.checkValidPlay(p2, board, position, tab, p1, p2)) {
				lastPlayer = p2;
				return makePlay(p2, board, position, p1);
			} else
				return "Posicao ou jogada invalida.\n";
		}
		return countPlays();
	}

	private String makePlay(IPlayer player, int board, int position, IPlayer other) {
		tab.at(board).setBoardCharAt(position, player.getName());

		// Allow the player to make the movement
		player.play(board, position);

		// Get the current status of the game after the play
		GameStatus status = GameCheck.checkGame(player, tab, this);

		// Change the turn
		player.setBlocked(true);
		other.setBlocked(false);

		// String message = generateMessage(status, player);

		if (status != GameStatus.GAME_WIN) {
//			while (player.isBlocked() && !(other instanceof AIPlayer)) {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//				}
//			}
			if (endGame)
				return tab.getState() + "\nVoce perdeu!\n";
			if (quited)
				return "\nO jogo foi encerrado pelo oponente!\n";
			// return tab.getState() + message;
			return tab.getState();
		}

		if (quited)
			return "\nO jogo foi encerrado pelo oponente!\n";

		return "Voce ganhou!";
	}

	@SuppressWarnings("unused")
	private String generateMessage(GameStatus status, IPlayer player) {

		switch (status) {
		case BOARD_DRAW:
			return "\nDeu Velha!\n";
		case GAME_DRAW:
			return "\nDeu Velha!\n";
		case BOARD_WIN:
			return "\nJogador " + player.getName() + " venceu o tabuleiro " + player.getBoard() + "!\n";
		case GAME_WIN:
			return "\nJogador " + player.getName() + " venceu o jogo!\n";
		default:
		}
		return "";
	}

	private String countPlays() {
		countPlays++;

		// If the total number of possible moves was achieved
		if (countPlays >= 82) {
			endGame = true;
			return "O jogo acabou.\n";
		}
		return "";
	}

	public UltimateBoard getTab() {
		return tab;
	}

	public void setTab(UltimateBoard tab) {
		this.tab = tab;
	}

	private static GameControl uniqueInstance = new GameControl();

	private GameControl() {
	}

	public static GameControl getInstance() {
		return uniqueInstance;
	}

}
