package model.map;

import java.util.ArrayList;
import java.util.Random;

import controller.Config;
import model.Camera;
import model.Vector;

public class Map {
	
	public static final double bufferMinLength = 1d;
	
	public ArrayList<MapSegment> data;
	private MapSegment lastMapSegment;
	private int dir = 0;
	
	private Random rand;
	
	public Map(long seed) {
		this.rand = new Random(seed);
		System.out.println("Map seed: " + seed);
		
		this.data = new ArrayList<MapSegment>();
		
		this.data.add(
						this.lastMapSegment = new MapSegment(getWidth(0), 0.8)
					 );
		
		this.dir = (rand.nextInt(2) == 0) ? -1 : 1;
		
		addSegment(0.2);
		
		
	}
	
	public void build(Camera cam) {
		final double yTop = cam.Position.y + bufferMinLength;
		
		// add new map segments
		while(lastMapSegment.yHigh < yTop) {
			addSegment();
		}
		
		final double yBottom = cam.Position.y - 1d;
		
		// remove old segments
		while(data.get(0).yHigh < yBottom) {
			data.remove(0);
		}
	}
	
	public void addSegment(double height) {
		MapSegment ms = new MapSegment(
							this.lastMapSegment,
							getWidth(this.lastMapSegment.yHigh + height),
							height,
							this.dir
						);
		
		this.data.add(ms);
		
		this.lastMapSegment = ms;
		
		if(dir != 0) {			
			dir = (dir < 0) ? 1 : -1;
		}
	}
	
	public void addSegment() {
		double height = rand.nextDouble() * 0.42;
		
		addSegment(height);
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
		return 0.5;
	
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


