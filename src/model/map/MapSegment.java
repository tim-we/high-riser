package model.map;

public class MapSegment {
	
	public final double height;
	
	public final double xLow;
	
	public final double xHigh;
	
	public final double y; // low
	
	public MapSegment(double x, double y, double height, int dir) {
		this.height = height;
		this.xLow = x;
		this.xHigh = (dir > 0) ? this.xLow + height : this.xLow - height;
		this.y = y;
	}
	
	public MapSegment(MapSegment ms, double height) {
		this.height = height;
		
		this.xLow = ms.xHigh;
		
		double dir = ms.xHigh - ms.xLow;
		this.xHigh = (dir > 0) ? this.xLow + height : this.xLow - height;
		
		this.y = ms.y + ms.height;
	}
	
	public MapSegment(double x, double y, double height) {
		this.height = height;
		
		this.xLow = this.xHigh = x;
		
		this.y = y;
	}
	
	public String toString() {
		return "[MapSegment " + Math.round(this.y) + "]";
	}
}
