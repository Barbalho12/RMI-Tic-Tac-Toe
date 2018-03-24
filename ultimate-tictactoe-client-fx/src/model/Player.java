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

	public Player() throws RemoteException {
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
		this.board = board;
		this.position = position;
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
