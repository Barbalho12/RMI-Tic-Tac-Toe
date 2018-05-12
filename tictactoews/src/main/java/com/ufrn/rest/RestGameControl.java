package com.ufrn.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.ufrn.ai.AIPlayer;

import com.ufrn.enums.GameOptions;
import com.ufrn.enums.GameStatus;
import com.ufrn.game.GameCheck;
import com.ufrn.game.GameControl;
import com.ufrn.game.Player;
import com.ufrn.game.UltimateBoard;
import com.ufrn.interfaces.IGame;
import com.ufrn.interfaces.IBoard;
import com.ufrn.interfaces.IPlayer;

@Path("/")
public class RestGameControl {

	GameControl gameControl = GameControl.getInstance();

	static int count_init = 0;

	// http://localhost:8080/tictactoews/rest/init?game_option=NORMAL
	@GET
	@Path("/init")
	public String init(@QueryParam("game_option") GameOptions option) {
		++count_init;
		if (count_init <= 2) {
			IPlayer player = new Player();
			gameControl.init(player, option);
			return String.valueOf(player.getId());
		} else {
			return "ERROR";
		}
	}

	// http://localhost:8080/tictactoews/rest/play?id_player=1&board=1&position=2
	@GET
	@Path("/play")
	public String play(@QueryParam("id_player") int id_player, @QueryParam("board") int board,
			@QueryParam("position") int position) {

		if (count_init < 2)
			return "Esperando adversário!";

		IPlayer player = gameControl.getPlayer(id_player);
		String response = gameControl.play(player, board, position);
		return response;

	}

	// http://localhost:8080/tictactoews/rest/checkTurn?id_player=1
	@GET
	@Path("/checkTurn")
	public String checkTurn(@QueryParam("id_player") int id_player) {

		if (count_init < 2) {
			return "Esperando adversário!";
		}

		IPlayer player = gameControl.getPlayer(id_player);

		if (gameControl.lastPlayer == null && id_player == 1) {
			return "true";
		} else if (gameControl.lastPlayer != null && !gameControl.lastPlayer.equals(player)) {
			return "true";
		}
		
		return checkQuited();

	}

	// http://localhost:8080/tictactoews/rest/board
	@GET
	@Path("/board")
	public String board() {
		return gameControl.getTab().getState();
	}

	@GET
	@Path("/quit")
	public String quit(@QueryParam("id_player") int id_player) {
		if (count_init > 0) {
			IPlayer player = gameControl.getPlayer(id_player);
			gameControl.exit(player);
			return "";
		}else {
			return "";
		}
	}
	
	@GET
	@Path("/checkQuited")
	public String checkQuited() {
		if (gameControl.quited) {
			return "true";
		}else {
			return "false";
		}
	}

}
