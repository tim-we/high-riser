package model;

import controller.Config;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import model.map.Map;

public class Player {
	public Vector Position;
	
	public Vector Velocity;
	
	private boolean userInput = false;
	
	public KeyCode keycode = KeyCode.SPACE;
	
	public Tail Tail;
	
	private boolean alive = true;
	
	public Player() {
		this(Color.RED, 0);
	}
	
	public Player(Color tailColor, double offsetX) {
		this.Position = new Vector(offsetX, Config.PLAYER_Y_POS);
		this.Velocity = new Vector(0, Config.SPEED_Y);
		
		this.Tail = new Tail(tailColor);
	}
	
	public void update(double seconds) {
		this.Velocity.x += (userInput ? -Config.X_ACC : Config.X_ACC) * seconds;
		
		if(Math.abs(Velocity.x) > Config.MAX_SPEED_X) {
			Velocity.x = (Velocity.x > 0) ? Config.MAX_SPEED_X : -Config.MAX_SPEED_X;
		}
		
		this.move(seconds);
	}
	
	public void move(double seconds) {
		this.Tail.add(this.Position);
		
		this.Position = Vector.add(this.Position, seconds, this.Velocity);
	}
	
	public void setUserInput(boolean pressed) {
		this.userInput = pressed;
	}
	
	public Color getColor() {
		return Color.WHITE;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void die() {
		this.alive = false;
	}
}
