package interfaces;

import java.rmi.*;

import enums.GameOptions;

public interface IGame extends Remote{
	
	public GameResponse init(IPlayer player, GameOptions option) throws RemoteException;
	public GameResponse play(IPlayer player, int choosenBoard, int choosenPosition) throws RemoteException;

	public int getCredential() throws RemoteException;
}