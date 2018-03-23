package game;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import enums.GameStatus;

import interfaces.IBoard;
import interfaces.IGame;

public class GameServer extends UnicastRemoteObject implements IGame, IBoard{

	private static final long serialVersionUID = 1L;

    private UltimateBoard tab = new UltimateBoard();
    
	static int countCredentials = 0;
	static int countPlays = 0;
	
	static boolean endGame = false;

	static Player p1 = null;
	static Player p2 = null;
	
	public GameServer() throws RemoteException { 
		super();
	}
	
	public String init(int credential) throws RemoteException {

		// Primeiro jogador
		if(p1 == null){ 
			p1 = new Player();
			p1.init(credential, X, false);
			
			while(p2 == null){
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
			}

		// Segundo jogador
		} else if(p2 == null){
			p2 = new Player();
			p2.init(credential, O, true);
			
			while(p2.isBlocked()){
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
			}
			return tab.getState();		    
		}
		return tab.getState();
	}

	public String play(int credential, int board, int position) throws RemoteException {
		
		if (endGame) {
			return "O jogo acabou.\n";
		}
		else if(p1.getId() == credential){
						
			if (checkValidPlay( p1, board, position)) {
				tab.at(board).setBoardCharAt(position, p1.getName());		
				
				p1.play(board,position);
				
				p1.setBlocked(true);
				p2.setBlocked(false);
	
				while(p1.isBlocked()){
					try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	
	                }
				}
				countPlays++;
				
				GameStatus status = checkGame(p1);
				String message = "";
				
				switch (status) {
					case BOARD_DRAW: 
						message = "\nDeu Velha!\n";
						break;
					case GAME_DRAW:
						message = "\nDeu Velha!\n";
						break;
					case BOARD_WIN:
						message = "\nJogador "+ p1.getName() +" venceu o tabuleiro " + p1.getBoard() + "!\n";
						break;
					case GAME_WIN:
						message = "\nJogador " + p1.getName() + " venceu o jogo!\n";
						break;
					default:
				}
					
				return tab.getState() + message;
			}
			else 
				return "Posicao ou jogada invalida.\n";
		}
		else if(p2.getId() == credential ){

			if (checkValidPlay( p2, board, position)) {
				tab.at(board).setBoardCharAt(position, p2.getName());

				p2.play(board, position);
				p2.setBlocked(true);
				p1.setBlocked(false);
	
				while(p2.isBlocked()){
					try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	
	                }
				}
				countPlays++;				
				
				GameStatus status = checkGame(p2);				
				String message = "";
				
				switch (status) {
					case BOARD_DRAW: 
						message = "\nDeu Velha!\n";
						break;
					case GAME_DRAW:
						message = "\nDeu Velha!\n";
						break;
					case BOARD_WIN:
						message = "\nJogador "+ p2.getName() +" venceu o tabuleiro " + p2.getBoard() + "!\n";
						break;
					case GAME_WIN:
						message = "\nJogador " + p2.getName() + " venceu o jogo!\n";
						break;
					default:
				}
					
				return tab.getState() + message;
			}
			else
				return "Posicao ou jogada invalida.\n";
		}

		if (countPlays == 82) {
			endGame = true;
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
	
        if (p2.getPosition() != 0 && player.equals(p1) && board != p2.getPosition() &&  tab.at(p2.getPosition()).isValid())
        	return false;
        
        if (p1.getPosition() != 0 && player.equals(p2) && board != p1.getPosition() &&  tab.at(p1.getPosition()).isValid())
        	return false;
        
        // if the position is already occupied
        if (tab.at(board).getBoardCharAt(position) != E) 
        	return false;
        
		return true;
	}
	
	private GameStatus checkGame (Player player) throws RemoteException{		
		// verify if someone won the current board
        int boardchk = tab.at(player.getBoard()).check_win();
      
        if (boardchk == 1 || boardchk == -1){
            tab.at(player.getBoard()).setValid(false);
            tab.at(player.getBoard()).setWinner(player.getName());
                        
            return GameStatus.BOARD_WIN;
        }
        else if (tab.at(player.getBoard()).check_draw() == 1) {	
        	            
            return GameStatus.BOARD_DRAW;
	    }
        
        int chktable = tab.check_win();
        
        if(chktable == 1 || chktable == -1){
            return GameStatus.GAME_WIN;
        }
        else if(tab.check_draw() == 1){
            return GameStatus.GAME_DRAW;
        }

        return GameStatus.NORMAL;		
	}
	

}
