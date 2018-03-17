package core;


/**
 * This class is responsible for the management of one single tic-tac-toe board.
 */

public class Board {

    private char winner;
    private char[] board;
   	private boolean draw;
    private boolean valid;
    private int heuristic;
    
	public Board () {
		board = new char[10];
		
		for(int i = 1; i <= 9; i++)
	        board[i] = '_';

	    this.winner = '_';
	    this.heuristic = 0;
	    
	    this.valid = true;
	    this.draw = false;
	}
    
	/**
	 * Print the board state into the console
	 */
	public void print_board(){
	    int i;
	    for (i = 1; i <= 9; i++){
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

	    //sets back to 0 to recalculate
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

	    //if nobody won, sum all the heuristics of the lines
	    int chkT = check_two();
	    int chkO = check_one();

	    //sum both values and change the return signal
	    this.heuristic = -(chkT + chkO);

	    return this.heuristic;
	}

	/**
	 * Verifies if someone won the game
	 * @return 1 if the 'X' player won, else -1 if the player 'O' won
	 */
	public int check_win(){
	    if ((board[1] == 'X' && board[2] == 'X' && board[3] == 'X') ||
	        (board[4] == 'X' && board[5] == 'X' && board[6] == 'X') ||
	        (board[7] == 'X' && board[8] == 'X' && board[9] == 'X') ||
	        (board[1] == 'X' && board[4] == 'X' && board[7] == 'X') ||
	        (board[2] == 'X' && board[5] == 'X' && board[8] == 'X') ||
	        (board[3] == 'X' && board[6] == 'X' && board[9] == 'X') ||
	        (board[1] == 'X' && board[5] == 'X' && board[9] == 'X') ||
	        (board[3] == 'X' && board[5] == 'X' && board[7] == 'X'))
	    {
	        return 1;
	    }
	    else if((board[1] == 'O' && board[2] == 'O' && board[3] == 'O') ||
	            (board[4] == 'O' && board[5] == 'O' && board[6] == 'O') ||
	            (board[7] == 'O' && board[8] == 'O' && board[9] == 'O') ||
	            (board[1] == 'O' && board[4] == 'O' && board[7] == 'O') ||
	            (board[2] == 'O' && board[5] == 'O' && board[8] == 'O') ||
	            (board[3] == 'O' && board[6] == 'O' && board[9] == 'O') ||
	            (board[1] == 'O' && board[5] == 'O' && board[9] == 'O') ||
	            (board[3] == 'O' && board[5] == 'O' && board[7] == 'O'))
	    {
	        return -1;
	    }
	    else return 0;
	}
	
	/**
	 * Verifies if the game ended-up in a draw
	 * @return 1 if it was a draw, else 0
	 */
	public int check_draw(){
		
		// Checks if no one won and all spaces were filled
	    if ((check_win() == 0) && (board[1] != '_') && (board[2] != '_') &&
	         (board[3] != '_') && (board[4] != '_') && (board[5] != '_') &&
	         (board[6] != '_') && (board[7] != '_') && (board[8] != '_') &&
	         (board[9] != '_'))
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
	 * @return -10 if it is the 'X', else 10 if it is the 'O' 
	 */
	public int check_two(){
	    int sum = 0;
	    
	    //good states for 'X'
	    if ((board[1] == '_' && board[2] == 'X' && board[3] == 'X') ||
	        (board[1] == 'X' && board[2] == '_' && board[3] == 'X') ||
	        (board[1] == 'X' && board[2] == 'X' && board[3] == '_')){
	            sum += -10;
	    }

	    if ((board[4] == '_' && board[5] == 'X' && board[6] == 'X') ||
	        (board[4] == 'X' && board[5] == '_' && board[6] == 'X') ||
	        (board[4] == 'X' && board[5] == 'X' && board[6] == '_')){
	            sum += -10;
	    }

	    if ((board[7] == '_' && board[8] == 'X' && board[9] == 'X') ||
	        (board[7] == 'X' && board[8] == '_' && board[9] == 'X') ||
	        (board[7] == 'X' && board[8] == 'X' && board[9] == '_')){
	            sum += -10;
	    }

	    if ((board[1] == '_' && board[4] == 'X' && board[7] == 'X') ||
	        (board[1] == 'X' && board[4] == '_' && board[7] == 'X') ||
	        (board[1] == 'X' && board[4] == 'X' && board[7] == '_')){
	            sum += -10;
	    }

	    if ((board[2] == '_' && board[5] == 'X' && board[8] == 'X') ||
	        (board[2] == 'X' && board[5] == '_' && board[8] == 'X') ||
	        (board[2] == 'X' && board[5] == 'X' && board[8] == '_')){
	            sum += -10;
	    }

	    if ((board[3] == '_' && board[6] == 'X' && board[9] == 'X') ||
	        (board[3] == 'X' && board[6] == '_' && board[9] == 'X') ||
	        (board[3] == 'X' && board[6] == 'X' && board[9] == '_')){
	            sum += -10;
	    }

	    if ((board[1] == '_' && board[5] == 'X' && board[9] == 'X') ||
	        (board[1] == 'X' && board[5] == '_' && board[9] == 'X') ||
	        (board[1] == 'X' && board[5] == 'X' && board[9] == '_')){
	            sum += -10;
	    }

	    if ((board[3] == '_' && board[5] == 'X' && board[7] == 'X') ||
	        (board[3] == 'X' && board[5] == '_' && board[7] == 'X') ||
	        (board[3] == 'X' && board[5] == 'X' && board[7] == '_')){
	            sum += -10;
	    }

	    //good states for 'O'
	    if((board[1] == '_' && board[2] == 'O' && board[3] == 'O') ||
	       (board[1] == 'O' && board[2] == '_' && board[3] == 'O') ||
	       (board[1] == 'O' && board[2] == 'O' && board[3] == '_')){
	            sum += 10;
	    }

	    if ((board[4] == '_' && board[5] == 'O' && board[6] == 'O') ||
	        (board[4] == 'O' && board[5] == '_' && board[6] == 'O') ||
	        (board[4] == 'O' && board[5] == 'O' && board[6] == '_')){
	            sum += 10;
	    }

	    if ((board[7] == '_' && board[8] == 'O' && board[9] == 'O') ||
	        (board[7] == 'O' && board[8] == '_' && board[9] == 'O') ||
	        (board[7] == 'O' && board[8] == 'O' && board[9] == '_')){
	            sum += 10;
	    }

	    if ((board[1] == '_' && board[4] == 'O' && board[7] == 'O') ||
	        (board[1] == 'O' && board[4] == '_' && board[7] == 'O') ||
	        (board[1] == 'O' && board[4] == 'O' && board[7] == '_')){
	            sum += 10;
	    }

	    if ((board[2] == '_' && board[5] == 'O' && board[8] == 'O') ||
	        (board[2] == 'O' && board[5] == '_' && board[8] == 'O') ||
	        (board[2] == 'O' && board[5] == 'O' && board[8] == '_')){
	            sum += 10;
	    }

	    if ((board[3] == '_' && board[6] == 'O' && board[9] == 'O') ||
	        (board[3] == 'O' && board[6] == '_' && board[9] == 'O') ||
	        (board[3] == 'O' && board[6] == 'O' && board[9] == '_')){
	            sum += 10;
	    }

	    if ((board[1] == '_' && board[5] == 'O' && board[9] == 'O') ||
	        (board[1] == 'O' && board[5] == '_' && board[9] == 'O') ||
	        (board[1] == 'O' && board[5] == 'O' && board[9] == '_')){
	            sum += 10;
	    }

	   if ((board[3] == '_' && board[5] == 'O' && board[7] == 'O') ||
	       (board[3] == 'O' && board[5] == '_' && board[7] == 'O') ||
	       (board[3] == 'O' && board[5] == 'O' && board[7] == '_')){
	           sum += 10;
	    }

	   return sum;
	}
	
	/**
	 * Verifies if someone has a good start in a roll
	 * @return -1 if it is the 'X', else 1 if it is the 'O' 
	 */
	public int check_one(){
	    int sum = 0;

	    //good states for 'X'
	    if ((board[1] == '_' && board[2] == '_' && board[3] == 'X') ||
	        (board[1] == 'X' && board[2] == '_' && board[3] == '_') ||
	        (board[1] == '_' && board[2] == 'X' && board[3] == '_')){
	            sum += -1;
	    }

	    if ((board[4] == '_' && board[5] == '_' && board[6] == 'X') ||
	        (board[4] == 'X' && board[5] == '_' && board[6] == '_') ||
	        (board[4] == '_' && board[5] == 'X' && board[6] == '_')){
	            sum += -1;
	    }

	    if ((board[7] == '_' && board[8] == '_' && board[9] == 'X') ||
	        (board[7] == 'X' && board[8] == '_' && board[9] == '_') ||
	        (board[7] == '_' && board[8] == 'X' && board[9] == '_')){
	            sum += -1;
	    }

	    if ((board[1] == '_' && board[4] == '_' && board[7] == 'X') ||
	        (board[1] == 'X' && board[4] == '_' && board[7] == '_') ||
	        (board[1] == '_' && board[4] == 'X' && board[7] == '_')){
	            sum += -1;
	    }

	    if ((board[2] == '_' && board[5] == '_' && board[8] == 'X') ||
	        (board[2] == 'X' && board[5] == '_' && board[8] == '_') ||
	        (board[2] == '_' && board[5] == 'X' && board[8] == '_')){
	            sum += -1;
	    }

	    if ((board[3] == '_' && board[6] == '_' && board[9] == 'X') ||
	        (board[3] == 'X' && board[6] == '_' && board[9] == '_') ||
	        (board[3] == '_' && board[6] == 'X' && board[9] == '_')){
	            sum += -1;
	    }

	    if ((board[1] == '_' && board[5] == '_' && board[9] == 'X') ||
	        (board[1] == 'X' && board[5] == '_' && board[9] == '_') ||
	        (board[1] == '_' && board[5] == 'X' && board[9] == '_')){
	            sum += -1;
	    }

	    if ((board[3] == '_' && board[5] == '_' && board[7] == 'X') ||
	        (board[3] == 'X' && board[5] == '_' && board[7] == '_') ||
	        (board[3] == '_' && board[5] == 'X' && board[7] == '_')){
	            sum += -1;
	    }

	    //good states for 'O'
	    if((board[1] == '_' && board[2] == '_' && board[3] == 'O') ||
	       (board[1] == 'O' && board[2] == '_' && board[3] == '_') ||
	       (board[1] == '_' && board[2] == 'O' && board[3] == '_')){
	            sum += 1;
	    }

	    if ((board[4] == '_' && board[5] == '_' && board[6] == 'O') ||
	        (board[4] == 'O' && board[5] == '_' && board[6] == '_') ||
	        (board[4] == '_' && board[5] == 'O' && board[6] == '_')){
	            sum += 1;
	    }

	    if ((board[7] == '_' && board[8] == '_' && board[9] == 'O') ||
	        (board[7] == 'O' && board[8] == '_' && board[9] == '_') ||
	        (board[7] == '_' && board[8] == 'O' && board[9] == '_')){
	            sum += 1;
	    }

	    if ((board[1] == '_' && board[4] == '_' && board[7] == 'O') ||
	        (board[1] == 'O' && board[4] == '_' && board[7] == '_') ||
	        (board[1] == '_' && board[4] == 'O' && board[7] == '_')){
	            sum += 1;
	    }

	    if ((board[2] == '_' && board[5] == '_' && board[8] == 'O') ||
	        (board[2] == 'O' && board[5] == '_' && board[8] == '_') ||
	        (board[2] == '_' && board[5] == 'O' && board[8] == '_')){
	            sum += 1;
	    }

	    if ((board[3] == '_' && board[6] == '_' && board[9] == 'O') ||
	        (board[3] == 'O' && board[6] == '_' && board[9] == '_') ||
	        (board[3] == '_' && board[6] == 'O' && board[9] == '_')){
	            sum += 1;
	    }

	    if ((board[1] == '_' && board[5] == '_' && board[9] == 'O') ||
	        (board[1] == 'O' && board[5] == '_' && board[9] == '_') ||
	        (board[1] == '_' && board[5] == 'O' && board[9] == '_')){
	            sum += 1;
	    }

	   if ((board[3] == '_' && board[5] == '_' && board[7] == 'O') ||
	       (board[3] == 'O' && board[5] == '_' && board[7] == '_') ||
	       (board[3] == '_' && board[5] == 'O' && board[7] == '_')){
	           sum += 1;
	    }
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
