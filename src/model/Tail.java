package model;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Tail {
	public final Color Color;
	
	public LinkedList<Vector> points;
	
	private static final int Length = 42;
	
	private static final double pointDelay = 0.04d; // seconds
	
	private double time = pointDelay;
	
	public Tail(Color color) {
		this.Color = color;
		
		this.points = new LinkedList<Vector>();
	}
	
	public void add(Vector p, double seconds) {
		time += seconds;
		
		if(time >= pointDelay) {
			this.points.push(p);
			time = 0d;
		}
		
		this.gc();
	}
	
	/**
	 * tail garbage-collector
	 * removes points below the cutoff line
	 * @param cutoff
	 */
	private void gc() {
		while(points.size() > Length) {
			points.removeLast();
		}
	}
}
