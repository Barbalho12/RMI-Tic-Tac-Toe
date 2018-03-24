package ai;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IPlayer;

public class AIPlayer implements IPlayer{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int board;
	private int position;
	
	private char name;   // 'X' or 'O'
	
	private boolean blocked;   // if he can play now

	public AIPlayer() throws RemoteException {
		super();
		
		this.board = 0;
		this.position = 0;
	}

	@Override
	public void init(int id, char name, boolean blocked) throws RemoteException {
		this.id = id;
		this.name = name;
		this.blocked = blocked;
	}
	
	@Override
	public void play(int board, int position) throws RemoteException {
		/*int i, input, opt;
    int k=0;

    Tabuleiro *tab = new Tabuleiro();
    tab->print();

    for(i = 1; i <= 81; i++){
        if (i % 2 == 0){
            printf("Player O jogou no tabuleiro %d na posicao %d.\n\n", k, posmin);
            input = posmin;
        }
        else {
            if(k==0 || !tab->at(k)->valid){
                do{
                    printf("Escolha o tabuleiro em que quer jogar:\n");
                    scanf("%d", &k);
                    if(k>9){

                        for(int i=1; i<=9; i++){
                            cout << tab->at(i)->valid << " Tab " << i << "." << endl;
                            k=0;
                        }
                    }
                }while(k==0 || !tab->at(k)->valid);
            }

            printf("Jogador X marque no tabuleiro %d :\n", k);
            do{
            scanf("%d", &input);
            }while(tab->at(k)->board[input]!='_');
        }

        if (i % 2 != 0)
            tab->at(k)->board[input] = 'X';
        else
            tab->at(k)->board[input] = 'O';

        //se estiver no Windows habilite esse clear
        system("CLS");

        tab->print();

        int tempk = findK(tab, k, 2);

        //Verificação da vitoria de um TABULEIRO INTERNO.
        int boardchk = tab->at(k)->check_win();
        if (boardchk == 1){
            printf("Jogador X venceu o tabuleiro %d!\n", k);
            tab->at(k)->valid = false;
            tab->at(k)->winner = 'X';
        }
        else{
            if (boardchk == -1){
                printf("Jogador O venceu o tabuleiro %d!\n", k);
                tab->at(k)->valid = false;
                tab->at(k)->winner = 'O';
                k=0;
            }
            else{
                tab->at(k)->check_draw();
            }
        }

        int chktable = tab->check_win();

        if(chktable == 1){
            printf("Jogador X venceu o jogo!\n");
            break;
        }
        else{
            if(chktable == -1){
                printf("Jogador O venceu o jogo!\n");
                break;
            }
            else if(tab->check_draw() == 1){
                printf("Deu velha!\n");
                break;
            }
            else{
                if((chktable == 0) && (i != 81)){
                    if((i%2!=0) && (input==0 || !tab->at(input)->valid)){
                        k = tempk;
                    }
                    else{
                        k = input;
                    }
                    posmin = -1;
                    if(i % 2 != 0){
                        if(i < 30){
                            opt = alphabeta(k, 14, -400, 400, 2, *tab, 14);
                        }
                        else if(i < 40){
                                opt = alphabeta(k, 24, -400, 400, 2, *tab, 24);
                            }
                        else{
                            opt = alphabeta(k, 28, -400, 400, 2, *tab, 28);
                        }
                    }
                }
            }
        }
    }*/
	}	

	@Override
	public boolean isBlocked() throws RemoteException {
		return blocked;
	}

	@Override
	public void setBlocked(boolean blocked) throws RemoteException {
		this.blocked = blocked;
	}

	@Override
	public int getId() throws RemoteException {
		return id;
	}

	@Override
	public int getBoard() throws RemoteException {
		return board;
	}

	@Override
	public int getPosition() throws RemoteException {
		return position;
	}

	@Override
	public char getName() throws RemoteException {
		return name;
	}
}
