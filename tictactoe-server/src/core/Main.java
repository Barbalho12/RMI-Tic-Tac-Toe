package core;

import java.util.Scanner;

public class Main {
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
	static int findK(UltimateBoard tab, int k, int player){
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
	            if(tab.at(i).isValid() && tab.at(i).check_heuristic() < htemp){
	                htemp = tab.at(i).check_heuristic();
	                tempk = i;
	            }
	        }
	    	// if it is the player 'X' 
	        else{
	        	// check if it's valid and the heuristic is higher than the worse case
	            if(tab.at(i).isValid() && tab.at(i).check_heuristic() > htemp){
	                htemp = tab.at(i).check_heuristic();
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
	static int alphabeta(int boardnum, int depth, int a, int b, int player, UltimateBoard tab, int profundidade){
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
	
	
	
	public static void main(String[] args) {
		/*
	        1 - X
	        2 - O
	    */
	    int i, input, opt;
	    int k=0;	    

	    Scanner scanIn = new Scanner(System.in);
	
	    UltimateBoard tab = new UltimateBoard();
	    tab.print();
	    
	    // there are 81 plays at most
	    for(i = 1; i <= 81; i++){
	    	
	    	// check if it is the AI turn
	        if (i % 2 == 0){
	            System.out.println("Player O jogou no tabuleiro " + k + " na posicao " + posmin + ". \n" );
	            input = posmin;
	        }
	        
	        //check if it is the player turn
	        else {
	        	
	        	// If this is the first turn or the current board is invalid
	            if(k==0 || !tab.at(k).isValid()){
	            	
	            	// ask the player to select the first board
	                do{
	                	System.out.printf("Escolha o tabuleiro em que quer jogar:\n");
	            	    k = scanIn.nextInt();	                    
	                }while(k==0 || !tab.at(k).isValid());
	            }
	
	            // ask the player to select were he wants to place the 'X'
	            System.out.printf("Jogador X marque no tabuleiro %d :\n", k);
	            do{
	        	    input = scanIn.nextInt();
	            }while(tab.at(k).getBoardCharAt(input) !='_');
	        }

	        // find the best board
	        int tempk = findK(tab, k, 2);
	
	        // place the marker
	        if (i % 2 != 0)
	            tab.at(k).setBoardCharAt( input, 'X');
	        else
	            tab.at(k).setBoardCharAt( input, 'O');
	
	        
	        tab.print();
	
	        // verify if someone won the current board
	        int boardchk = tab.at(k).check_win();
	        if (boardchk == 1){
	        	System.out.printf("Jogador X venceu o tabuleiro %d!\n", k);
	            tab.at(k).setValid(false);
	            tab.at(k).setWinner('X');
	        }
	        else{
	            if (boardchk == -1){
	            	System.out.printf("Jogador O venceu o tabuleiro %d!\n", k);
	                tab.at(k).setValid(false);
		            tab.at(k).setWinner('O');
	                k=0;
	            }
	            else{
	                tab.at(k).check_draw();
	            }
	        }
	    		
	        int chktable = tab.check_win();
	
	        if(chktable == 1){
	        	System.out.printf("Jogador X venceu o jogo!\n");
	            break;
	        }
	        else if(chktable == -1){
            	System.out.printf("Jogador O venceu o jogo!\n");
                break;
            }
            else if(tab.check_draw() == 1){
            	System.out.printf("Deu velha!\n");
                break;
            }
	        else if((chktable == 0) && (i != 81)){
                if((i%2!=0) && (input==0 || !tab.at(input).isValid())){
                    k = tempk;
                }
                else{
                    k = input;
                }
                posmin = -1;
                if(i % 2 != 0){
                    if(i < 30){
                        opt = alphabeta(k, 14, -400, 400, 2, tab, 14);
                    }
                    else if(i < 40){
                            opt = alphabeta(k, 24, -400, 400, 2, tab, 24);
                        }
                    else{
                        opt = alphabeta(k, 28, -400, 400, 2, tab, 28);
                    }
                }
	        }
	    }

	}

}
