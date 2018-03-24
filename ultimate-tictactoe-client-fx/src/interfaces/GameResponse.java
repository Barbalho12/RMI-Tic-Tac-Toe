package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameResponse extends Remote, Serializable{
	
	public String[] getSettings() throws RemoteException;
	public String getAlert() throws RemoteException;
	public String getMessage() throws RemoteException;

}
