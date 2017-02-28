package model.map;

public class MapSegment {
	
	public final int y;
	
	public final int left;
	
	public final int right;
	
	public MapSegment(int x, int y, int width) {
		this.y = y;
		
		this.left = x - width/2;
		
		this.right = this.left + width;
	}
	
	public MapSegment(MapSegment previous, boolean left) {
		this.y = previous.y + 1;
		
		if(left) {
			this.left = previous.left - 1;
			this.right = previous.right - 1;
		} else {
			this.left = previous.left + 1;
			this.right = previous.right + 1;
		}
	}
	
	public MapSegment(MapSegment previous, boolean left, int width) {
		this.y = previous.y + 1;
		
		this.left = previous.left + (left ?  - 1 : 1);
		
		this.right = this.left + width;
	}
	
	// for debugging purposes
	public String toString() {
		return "[MapSegment] {y="+y+", l=" + left + ", r=" + right + "}";
	}
}
