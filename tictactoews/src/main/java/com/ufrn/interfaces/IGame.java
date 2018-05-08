package com.ufrn.interfaces;
import com.ufrn.enums.GameOptions;

public interface IGame{
	
	public String init(IPlayer player, GameOptions option);
	public String play(IPlayer player, int choosenBoard, int choosenPosition);
	public void exit(IPlayer player);
	public int getCredential();
}