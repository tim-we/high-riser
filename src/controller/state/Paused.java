package controller.state;

import controller.input.UserInput;

public class Paused implements GameState {
	
	private UserInput input;
	private GameState nextState;
	private boolean noKeyPressed = true;
	
	public Paused(UserInput input, GameState nextState) {
		this.input = input;
		this.nextState = new Countdown(nextState);
		
		// make sure pause is not skipped immediatley
		input.clear();
		
		System.out.println("Press any key to continue the game...");
	}
	
	/**
	 * checks for key press otherwise do nothing
	 */
	public void update(double seconds) {
		if(input.anyKeyPressed()) {
			this.noKeyPressed = false;
		}
	}

	public GameState nextState() {
		if(noKeyPressed) {
			return this;
		} else {
			return nextState;
		}
	}
	
}
