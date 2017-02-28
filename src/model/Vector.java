package model;

public class Vector {
	
	public int x;
	public int y;
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this.x = this.y = 0;
	}
	
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y);
	}
	
	public Vector clone() {
		return new Vector(x,y);
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
