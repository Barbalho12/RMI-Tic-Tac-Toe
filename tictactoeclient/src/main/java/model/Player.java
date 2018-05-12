package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IPlayer;

public class Player extends UnicastRemoteObject implements IPlayer{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int board;
	private int position;
	
	private char name;   // 'X' or 'O'
	
	private boolean blocked;   // if he can play now

	protected Player() throws RemoteException {
		super();
		
		this.board = 0;
		this.position = 0;
	}

	public void init(int id, char name, boolean blocked) throws RemoteException {
		this.id = id;
		this.name = name;
		this.blocked = blocked;
	}
	

	public void play(int board, int position) throws RemoteException {
		this.board = board;
		this.position = position;
	}	


	public boolean isBlocked() throws RemoteException {
		return blocked;
	}


	public void setBlocked(boolean blocked) throws RemoteException {
		this.blocked = blocked;
	}


	public int getId() throws RemoteException {
		return id;
	}

	public int getBoard() throws RemoteException {
		return board;
	}


	public int getPosition() throws RemoteException {
		return position;
	}


	public char getName() throws RemoteException {
		return name;
	}
}
