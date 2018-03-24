package ai;

import game.BasicBoard;
import game.UltimateBoard;
import interfaces.IBoard;

public class AlphaBetaPruning  implements IBoard{

	// O move position
	static int posmin;
	
	/**
	 * Searches a valid board and return it's number
	 * @param tab    - the ultimate board
	 * @param k      - the current board number
	 * @param player - the player number id (1 for 'X', 2 for 'O')
	 * 
	 * @return the number of the valid board
	 */
	public static int findK(UltimateBoard tab, int k, int player){
	    int tempk = k;
	    int htemp;
	    
	    //Sets the worse case for the current player
	    if(player==2){
	        htemp = 200;
	    }
	    else{
	        htemp = -200;
	    }

	    //for all boards
	    for(int i=1; i<=9; i++){
	    	
	    	// if it is the player 'O' 
	        if(player==2){
	        	// check if it's valid and the heuristic is lower than the worse case
	            if(tab.at(i).isValid() && check_heuristic(tab.at(i)) < htemp){
	                htemp = check_heuristic(tab.at(i));
	                tempk = i;
	            }
	        }
	    	// if it is the player 'X' 
	        else{
	        	// check if it's valid and the heuristic is higher than the worse case
	            if(tab.at(i).isValid() && check_heuristic(tab.at(i)) > htemp){
	                htemp = check_heuristic(tab.at(i));
	                tempk = i;
	            }
	        }
	    }
	    return tempk;
	}
	
	/**
	 * Calculates the minmax with alphabeta pruning and 
	 * sets the posmin variable at the best spot for the 'O' player
	 * 
	 * @param boardnum      - current board number
	 * @param depth         - the maximum depth of the tree that the minmax will check
	 * @param a             - the alpha value
	 * @param b             - the beta value
	 * @param player        - the player number id (1 for 'X', 2 for 'O')
	 * @param tab           - the ultimateboard
	 * @param profundidade  - the maximum depth, same as "depth", that will work as a verifier
	 * 
	 * @return the best move heuristic
	 */
	public static int alphabeta(int boardnum, int depth, int a, int b, int player, UltimateBoard tab, int profundidade){
	    int aux;

	    //base case
	    // if the 'X' won the current board
	    if(tab.at(boardnum).check_win() == 1){
	        //verifies if 'X' won the ultimateboard
	        if (tab.check_win() == 1){
	            return 200;
	        }
	        return 100;
	    }
	    // if the 'O' won the current board
	    else if (tab.at(boardnum).check_win() == -1){
	        //verifies if 'O' won the ultimateboard
	        if (tab.check_win() == -1){
	            return -200;
	        }
	        return -100;
	    }
	    // if it's a draw
	    else if (tab.at(boardnum).check_draw() == 1){
	        return 0;
	    }
	    // if the minmax reached the maximum depth
	    else if (depth == 0){
	        return tab.at(boardnum).getHeuristic();
	    }

	    if(!tab.at(boardnum).isValid()){
	        boardnum = findK(tab, boardnum, player);
	    }

	    // 'X'
	    if(player == 1){
	    	// for each spot at the best board found
	        for(int i = 1; i <= 9; i++){
	        	
	        	// if this spot isn't taken
	            if(tab.at(boardnum).getBoardCharAt(i) == '_'){

	            	// place the 'X' and go to one level down on the tree
	                tab.at(boardnum).setBoardCharAt(i, 'X');
	                aux = alphabeta(i, depth -1, a, b, 2, tab, profundidade);
	                
	                //clear the current place
	                tab.at(boardnum).setBoardCharAt(i, '_');


	                //if the heuristic can be maximized
	                if(a < aux){a = aux;}
	                
	                //prune
	                if(a >= b){break;}
	            }
	        }
	        return a;
	    }
	    // 'O'
	    if(player == 2){
	    	// for each spot at the best board found
	        for(int i = 1; i <= 9; i++){

	        	// if this spot isn't taken
	            if(tab.at(boardnum).getBoardCharAt(i) == '_'){

	            	// place the 'O' and go to one level down on the tree
	                tab.at(boardnum).setBoardCharAt(i, 'O');
	                aux = alphabeta(i, depth -1, a, b, 1, tab, profundidade);

	                //clear the current place
	                tab.at(boardnum).setBoardCharAt(i, '_');

	                //if the heuristic can be minimized
	                if(b > aux){
	                    b = aux;
	                    if(depth == profundidade){
	                        posmin = i;
	                    }
	                }
	                
	                //prune
	                if(a >= b){break;}
	            }
	        }
	        return b;
	    }
	    return 0; 	
	}
	
