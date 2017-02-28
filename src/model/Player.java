package model;

import controller.Options;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player {
	public Vector Position;
	
	private double realXPos;
	
	private double xSpeed = 0;
	
	private boolean userInput = false;
	
	public KeyCode keycode = KeyCode.SPACE;
	
	public Tail Tail;
	
	private boolean alive = true;
	
	public Player() {
		this(Color.RED, 0);
	}
	
	public Player(Color tailColor, int offsetX) {
		this.Position = new Vector(offsetX, Options.PLAYER_Y_POS);
		this.realXPos = offsetX;
		
		this.Tail = new Tail(tailColor, this.Position.x, this.Position.y-1, Options.PLAYER_Y_POS);
	}
	
	public void update(double seconds) {
		this.xSpeed += (userInput ? -Options.X_ACC : Options.X_ACC) * seconds;
		
		if(Math.abs(xSpeed) > Options.MAX_SPEED_X) {
			xSpeed = (xSpeed > 0) ? Options.MAX_SPEED_X : -Options.MAX_SPEED_X;
		}
		
		this.realXPos += xSpeed * seconds;
		
		this.Position.x = (int) Math.round(this.realXPos);
	}
	
	public void moveUp() {
		this.Tail.addPoint(this.Position.clone());
		
		this.Position.y++;
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
