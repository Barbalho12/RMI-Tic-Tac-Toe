package core;

import java.util.ArrayList;
import java.util.List;

public class Person{

	int credential;
	char playerName;
	boolean blocked;
	int choosenBoard;
	int choosenPosition;

	public Person(int credential, char playerName, boolean blocked){
		this.credential = credential;
		this.playerName = playerName;
		this.blocked = blocked;

		this.choosenBoard = 0;
		this.choosenPosition = 0;
	}
}
