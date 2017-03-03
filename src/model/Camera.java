package model;

public class Camera {
	
	public Vector Position;
	
	private Vector ViewportSize;
	private double widthHalf;
	private double heightHalf;
	
	public void setViewportSize(double width, double height) {
		ViewportSize = new Vector(width, height);
		
		widthHalf = width / 2d;
		heightHalf = height / 2d;
	}
	
	public Camera() {
		setViewportSize(1d,1d);
	}
	
	public void update(Game model) {
		
	}
	
	public double lowerBound() {
		return Position.y - heightHalf;
	}
	
	public double upperBound() {
		return Position.y + heightHalf;
	}
	
	public double leftBound() {
		return Position.x - widthHalf;
	}
	
	public double rightBound() {
		return Position.x + widthHalf;
	}
	
	public Vector getSize() {
		return ViewportSize;
	}
	
}
