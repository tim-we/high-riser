package model;

import controller.Options;
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
		this.Position = new Vector(offsetX, Options.PLAYER_Y_POS);
		this.Velocity = new Vector(0, Options.SPEED_Y);
		
		this.Tail = new Tail(tailColor);
	}
	
	public void update(double seconds, Game model) {
		this.Velocity.x += (userInput ? -Options.X_ACC : Options.X_ACC) * seconds;
		
		if(Math.abs(Velocity.x) > Options.MAX_SPEED_X) {
			Velocity.x = (Velocity.x > 0) ? Options.MAX_SPEED_X : -Options.MAX_SPEED_X;
		}
		
		this.move(seconds, model.Map);
	}
	
	public void move(double seconds, Map model) {
		this.Tail.update(this.Position, model.bottom());
		
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