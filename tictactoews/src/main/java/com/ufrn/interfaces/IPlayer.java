package com.ufrn.interfaces;



public interface IPlayer {
		
	public void init(int id, char name, boolean blocked) ;
	public void play(int board, int position);
	
	public boolean isBlocked();
	public void setBlocked(boolean blocked);
	
	public int getId();
	public int getBoard();
	public char getName();
	public int getPosition();
}
