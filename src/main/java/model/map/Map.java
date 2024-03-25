package model.map;

import java.util.ArrayList;
import java.util.Random;

import controller.Config;
import model.Camera;
import model.Vector;

public class Map {
	
	// for some screen sizes this might have to be a bit higher
	public static final double bufferMinLength = 1d;
	
	public ArrayList<MapSegment> data;
	private MapSegment lastMapSegment;
	private int dir = 0;
	
	private Random rand;
	
	public Map(long seed) {
		this.rand = new Random(seed);
		System.out.println("Map seed: " + seed);
		
		this.data = new ArrayList<MapSegment>();
		
		// generate the first (constant) part of the map
		
		double startHeight = 0.1;
		
		this.data.add(
			this.lastMapSegment = new MapSegment(startHeight, getWidth(0), 0.8-startHeight)
		);
		
		this.dir = (rand.nextInt(2) == 0) ? -1 : 1;
		
		addSegment(0.2);
		addSegment(0.2);
		
	}
	
	public void update(Camera cam) {
		assert(cam != null);
		
		final double yTop = cam.Position.y + bufferMinLength;
		
		// add new map segments
		while(lastMapSegment.yTop < yTop) {
			addSegment();
		}
		
		final double yBottom = cam.Position.y - 1d;
		
		// remove old segments
		while(data.get(0).yTop < yBottom) {
			data.remove(0);
		}
	}
	
	public void addSegment(double height) {
		assert(lastMapSegment != null);
		
		MapSegment ms = new MapSegment(
							this.lastMapSegment,
							getWidth(this.lastMapSegment.yTop + height),
							height,
							this.dir * Config.SHEARING
						);
		
		this.data.add(ms);
		
		this.lastMapSegment = ms;
		
		if(dir != 0) {			
			dir = (dir < 0) ? 1 : -1;
		}
	}
	
	public void addSegment() {
		double height = 0.02 + rand.nextDouble() * 0.6;
		
		addSegment(height);
	}
	
	public boolean isWall(Vector p) {
		assert(p != null);
		
		return isWall(p.x, p.y);
	}
	
	// for collision check
	public boolean isWall(double x, double y) {
		for(MapSegment ms : data) {
			
			if (ms.yBottom <= y && y < ms.yTop) {
				
				double p = (y - ms.yBottom) / (ms.yTop - ms.yBottom);
				
				assert(0d <= p && p <= 1d);
				
				double xLeft	= ms.bottomLeft.x + p * (ms.topLeft.x - ms.bottomLeft.x);
				double xRight	= ms.bottomRight.x + p * (ms.topRight.x - ms.bottomRight.x);
				
				return x < xLeft || xRight < x;
			}
		}
		
		return true;
	}
	
	// width of playable space for a given height
	private double getWidth(double y) {
		if(y < 2) { return 0.8; }
		
		double f = (y-2) * 0.07;
		double w = Math.max(0.25,  0.8 - f * 0.3);

		return w;
	
	}
	
	// for debugging
	public String toString() {
		return "[Game]";
	}
}


