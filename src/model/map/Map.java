package model.map;

import java.util.ArrayList;
import java.util.Random;

import controller.Options;
import model.Vector;

public class Map {
	
	private boolean lastDir;
	
	public static final double HEIGHT = 392;
	public static final int START_SEGMENT_LENGTH = 12;
	
	public ArrayList<MapSegment> data;
	private MapSegment lastMapSegment;
	
	private Random rand;
	
	public Map(long seed) {
		this.rand = new Random(seed);
		System.out.println("Map seed: " + seed);
		
		this.data = new ArrayList<MapSegment>();
		
		/*MapSegment ms = null;
		int y = 0;
		// create map start (centered map segments)
		for(; y<START_SEGMENT_LENGTH; y++) {			
			ms = new MapSegment(0, y, getWidth(y));
			
			data.add(ms);
		}
		
		boolean goLeft = lastDir = rand.nextBoolean();
		
		for(; y<HEIGHT; y++) {
			ms = new MapSegment(ms, goLeft);
			
			data.add(ms);
		}
		
		this.lastMapSegment = ms;
		*/
	}
	
	public boolean isWall(Vector p) {
		return isWall(p.x, p.y);
	}
	
	public boolean isWall(double x, double y) {
		for(int i=0; i<data.size(); i++) {
			MapSegment ms = data.get(i);
			
			if (ms.yLow <= y && y < ms.yHigh) {
				final double p = (y - ms.yLow) / (ms.yHigh - ms.yLow);
				double xLeft	= ms.xLowLeft + p * (ms.xHighLeft - ms.xLowLeft);
				double xRight	= ms.xLowRight + p * (ms.xHighRight - ms.xLowRight);
				
				return x <= xLeft && xRight <= x;
			}
		}
		
		return true;
	}
	
	// width of playable space
	private double getWidth(double y) {
		return 18;
	
	}
	
	public double bottom() {
		// TODO: implement!
		return 0;
	}
	
	// for debugging
	public String toString() {
		return "[Game]";
	}
}


