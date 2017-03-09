package model;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Tail {
	public final Color Color;
	
	public LinkedList<Vector> points;
	
	private static final int Length = 42;
	
	private double lastYPos;
	
	public Tail(Color color) {
		this.Color = color;
		
		this.points = new LinkedList<Vector>();
		
		this.lastYPos = 0;
	}
	
	public void add(Vector p) {
		if(points.size() == 0) {
			this.points.push(p);
			this.lastYPos = p.y;
		} else {
			if(p.y - lastYPos > 1) {
				this.points.push(p);
				this.lastYPos = p.y;
			}
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
