package model;

public class Camera {
	
	public Vector Position = new Vector();
	
	public double viewYOffset = 0.5d;
	
	public Camera(double yPos) {
		this.Position.y = yPos;
	}
	
	public void update(Game model) {
		if(model.Players.length > 0) {
			double x = 0, y = 0;
			int n = 0;
			
			for(Player p : model.Players) {
				if(p.isAlive()) {
					x += p.Position.x;
					y += p.Position.y;
					n++;
				}
			}
			
			if(n > 0) {
				double f = 1d / (double)n;
				
				this.Position = new Vector(f * x, f * y);
			}
		}
	}
	
	public Vector toViewport(Vector p, double ratio) {
		return new Vector(
				p.x - Position.x + 0.5 * ratio,
				p.y - Position.y + viewYOffset
			);
	}
	
}
