package model.map;

import java.util.ArrayList;
import java.util.Random;

import controller.Options;
import model.Vector;

public class Map {
	
	// playable space
	private int width = 18;
	
	private int yPosition = 0;
	private double yBuffer = 0; // in [0,1)
	
	// available height
	private int HEIGHT = Options.HEIGHT;
	
	private boolean lastDir;
	
	public static final int START_SEGMENT_LENGTH = 12;
	
	public ArrayList<MapSegment> data;
	private MapSegment lastMapSegment;
	
	private Random rand;
	
	public Map(long seed) {
		this.rand = new Random(seed);
		System.out.println("Map seed: " + seed);
		
		this.data = new ArrayList<MapSegment>();
		
		MapSegment ms = null;
		int y = 0;
		// create map start (centered map segments)
		for(; y<START_SEGMENT_LENGTH; y++) {			
			ms = new MapSegment(0, y, width);
			
			data.add(ms);
		}
		
		boolean goLeft = lastDir = rand.nextBoolean();
		
		for(; y<HEIGHT; y++) {
			ms = new MapSegment(ms, goLeft);
			
			data.add(ms);
		}
		
		this.lastMapSegment = ms;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
	public double getYBuffer() {
		return yBuffer;
	}
	
	public void moveUp(double amount) {
		
		this.yBuffer += amount;
		
		while(this.yBuffer > 1.0) {
			this.yPosition++;
			this.yBuffer -= 1.0;
			
			if(rand.nextBoolean()) {
				this.lastDir = rand.nextBoolean();
			}
			
			MapSegment next = new MapSegment(this.lastMapSegment, this.lastDir, this.width);
			
			data.add(next);
			data.remove(0);
			
			this.lastMapSegment = next;
		}
	}
	
	public boolean isWall(Vector p) {
		return isWall(p.x, p.y);
	}
	
	public boolean isWall(int x, int y) {
		y = y - this.yPosition;
		
		if(0 <= y && y < HEIGHT) {
			final MapSegment ms = data.get(y);
			
			return isWall(x, ms);
		}
		
		return true;
	}
	
	public boolean isWall(int x, MapSegment ms) {
		assert(ms != null);
		return (x < ms.left) || (ms.right <= x);
	}
	
	public void thinner() {
		this.width = Math.max(Options.MIN_X_SPACE, this.width-1);
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


