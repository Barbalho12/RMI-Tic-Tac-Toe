package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends Remote {
		
	public void init(int id, char name, boolean blocked) throws RemoteException;
	public void play(int board, int position) throws RemoteException;
	
	public boolean isBlocked() throws RemoteException;
	public void setBlocked(boolean blocked) throws RemoteException;
	
	public int getId() throws RemoteException;
	public int getBoard() throws RemoteException;
	public char getName() throws RemoteException;
	public int getPosition() throws RemoteException;
}
