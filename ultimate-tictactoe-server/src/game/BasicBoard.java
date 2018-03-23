package game;

import interfaces.IBoard;

/**
 * This class is responsible for the management of one single tic-tac-toe board.
 */
public class BasicBoard implements IBoard{
	private char winner;
    private char[] board;
   	private boolean draw;
    private boolean valid;
    private int heuristic;
    
	public BasicBoard () {
		board = new char[10];
		
		for(int i = 1; i <= 9; i++)
	        board[i] = E;

	    this.winner = E;
	    this.heuristic = 0;
	    
	    this.valid = true;
	    this.draw = false;
	}
    
	/**
	 * Print the board state into the console
	 */
	public void print_board(){
	    for (int i = 1; i <= 9; i++){
	        System.out.print(board[i]);
	        if (i % 3 == 0)
	        	System.out.println();
	    }
	    System.out.println();
	}
	
	/**
	 * Generate a heuristic value for the board state
	 * 
	 * the heuristic is based on the minmax tradeoff 
	 * the O (AI) tries to minimize and the X (player) tries to maximize the value
	 * 
	 * @return heuristic value in int
	 */
	public int check_heuristic(){

	    // Sets back to 0 to recalculate
	    this.heuristic = 0;

	    int chkW = check_win();

	    if(chkW == 1){
	       this.heuristic = 100;
	        return 100;
	    }
	    else if (chkW == -1){
	        this.heuristic = -100;
	        return -100;
	    }

	    // If nobody won, sum all the heuristics of the lines
	    int chkT = check_two();
	    int chkO = check_one();

	    // Sum both values 
	    this.heuristic = chkT + chkO;

	    return this.heuristic;
	}

	/**
	 * Verifies if someone won the game
	 * @return 1 if the X player won, else -1 if the player O won
	 */
	public int check_win(){
	
		int XXX = X+X+X;
		
		if ((board[1] + board[2] + board[3] == XXX) ||
		   ( board[4] + board[5] + board[6] == XXX) ||
		   ( board[7] + board[8] + board[9] == XXX) ||
		   ( board[1] + board[4] + board[7] == XXX) ||
		   ( board[2] + board[5] + board[8] == XXX) ||
		   ( board[3] + board[6] + board[9] == XXX) ||
		   ( board[1] + board[5] + board[9] == XXX) ||
		   ( board[3] + board[5] + board[7] == XXX)) {
			
			return 1;
		}
		
		int OOO = O+O+O;
		
		if ((board[1] + board[2] + board[3] == OOO) ||
		   ( board[4] + board[5] + board[6] == OOO) ||
		   ( board[7] + board[8] + board[9] == OOO) ||
		   ( board[1] + board[4] + board[7] == OOO) ||
		   ( board[2] + board[5] + board[8] == OOO) ||
		   ( board[3] + board[6] + board[9] == OOO) ||
		   ( board[1] + board[5] + board[9] == OOO) ||
		   ( board[3] + board[5] + board[7] == OOO)) {
			
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Verifies if the game ended-up in a draw
	 * @return 1 if it was a draw, else 0
	 */
	public int check_draw(){
		
		// Checks if no one won and all spaces were filled
	    if ((check_win() == 0) && (board[1] != E) && (board[2] != E) &&
	        (board[3] != E)    && (board[4] != E) && (board[5] != E) &&
	        (board[6] != E)    && (board[7] != E) && (board[8] != E) &&
	        (board[9] != E))
	    {
	        this.setValid(false);
	        this.setDraw(true);
	        return 1;
	    }
	    else{
	        this.setValid(true);
	        this.setDraw(false);
	        return 0;
	    }
	}
	
	/**
	 * Verifies if someone is almost winning (2 spots filled in a roll)
	 * @return 10 if it is the X, else -10 if it is the O 
	 */
	public int check_two(){
	    int sum = 0;
	    
	    /* Good states for X */
	    
	    final int XXE = X+X+E;
	    
		/* Line Check*/
	    if (board[1] + board[2] + board[3] == XXE)  sum += 10; 
	    if (board[4] + board[5] + board[6] == XXE)  sum += 10;
	    if (board[7] + board[8] + board[9] == XXE)  sum += 10;
	      
	    /* Column Check*/
	    if (board[1] + board[4] + board[7] == XXE)  sum += 10;
	    if (board[2] + board[5] + board[8] == XXE)  sum += 10;
	    if (board[3] + board[6] + board[9] == XXE)  sum += 10;
	       
	    /* Diagonal Check*/
	    if (board[1] + board[5] + board[9] == XXE)  sum += 10;
	    if (board[3] + board[5] + board[7] == XXE)	sum += 10;
	    
	    /* Good states for O */
	    
	    final int OOE = O+O+E;
	    
	    /* Line Check*/
	    if (board[1] + board[2] + board[3] == OOE)  sum += -10; 
	    if (board[4] + board[5] + board[6] == OOE)  sum += -10;
	    if (board[7] + board[8] + board[9] == OOE)  sum += -10;
	      
	    /* Column Check*/
	    if (board[1] + board[4] + board[7] == OOE)  sum += -10;
	    if (board[2] + board[5] + board[8] == OOE)  sum += -10;
	    if (board[3] + board[6] + board[9] == OOE)  sum += -10;
	       
	    /* Diagonal Check*/
	    if (board[1] + board[5] + board[9] == OOE)  sum += -10;
	    if (board[3] + board[5] + board[7] == OOE)	sum += -10;

	   return sum;
	}
	
	/**
	 * Verifies if someone has a good start in a roll
	 * @return 1 if it is the X, else -1 if it is the O 
	 */
	public int check_one(){
	    
		int sum = 0;
	    
	    /* Good states for X */
	    
	    final int EEX = E+E+X;
	    
	    /* Line Check*/
	    if (board[1] + board[2] + board[3] == EEX)  sum += 1; 
	    if (board[4] + board[5] + board[6] == EEX)  sum += 1;
	    if (board[7] + board[8] + board[9] == EEX)  sum += 1;
	      
	    /* Column Check*/
	    if (board[1] + board[4] + board[7] == EEX)  sum += 1;
	    if (board[2] + board[5] + board[8] == EEX)  sum += 1;
	    if (board[3] + board[6] + board[9] == EEX)  sum += 1;
	       
	    /* Diagonal Check*/
	    if (board[1] + board[5] + board[9] == EEX)  sum += 1;
	    if (board[3] + board[5] + board[7] == EEX)	sum += 1;
	    
	    /* Good states for O */
	    
	    final int EEO = E+E+O;
	    
	    /* Line Check*/
	    if (board[1] + board[2] + board[3] == EEO)  sum += -1; 
	    if (board[4] + board[5] + board[6] == EEO)  sum += -1;
	    if (board[7] + board[8] + board[9] == EEO)  sum += -1;
	      
	    /* Column Check*/
	    if (board[1] + board[4] + board[7] == EEO)  sum += -1;
	    if (board[2] + board[5] + board[8] == EEO)  sum += -1;
	    if (board[3] + board[6] + board[9] == EEO)  sum += -1;
	       
	    /* Diagonal Check*/
	    if (board[1] + board[5] + board[9] == EEO)  sum += -1;
	    if (board[3] + board[5] + board[7] == EEO)	sum += -1;

	   return sum;
	}

	public char getWinner() {
		return winner;
	}

	public void setWinner(char winner) {
		this.winner = winner;
	}

	public char getBoardCharAt(int i) {
		return board[i];
	}

	public void setBoardCharAt(int i, char a) {
		board[i] = a;
	}

	public boolean isValid() {
		this.check_draw();
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	
	 public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
}
