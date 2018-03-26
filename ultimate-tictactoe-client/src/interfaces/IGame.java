package interfaces;

import java.rmi.*;

import enums.GameOptions;

public interface IGame extends Remote{
	
	public String init(IPlayer player,  GameOptions option) throws RemoteException;
	public String play(IPlayer player, int choosenBoard, int choosenPosition) throws RemoteException;
	public void quit(IPlayer player) throws RemoteException;
	public int getCredential() throws RemoteException;
}