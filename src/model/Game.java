package model;

import java.util.Random;

import controller.Options;
import model.map.Map;

public class Game {
	
	public Map Map;
	
	public Player[] Players;
	
	public State State;
	
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
	
	public void updatePlayers(double seconds) {
		for(Player player : this.Players) {
			player.update(seconds, this);
		}
	}
	
}
