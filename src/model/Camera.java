package model;

public class Camera {
	
	public Vector Position = new Vector();
	
	public double viewYOffset = 0.5d;
	public double viewXOffset = 0.5d;
	
	public Camera(double yPos) {
		this.Position.y = yPos;
	}
	
	public void update(Game model) {
		if(model.Players.length > 0) {
			double y = 0;
			int n = 0;
			
			for(Player p : model.Players) {
				if(p.isAlive()) {
					y += p.Position.y;
					n++;
				}
			}
			
			if(n > 0) {
				this.Position.y = y - viewYOffset;
			}
		}
	}
	
	public Vector toViewport(Vector p) {
		return new Vector(
				p.x - Position.x + viewXOffset,
				p.y - Position.y + viewYOffset
			);
	}
	
}
