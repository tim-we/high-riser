package model;

import java.util.Random;

import controller.Config;
import model.map.Map;

public class Game {
	
	public Map Map;
	
	public Player[] Players;
	
	public Camera Camera;
	
	public GameState State = GameState.AwaitingInput;
	
	public double Rotation = 0d; // in degrees :/
	
	public double TimeFactor = 1d;
	
	public double RealTimeTotal = 0d;
	
	public boolean GameOver = false;
	
	private Game() {
		Random rand = new Random();
		
		final int seed = rand.nextInt();
		
		this.Map = new Map(seed);
		
		this.Camera = new Camera(Config.PLAYER_Y_POS);
	}
	
	public Game(Player p1) {
		this();
		
		this.Players = new Player[1];
		this.Players[0] = p1;
	}
	
	public Game(Player p1, Player p2) {
		this();
		
		this.Players = new Player[2];
		this.Players[0] = p1;
		this.Players[1] = p2;
	}
	
	private void updatePlayers(double seconds) {
		for(Player player : this.Players) {
			if(player.isAlive()) {
				
				// move, accelerate, tail, etc ...
				player.update(seconds);
				
				// check collisions...
				if(Map.isWall(player.Position)) {
					System.out.println("wall collision!");
					player.die();
				}
				
			}
		}
	}
	
	/**
	 * time that has passed since last frame in seconds
	 * @param delta
	 */
	public void update(double delta) {
		RealTimeTotal += delta;
		updateTimeFactor();		
		delta *= TimeFactor;
		
		this.Rotation += delta * 9;
		
		updatePlayers(delta);
		
		Camera.update(this);
		Map.update(Camera);
	}
	
	private void updateTimeFactor() {
		assert(RealTimeTotal >= 0d);
		
		if(RealTimeTotal < 2d) {
			// slow start
			TimeFactor = 0.2d + 0.4d * RealTimeTotal;
			return;
		}
		
		TimeFactor = 1d;
	}
	
	public int numPlayersAlive() {
		int n = 0;
		
		for(Player p : Players) {
			if(p.isAlive()) { n++; }
		}
		
		return n;
	}
	
}
