package model.map;

public class MapSegment {
	
	public final double xLowLeft;
	public final double xLowRight;
	
	public final double xHighLeft;	
	public final double xHighRight;
	
	public final double yLow;	
	public final double yHigh;
	
	public MapSegment(double x, double y, double width, double height, int dir) {
		final double widthHalf = width * 0.5;
		
		double xTop = x;
		if(dir > 0) { xTop = x + height; }
		else if(dir < 0) { xTop = x - height; }
		
		this.xLowLeft = x - widthHalf;
		this.xLowRight = x + widthHalf;
		this.xHighLeft = xTop - widthHalf;
		this.xHighRight = xTop + widthHalf;
		
		this.yLow = y;
		this.yHigh = y + height;
	}
	
	public MapSegment(MapSegment ms, double endWidth, double height, int dir) {
		this.xLowLeft = ms.xHighLeft;
		this.xLowRight = ms.xHighRight;
		
		final double xOffset = (dir > 0) ? height : -height;
		this.xHighLeft = this.xLowLeft + xOffset;
		this.xHighRight = this.xHighLeft + endWidth;
		
		this.yLow = ms.yHigh;
		this.yHigh = this.yLow + height;
	}
	
	public String toString() {
		return "[MapSegment " + Math.round(this.yLow) + "]";
	}
}
