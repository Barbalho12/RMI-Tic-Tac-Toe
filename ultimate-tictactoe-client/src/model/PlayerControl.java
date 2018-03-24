package model;

import java.rmi.RemoteException;
import java.util.Scanner;

import enums.GameOptions;
import interfaces.GameResponse;
import interfaces.IGame;
import interfaces.IPlayer;

public class PlayerControl {
	
	private Scanner scanIn;

	public void execute(IGame game) throws RemoteException  {
		scanIn = new Scanner(System.in);		
		IPlayer player = new Player();
		int board, position;
		
		System.out.print("\nEscolha o modo de jogo.\n0 - Online contra outro jogador.\n1 - Contra uma I.A.\n\n> ");
		
        int option = scanIn.nextInt();
        
        while (option != 0  && option != 1) {
        	option = scanIn.nextInt();
        }
        
		String msg = game.init(player, GameOptions.values()[option]);

		System.out.print("\nTutorial> Digite 2 números separados por um espaço.\n");
		System.out.print("Tutorial> O 1º é a posição no tabuleiro externo.\n");
		System.out.println("Tutorial> O 2º é a posição no tabuleiro interno.\n");

		System.out.println(msg);


		while (true){

			System.out.print( player.getName() + "> ");
	        board = scanIn.nextInt();
	        position = scanIn.nextInt();

	        GameResponse response = game.play(player, board, position );

	        
	        if(response.getSettings() != null)
	        	printState(response.getSettings());
	        if(response.getMessage() != null)
	        	System.out.println(response.getMessage());
	        else if(response.getAlert() != null)
	        	System.err.println(response.getAlert());

			try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
		}

	}
	
	/**
	 * Get the board state into the console
	 */
    public String printState(String[] response){
        int conti=0; // the column counter
        int contj=0; // the line counter
        int i, j;
        
        String state = "";

        while(conti!=2){
            for(i=0; i<3; i++){
                for(j=0; j<3; j++){
                	
                	
                	state += response[(i*9)+j+contj] + " ";
                }
                if(j%3!=0)	state += " | ";
            }
            contj+=3;
            state += "\n";
            System.out.println();
            conti++;
        }
        state += "---------------------------------\n";

        conti=0;
        contj=0;

        while(conti!=2){
            for(i=3; i<6; i++){
                for(j=0; j<3; j++){
                	state += response[(i*9)+j+contj] + " ";
                }
                if(j%3!=0)	state += " | ";
            }
            contj+=3;
            state += "\n";
            conti++;
        }
        
        state += "---------------------------------\n";

        conti=0;
        contj=0;

        while(conti!=2){
            for(i=6; i<9; i++){
                for(j=0; j<3; j++){
                	state += response[(i*9)+(j+contj)] + " ";
                }
                if(j%3!=0)	state += " | ";
            }
            contj+=3;
            state += "\n";
            conti++;
        }
        
        state += "\n";
        
        return state;
    }
}
