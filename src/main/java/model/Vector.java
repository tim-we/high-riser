package model;

public class Vector {
	
	public double x;
	public double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this.x = this.y = 0;
	}
	
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y);
	}
	
	public static Vector add(Vector a, double s, Vector b) {
		return new Vector(
					a.x + s * b.x,
					a.y + s * b.y
				);
	}
	
	public Vector clone() {
		return new Vector(x,y);
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
