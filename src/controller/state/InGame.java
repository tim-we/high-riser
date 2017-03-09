package controller.state;

import controller.input.UserInput;
import javafx.scene.input.KeyCode;
import model.Game;
import model.Player;

public class InGame implements GameState {
	
	private Game game;
	private UserInput input;
	private GameState nextState = this;
	
	private int lastWidthChangeYPos = 0;
	
	public InGame(Game game, UserInput input) {
		this.game = game;
		this.input = input;
	}
	
	public void update(double seconds) {		
//		game.moveUp(seconds);
//		updatePlayers(seconds);
//		
//		checkCollisions();
//		
//		// get thinner ?
//		if(game.Map.getYPosition() - this.lastWidthChangeYPos > 25) {
//			game.Map.thinner();
//			this.lastWidthChangeYPos = game.Map.getYPosition();
//		}
//		
//		// pause?
//		if(input.isPressed(KeyCode.P) || input.isPressed(KeyCode.PAUSE)) {
//			// change game state to paused
//			this.nextState = new Paused(input, this);
//		}
	}
	
	private void updatePlayers(double seconds) {
		//game.updatePlayers(seconds);
		
		for(Player player : game.Players) {
			player.setUserInput(
					input.isPressed(player.keycode)
				);
		}
	}
	
	/**
	 * checks if players touch the wall
	 */
	private void checkCollisions() {
		boolean atLeastOneAlive = false;
		
		for(Player player : game.Players) {
			if(player.isAlive() && game.Map.isWall(player.Position)) {
				player.die();
			} else {
				atLeastOneAlive = true;
			}
		}
		
		if(!atLeastOneAlive) {
			// stop the game
			nextState = null;
			
			System.out.println("Game over!");
		}
	}
	
	public GameState nextState() {
		return nextState;
	}
}
