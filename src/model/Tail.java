package model;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class Tail {
	public final Color color;
	
	public LinkedList<Vector> points;
	
	private double lastYPos;
	
	public Tail(Color color) {
		this.color = color;
		
		this.points = new LinkedList<Vector>();
		
		this.lastYPos = 0;
	}
	
	public void update(Vector p, double yCutOff) {
		if(points.size() == 0) {
			this.points.push(p);
			this.lastYPos = p.y;
		} else {
			if(p.y - lastYPos > 1) {
				this.points.push(p);
				this.lastYPos = p.y;
			}
		}
		
		this.gc(yCutOff);
	}
	
	/**
	 * tail garbage-collector
	 * removes points below the cutoff line
	 * @param cutoff
	 */
	private void gc(double cutoff) {
		while(points.peekLast().y < cutoff) {
			points.removeLast();
		}
	}
}
