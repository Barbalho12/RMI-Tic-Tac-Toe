package game;

import java.rmi.RemoteException;

import enums.GameStatus;

import interfaces.IBoard;
import interfaces.IPlayer;

public final class GameCheck implements IBoard {

	private GameCheck() {
		super();
	}
	
	public static boolean checkValidPlay (IPlayer player, int board, int position, UltimateBoard tab, IPlayer p1, IPlayer p2) throws RemoteException {		
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
	
	public static  GameStatus checkGame (IPlayer player, UltimateBoard tab, GameControl gameCon) throws RemoteException{		
		int chktable = tab.check_win();
		
		if(chktable == 1 || chktable == -1){
			gameCon.endGame = true;        	
            return GameStatus.GAME_WIN;
        }
        else if(tab.check_draw() == 1){
        	gameCon.endGame = true;
        	
            return GameStatus.GAME_DRAW;
        }
		
		// verify if someone won the current board
        int boardchk = tab.at(player.getBoard()).check_win();
		
        if (boardchk == 1 || boardchk == -1){
            tab.at(player.getBoard()).setValid(false);
            tab.at(player.getBoard()).setWinner(player.getName());
            
            chktable = tab.check_win();
            if(chktable == 1 || chktable == -1){
            	gameCon.endGame = true;        	
                return GameStatus.GAME_WIN;
            }
            
            return GameStatus.BOARD_WIN;
        }
        else if (tab.at(player.getBoard()).check_draw() == 1) {	
            tab.at(player.getBoard()).setValid(false);
        	            
            return GameStatus.BOARD_DRAW;
	    }

        return GameStatus.NORMAL;		
	}
}
