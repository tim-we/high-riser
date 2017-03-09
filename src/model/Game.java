package model;

import java.util.Random;

import controller.Config;
import model.map.Map;

public class Game {
	
	public Map Map;
	
	public Player[] Players;
	
	public Camera Camera;
	
	public State State;
	
	public double Rotation = 0; // in degrees :/
	
	private Game() {
		Random rand = new Random();
		
		final int seed = rand.nextInt();
		
		this.Map = new Map(seed);
		
		this.Camera = new Camera(0.5);
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
	
	public void updatePlayers(double seconds) {
		for(Player player : this.Players) {
			player.update(seconds, this);
		}
	}
	
	/**
	 * time that has passed since last frame in seconds
	 * @param delta
	 */
	public void update(double delta) {
		this.Rotation += delta * 20;
	}
	
}
