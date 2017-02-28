package model;

import java.util.Random;

import controller.Options;
import model.map.Map;

public class Game {
	
	public Map Map;
	
	public Player[] Players;
	
	private Game() {
		Random rand = new Random();
		
		final int seed = rand.nextInt();
		
		this.Map = new Map(seed);
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
	
	/**
	 * @param deltaTime - time since last frame in seconds
	 */
	public void moveUp(double deltaTime) {
		final int oldY = this.Map.getYPosition();
		
		this.Map.moveUp(deltaTime * Options.SPEED_Y);
		
		int up = this.Map.getYPosition() - oldY;

		while(up>0) {
			this.moveUpPlayers();
			up--;
		}
	}
	
	private void moveUpPlayers() {
		for(Player player : this.Players) {
			if(player.isAlive()) {
				player.moveUp();
			}
		}
	}
	
	public void updatePlayers(double seconds) {
		for(Player player : this.Players) {
			if(player.isAlive()) {
				player.update(seconds);
			}
		}
	}
	
}