	/**
	 * Generate a heuristic value for the board state
	 * 
	 * the heuristic is based on the minmax tradeoff 
	 * the O (AI) tries to minimize and the X (player) tries to maximize the value
	 * 
	 * @return heuristic value in int
	 */
	public static int check_heuristic(BasicBoard bboard){

	    // Sets back to 0 to recalculate
		bboard.setHeuristic(0);

	    int chkW = bboard.check_win();

	    if(chkW == 1){
	    	bboard.setHeuristic(100);
	        return 100;
	    }
	    else if (chkW == -1){
	    	bboard.setHeuristic(-100);
	        return -100;
	    }

	    // If nobody won, sum all the heuristics of the lines
	    int chkT = check_two(bboard);
	    int chkO = check_one(bboard);

	    // Sum both values 
	    bboard.setHeuristic(chkT + chkO);
	  

	    return bboard.getHeuristic();
	}
	
	/**
	 * Verifies if someone is almost winning (2 spots filled in a roll)
	 * @return 10 if it is the X, else -10 if it is the O 
	 */
	public static int check_two(BasicBoard bboard){
	    int sum = 0;
	    
	    /* Good states for X */
	    
	    final int XXE = X+X+E;
	    
		/* Line Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(2) + bboard.getBoardCharAt(3) == XXE)  sum += 10; 
	    if (bboard.getBoardCharAt(4) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(6) == XXE)  sum += 10;
	    if (bboard.getBoardCharAt(7) + bboard.getBoardCharAt(8) + bboard.getBoardCharAt(9) == XXE)  sum += 10;
	      
	    /* Column Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(4) + bboard.getBoardCharAt(7) == XXE)  sum += 10;
	    if (bboard.getBoardCharAt(2) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(8) == XXE)  sum += 10;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(6) + bboard.getBoardCharAt(9) == XXE)  sum += 10;
	       
	    /* Diagonal Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(9) == XXE)  sum += 10;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(7) == XXE)	sum += 10;
	    
	    /* Good states for O */
	    
	    final int OOE = O+O+E;
	    
	    /* Line Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(2) + bboard.getBoardCharAt(3) == OOE)  sum += -10; 
	    if (bboard.getBoardCharAt(4) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(6) == OOE)  sum += -10;
	    if (bboard.getBoardCharAt(7) + bboard.getBoardCharAt(8) + bboard.getBoardCharAt(9) == OOE)  sum += -10;
	      
	    /* Column Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(4) + bboard.getBoardCharAt(7) == OOE)  sum += -10;
	    if (bboard.getBoardCharAt(2) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(8) == OOE)  sum += -10;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(6) + bboard.getBoardCharAt(9) == OOE)  sum += -10;
	       
	    /* Diagonal Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(9) == OOE)  sum += -10;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(7) == OOE)	sum += -10;

	   return sum;
	}
	
	/**
	 * Verifies if someone has a good start in a roll
	 * @return 1 if it is the X, else -1 if it is the O 
	 */
	public static int check_one(BasicBoard bboard){
	    
		int sum = 0;
	    
	    /* Good states for X */
	    
	    final int EEX = E+E+X;
	    
	    /* Line Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(2) + bboard.getBoardCharAt(3) == EEX)  sum += 1; 
	    if (bboard.getBoardCharAt(4) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(6) == EEX)  sum += 1;
	    if (bboard.getBoardCharAt(7) + bboard.getBoardCharAt(8) + bboard.getBoardCharAt(9) == EEX)  sum += 1;
	      
	    /* Column Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(4) + bboard.getBoardCharAt(7) == EEX)  sum += 1;
	    if (bboard.getBoardCharAt(2) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(8) == EEX)  sum += 1;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(6) + bboard.getBoardCharAt(9) == EEX)  sum += 1;
	       
	    /* Diagonal Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(9) == EEX)  sum += 1;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(7) == EEX)	sum += 1;
	    
	    /* Good states for O */
	    
	    final int EEO = E+E+O;
	    
	    /* Line Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(2) + bboard.getBoardCharAt(3) == EEO)  sum += -1; 
	    if (bboard.getBoardCharAt(4) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(6) == EEO)  sum += -1;
	    if (bboard.getBoardCharAt(7) + bboard.getBoardCharAt(8) + bboard.getBoardCharAt(9) == EEO)  sum += -1;
	      
	    /* Column Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(4) + bboard.getBoardCharAt(7) == EEO)  sum += -1;
	    if (bboard.getBoardCharAt(2) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(8) == EEO)  sum += -1;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(6) + bboard.getBoardCharAt(9) == EEO)  sum += -1;
	       
	    /* Diagonal Check*/
	    if (bboard.getBoardCharAt(1) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(9) == EEO)  sum += -1;
	    if (bboard.getBoardCharAt(3) + bboard.getBoardCharAt(5) + bboard.getBoardCharAt(7) == EEO)	sum += -1;

	   return sum;
	}
	
}

