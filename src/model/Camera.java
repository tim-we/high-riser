package model;

public class Camera {
	
	public Vector Position = new Vector();
	
	public double viewYOffset = 0.4d;
	
	private static final double maxXMovement = 0.01;
	
	public Camera(double yPos) {
		assert(maxXMovement > 0);
		
		this.Position.y = yPos;
	}
	
	public void update(Game model) {
		if(model.Players.length > 0) {
			double x = 0, y = 0;
			int n = 0;
			
			for(Player p : model.Players) {
				if(p.isAlive()) {
					x += p.Position.x;
					y = Math.max(y, p.Position.y);
					n++;
				}
			}
			
			if(n > 0) {
				// limit x movement
				double prevX = Position.x;
				double newX = x / n;
				double d = newX - prevX;
				double dAbs = Math.abs(d);
				
				if(dAbs > maxXMovement) {
					d *= maxXMovement / dAbs;
				}
				
				this.Position = new Vector(prevX + d, y);
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
