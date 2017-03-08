package model.map;

import model.Vector;

public class MapSegment {
	
	public final Vector topLeft;
	public final Vector topRight;
	public final Vector bottomLeft;
	public final Vector bottomRight;
	
	public final double yBottom;	
	public final double yTop;
	
	public MapSegment(double x, double y, double width, double height, double shear) {
		final double widthHalf = width * 0.5;
		
		double xTop = x + shear * height;
		
		this.yTop = y + height;
		this.yBottom = y;
		
		bottomLeft = new Vector(x - widthHalf, y);
		bottomRight = new Vector(x + widthHalf, y);
		topLeft = new Vector(xTop - widthHalf, yTop);
		topRight = new Vector(xTop + widthHalf, yTop);
	}
	
	public MapSegment(MapSegment ms, double endWidth, double height, double shear) {
		this.bottomLeft = ms.topLeft; // cloning not necessary
		this.bottomRight = ms.topRight;
		
		this.yBottom = ms.yTop;
		this.yTop = this.yBottom + height;
		
		Vector offset = new Vector(shear * height, height);
		
		this.topLeft	= Vector.add(bottomLeft,  offset);
		this.topRight	= Vector.add(bottomRight, offset);
		
		this.topRight.x = this.topLeft.x + endWidth;
	}
	
	public MapSegment(double width, double height) {
		this(0, 0, width, height, 0);
	}
	
	public String toString() {
		return "[MapSegment " + Math.round(this.yBottom) + "]";
	}
}
