package game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.GameResponse;

public class GameResponseImpl extends UnicastRemoteObject implements GameResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] Settings;
	private String alert;
	private String message;


	public GameResponseImpl(String[] settings, String message, String alert) throws RemoteException {
		super();
		setSettings(settings);
		this.setAlert(alert);
		this.setMessage(message);
	}

	@Override
	public String[] getSettings() throws RemoteException{
		return Settings;
	}

	@Override
	public String getAlert() throws RemoteException{
		return alert;
	}

	@Override
	public String getMessage() throws RemoteException{
		return message;
	}

	public void setSettings(String[] settings) {
		this.Settings = settings;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
