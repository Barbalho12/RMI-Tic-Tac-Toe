package core;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the management of the ultimate tic-tac-toe board.
 */

public class UltimateBoard {
	private List<Board> boards;
    private char winner;
    
    public UltimateBoard(){
    	boards = new ArrayList<Board>();
    	    	
        for(int i=0; i<9; i++){
            this.boards.add(new Board());
        }
        boards.add(0, null);  //Tabuleiros vão de 1 à 9.

        this.winner = '_';
    }

    /**
     * Returns the 'i'th board
     * @param i
     * @return
     */
    public Board at(int i){
        if(i!=0){
            return boards.get(i);
        }
        else{
            return null;
        }
    }

    /**
	 * Verifies if someone won the game
	 * @return 1 if the 'X' player won, else -1 if the player 'O' won
	 */
    public int check_win(){
        if ( (boards.get(1).getWinner() == 'X' && boards.get(2).getWinner() == 'X' && boards.get(3).getWinner() == 'X')||
             (boards.get(4).getWinner() == 'X' && boards.get(5).getWinner() == 'X' && boards.get(6).getWinner() == 'X')||
             (boards.get(7).getWinner() == 'X' && boards.get(8).getWinner() == 'X' && boards.get(9).getWinner() == 'X')||
             (boards.get(1).getWinner() == 'X' && boards.get(4).getWinner() == 'X' && boards.get(7).getWinner() == 'X')||
             (boards.get(2).getWinner() == 'X' && boards.get(5).getWinner() == 'X' && boards.get(8).getWinner() == 'X')||
             (boards.get(3).getWinner() == 'X' && boards.get(6).getWinner() == 'X' && boards.get(9).getWinner() == 'X')||
             (boards.get(1).getWinner() == 'X' && boards.get(5).getWinner() == 'X' && boards.get(9).getWinner() == 'X')||
             (boards.get(3).getWinner() == 'X' && boards.get(5).getWinner() == 'X' && boards.get(7).getWinner() == 'X')
           ){
            this.winner = 'X';
            return 1;
        }
        else if((boards.get(1).getWinner() == 'O' && boards.get(2).getWinner() == 'O' && boards.get(3).getWinner() == 'O')||
                (boards.get(4).getWinner() == 'O' && boards.get(5).getWinner() == 'O' && boards.get(6).getWinner() == 'O')||
                (boards.get(7).getWinner() == 'O' && boards.get(8).getWinner() == 'O' && boards.get(9).getWinner() == 'O')||
                (boards.get(1).getWinner() == 'O' && boards.get(4).getWinner() == 'O' && boards.get(7).getWinner() == 'O')||
                (boards.get(2).getWinner() == 'O' && boards.get(5).getWinner() == 'O' && boards.get(8).getWinner() == 'O')||
                (boards.get(3).getWinner() == 'O' && boards.get(6).getWinner() == 'O' && boards.get(9).getWinner() == 'O')||
                (boards.get(1).getWinner() == 'O' && boards.get(5).getWinner() == 'O' && boards.get(9).getWinner() == 'O')||
                (boards.get(3).getWinner() == 'O' && boards.get(5).getWinner() == 'O' && boards.get(7).getWinner() == 'O')
                ){
                this.winner = 'O';
                return -1;
        }
        else
            return 0;
    }

	/**
	 * Verifies if the game ended-up in a draw
	 * @return 1 if it was a draw, else 0
	 */
    public int check_draw(){
        if ((check_win() == 0)                 && (boards.get(1).getWinner()!= '_') && (boards.get(2).getWinner() != '_') &&
            (boards.get(3).getWinner() != '_') && (boards.get(4).getWinner()!= '_') && (boards.get(5).getWinner() != '_') &&
            (boards.get(6).getWinner() != '_') && (boards.get(7).getWinner()!= '_') && (boards.get(8).getWinner() != '_') &&
            (boards.get(9).getWinner() != '_'))
        {
            return 1;
        }
        else
            return 0;
    }


	/**
	 * Print the board state into the console
	 */
    public void print(){
        int conti=0; // the column counter
        int contj=0; // the line counter
        int i, j;

        while(conti!=3){
            for(i=1; i<4; i++){
                for(j=1; j<4; j++){
                    System.out.print(boards.get(i).getBoardCharAt(j+contj) + " ");
                }
                if(j%3!=0)
                	System.out.print(" | ");
            }
            contj+=3;
            System.out.println();
            conti++;
        }
        System.out.println( "---------------------------------");

        conti=0;
        contj=0;

        while(conti!=3){
            for(i=4; i<7; i++){
                for(j=1; j<4; j++){
                	System.out.print( boards.get(i).getBoardCharAt(j+contj) + " ");
                }
                if(j%3!=0)
                	System.out.print(" | ");
            }
            contj+=3;
            System.out.println();
            conti++;
        }
        System.out.println( "---------------------------------");

        conti=0;
        contj=0;

        while(conti!=3){
            for(i=7; i<10; i++){
                for(j=1; j<4; j++){
                	System.out.print( boards.get(i).getBoardCharAt(j+contj) + " ");
                }
                if(j%3!=0)
                	System.out.print(" | ");
            }
            contj+=3;
            System.out.println();
            conti++;
        }
        System.out.println();
    }

    
	public char getWinner() {
		return winner;
	}

	public void setWinner(char winner) {
		this.winner = winner;
	}
}
