package core;

import java.rmi.*;

public interface IGame extends Remote{
	
	public String init(int credential) throws RemoteException;
	public String play(int credential, int choosenBoard, int choosenPosition) throws RemoteException;

	public int getCredential() throws RemoteException;
}