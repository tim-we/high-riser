package controller.state;

public class Countdown implements GameState {
	
	private GameState next;
	private double counter;
	private int display;
	
	public Countdown(double timeout, GameState next) {
		assert(!(next instanceof Countdown));
		
		this.next = next;
		this.counter = timeout;
		this.display = (int) timeout + 1;
	}
	
	/**
	 * Default constructor -> 3 second countdown
	 * @param next
	 */
	public Countdown(GameState next) {
		this(3.0, next);
	}

	@Override
	public void update(double seconds) {
		if(Math.ceil(this.counter) < display) {
			display--;
			System.out.println(display + "...");
		}
		
		this.counter -= seconds;
		
		assert(display >= 0);
	}

	@Override
	public GameState nextState() {
		return this.counter > 0.0 ? this : next;
	}
	
}
