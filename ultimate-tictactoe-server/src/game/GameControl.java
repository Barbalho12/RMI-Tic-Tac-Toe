package game;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import enums.GameStatus;

import interfaces.IBoard;
import interfaces.IGame;
import interfaces.IPlayer;

public class GameControl extends UnicastRemoteObject implements IGame, IBoard{

	private static final long serialVersionUID = 1L;

    private UltimateBoard tab = new UltimateBoard();
    
	static int countCredentials = 0;
	static int countPlays = 0;
	
	static boolean endGame = false;

	static Player p1 = null;
	static Player p2 = null;
	
	public GameControl() throws RemoteException { 
		super();
	}
	
	public String init(int credential) throws RemoteException {

		// First player
		if(p1 == null){ 
			p1 = new Player();
			p1.init(credential, X, false);
			
			while(p2 == null){
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
			}
		} 
		// Second player
		else if(p2 == null){
			p2 = new Player();
			p2.init(credential, O, true);
			
			while(p2.isBlocked()){
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
			}	    
		}
		return tab.getState();
	}
	
	private String generateMessage(GameStatus status, IPlayer player) throws RemoteException {

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

	public String play(int credential, int board, int position) throws RemoteException {
		
		// If the game already ended
		if (endGame) {
			return "O jogo acabou.\n";
		}
		// if this is the player 1
		else if(p1.getId() == credential){
						
			// If the moviment is valid
			if (checkValidPlay( p1, board, position)) {
				tab.at(board).setBoardCharAt(position, p1.getName());		
				
				// Allow the p1 to make the movement
				p1.play(board, position);
				
				// Change the turn
				p1.setBlocked(true);
				p2.setBlocked(false);			
				
				// Get the current status of the game after the play
				GameStatus status = checkGame(p1);
				String message =  generateMessage(status, p1);				

				//sum the moves count
				countPlays++;

				while(p1.isBlocked()){
					try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {}
				}
				
				return tab.getState() + message;
			}
			else 
				return "Posicao ou jogada invalida.\n";
		}
		// if this is the player 2 
		else if(p2.getId() == credential ){

			if (checkValidPlay( p2, board, position)) {
				tab.at(board).setBoardCharAt(position, p2.getName());		
				
				// Allow the p1 to make the movement
				p2.play(board, position);
				
				// Change the turn
				p2.setBlocked(true);
				p1.setBlocked(false);			
				
				// Get the current status of the game after the play
				GameStatus status = checkGame(p2);
				String message =  generateMessage(status, p2);				

				//sum the moves count
				countPlays++;

				while(p2.isBlocked()){
					try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {}
				}
				
				return tab.getState() + message;
			}
			else
				return "Posicao ou jogada invalida.\n";
		}

		// If the total number of possible moves was achieved
		if (countPlays >= 82) {
			endGame = true;			
			return "O jogo acabou.\n";
		}				
		return "ERROR";
	}
	
	public int getCredential() throws RemoteException {
		return ++countCredentials;
	}

	private boolean checkValidPlay (Player player, int board, int position) throws RemoteException {		
		// If this is the first turn or the current board is invalid
         if (board == 0 || !tab.at(board).isValid())
        	return false;
        
        if (board <= 0 || board >= 10 || position <= 0 || position >= 10)
        	return false;
                	
        if (p2.getPosition() != 0 && tab.at(p2.getPosition()).isValid()) {
	        if (player.equals(p1) && board != p2.getPosition())
	        	return false;
        }
        
        if (p1.getPosition() != 0 && tab.at(p1.getPosition()).isValid()) {
	        if (player.equals(p2) && board != p1.getPosition())
	        	return false;
        }
        
        // if the position is already occupied
        if (tab.at(board).getBoardCharAt(position) != E) 
        	return false;
        
		return true;
	}
	
	private GameStatus checkGame (Player player) throws RemoteException{		
		int chktable = tab.check_win();
        
        if(chktable == 1 || chktable == -1){
        	endGame = true;
        	
            return GameStatus.GAME_WIN;
        }
        else if(tab.check_draw() == 1){
        	endGame = true;
        	
            return GameStatus.GAME_DRAW;
        }
		
		// verify if someone won the current board
        int boardchk = tab.at(player.getBoard()).check_win();
      
        if (boardchk == 1 || boardchk == -1){
            tab.at(player.getBoard()).setValid(false);
            tab.at(player.getBoard()).setWinner(player.getName());
            
            return GameStatus.BOARD_WIN;
        }
        else if (tab.at(player.getBoard()).check_draw() == 1) {	
            tab.at(player.getBoard()).setValid(false);
        	            
            return GameStatus.BOARD_DRAW;
	    }

        return GameStatus.NORMAL;		
	}
	

}
