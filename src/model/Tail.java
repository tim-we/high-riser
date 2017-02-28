package model;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Tail {
	public final Color color;
	
	private int height;
	
	public LinkedList<Vector> points;
	
	public Tail(Color color, int x, int y, int height) {
		this.color = color;
		
		this.points = new LinkedList<Vector>();
		this.height = height;
		
		for(int i=0; i<height; i++) {
			this.points.add(new Vector(x,y-i));
		}
	}
	
	public void addPoint(Vector p) {
		this.points.push(p);
		
		this.gc(p.y - height);
	}
	
	/**
	 * tail garbage-collector
	 * removes points below the cutoff line
	 * @param cutoff
	 */
	private void gc(int cutoff) {
		while(points.peekLast().y < cutoff) {
			points.removeLast();
		}
	}
}
