package controller.state;

public interface GameState {
	public void update(double seconds);
	
	/**
	 * set the next game state
	 * @return null to stop game
	 */
	public GameState nextState();
}
