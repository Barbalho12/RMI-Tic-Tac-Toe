package com.ufrn.game;

import com.ufrn.interfaces.IPlayer;

public class Player implements IPlayer{
	
//	private static final long serialVersionUID = 1L;
	
	private int id;
	private int board;
	private int position;
	
	private char name;   // 'X' or 'O'
	
	private boolean blocked;   // if he can play now

	public Player() {
		super();
		
		this.board = 0;
		this.position = 0;
	}


	public void init(int id, char name, boolean blocked) {
		this.id = id;
		this.name = name;
		this.blocked = blocked;
	}
	

	public void play(int board, int position)  {
		this.board = board;
		this.position = position;
	}	


	public boolean isBlocked()  {
		return blocked;
	}

	
	public void setBlocked(boolean blocked)  {
		this.blocked = blocked;
	}


	public int getId()  {
		return id;
	}


	public int getBoard()  {
		return board;
	}


	public int getPosition()  {
		return position;
	}


	public char getName()  {
		return name;
	}
}
