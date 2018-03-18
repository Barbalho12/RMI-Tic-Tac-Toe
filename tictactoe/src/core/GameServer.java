package core;

import java.rmi.*;
import java.rmi.server.*;

public class GameServer extends UnicastRemoteObject implements IGame, IBoard{

	private static final long serialVersionUID = 1L;

    private UltimateBoard tab = new UltimateBoard();
    
	static int countCredentials = 0;
	static int countPlays = 0;
	
	static boolean endGame = false;

	static Person p1 = null;
	static Person p2 = null;
	
	public GameServer() throws RemoteException { 
		super();
	}
	
	public String init(int credential) {

		// Primeiro jogador
		if(p1 == null){ 
			p1 = new Person(credential, X, false);

			while(p2 == null){
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
			}

		// Segundo jogador
		} else if(p2 == null){
			p2 = new Person(credential, O, true);

			while(p2.blocked){
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
			}
			return tab.getState();		    
		}
		return tab.getState();
	}

	public String play(int credential, int board, int position) {
		
		if (endGame) {
			return "O jogo acabou.\n";
		}
		else if(p1.credential == credential){
						
			if (checkValidPlay( p1, board, position)) {
				tab.at(board).setBoardCharAt(position, p1.playerName);		
				
				p1.choosenBoard = board;
				p1.choosenPosition = position;
				p1.blocked = true;
				p2.blocked = false;
	
				while(p1.blocked){
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
						message = "\nJogador "+ p1.playerName +" venceu o tabuleiro " + p1.choosenBoard + "!\n";
						break;
					case GAME_WIN:
						message = "\nJogador " + p1.playerName + " venceu o jogo!\n";
						break;
					default:
				}
					
				return tab.getState() + message;
			}
			else 
				return "Posicao ou jogada invalida.\n";
		}
		else if(p2.credential == credential ){

			if (checkValidPlay( p2, board, position)) {
				tab.at(board).setBoardCharAt(position, p2.playerName);

				p2.choosenBoard = board;
				p2.choosenPosition = position;
				p2.blocked = true;
				p1.blocked = false;
	
				while(p2.blocked){
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
						message = "\nJogador "+ p2.playerName +" venceu o tabuleiro " + p2.choosenBoard + "!\n";
						break;
					case GAME_WIN:
						message = "\nJogador " + p2.playerName + " venceu o jogo!\n";
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

	public int getCredential() {
		return ++countCredentials;
	}

	private boolean checkValidPlay (Person player, int board, int position) {		
		// If this is the first turn or the current board is invalid
        if (board == 0 || !tab.at(board).isValid())
        	return false;
        
        if (board <= 0 || board >= 10 || position <= 0 || position >= 10)
        	return false;
	
        if (p2.choosenPosition != 0 && player.equals(p1) && board != p2.choosenPosition &&  tab.at(p2.choosenPosition).isValid())
        	return false;
        
        if (p1.choosenPosition != 0 && player.equals(p2) && board != p1.choosenPosition &&  tab.at(p1.choosenPosition).isValid())
        	return false;
        
        // if the position is already occupied
        if (tab.at(board).getBoardCharAt(position) != E) 
        	return false;
        
		return true;
	}
	
	private GameStatus checkGame (Person player) {		
		// verify if someone won the current board
        int boardchk = tab.at(player.choosenBoard).check_win();
      
        if (boardchk == 1 || boardchk == -1){
            tab.at(player.choosenBoard).setValid(false);
            tab.at(player.choosenBoard).setWinner(player.playerName);
                        
            return GameStatus.BOARD_WIN;
        }
        else if (tab.at(player.choosenBoard).check_draw() == 1) {	
        	            
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
	


	public static void main (String args[]) throws AlreadyBoundException, java.net.MalformedURLException{
		try {
            
			// Criando o objeto remoto
			GameServer game = new GameServer();
			
			// Registrando esse objeto no serviço de nomes
			Naming.bind("Game", game);
            
		} catch (RemoteException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}




	

}
