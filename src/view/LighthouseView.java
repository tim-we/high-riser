package view;

import controller.Config;
import javafx.scene.paint.Color;
import model.Game;
import model.*;
import model.map.MapSegment;
import view.colors.BlendMode;
import view.colors.Blender;

public class LighthouseView implements View {
	
	// constants
	public final int WIDTH = Config.WIDTH;
	public final int HEIGHT = Config.HEIGHT;
	private final int offsetX = WIDTH/2;
	
	public static final int WINDOW_WIDTH = 10;	
	public static final int WINDOW_HEIGHT = 16;
	
	public static final int WINDOW_X_OFFSET = 3;	
	public static final int WINDOW_Y_OFFSET = 12;
	
	private Game Model;
	private Camera Camera;
	protected PixelImage data = new PixelImage(28,14);
	
	public Color backgroundColor = new Color(0, 0, 0.42, 1);
	
	public Color wallColor = new Color(0, 1, 0, 1);
	
	/**
	 * converts a game position to screen coordinates
	 * @param Position
	 * @return screen position
	 */
	public Vector toScreen(Vector Position) {
		return toScreen(Position.x, Position.y);
	}
		
	public Vector toScreen(double x, double y) {
		return new Vector(
				this.offsetX + x - Camera.Position.x,
				HEIGHT - 1 - (y - Camera.Position.y)
			);
	}
		
//	public void setPixel(Vector p, Color color, double alpha) {
//		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity() * alpha);
//		setPixel(p.x, p.y, color);
//	}
		
	public void drawMapSegment(MapSegment ms) {
		
	}
		
//	public void drawMapSegment(MapSegment ms, int xPos, int yOffset, double opacity, Game model) {
//		final int y = HEIGHT - 1 - (ms.y - this.Position.y) + yOffset;
//		
//		if(y < 0 || y >= HEIGHT) { return; }
//		
//		for(int x=0; x<WIDTH; x++) {
//			Color tmp = model.Map.isWall(xPos + x, ms) ? wallColor : backgroundColor;
//			final double alpha = tmp.getOpacity() * opacity;
//			Color clr = new Color(tmp.getRed(), tmp.getGreen(), tmp.getBlue(), alpha);
//							
//			pixels[y][x] = Blender.blend(BlendMode.NORMAL, clr, pixels[y][x]);
//		}
//	}
	
	protected void renderFrame(Game model) {
		
//		updatePosition(model);
//		
//		final int xPos = this.Position.x - this.offsetX;
//		smoothAlpha = model.Map.getYBuffer() * model.Map.getYBuffer();
//		
//		// draw map
//		MapSegment previous = null;
//		
//		for(MapSegment part : model.Map.data) {
//			
//			drawMapSegment(part, xPos, model);
//			
//			if(Config.SMOOTHING) {
//				if(previous != null) {
//					drawMapSegment(part, xPos, 1, smoothAlpha, model);
//				}
//				
//				previous = part;
//			}
//		}
		
		// draw players on top
		for(Player player : model.Players) {
			drawPlayer(player);
		}
	}
	
	private void drawPlayer(Player player) {
//		if(!player.isAlive()) { return; }
//		
//		setPixel(toScreen(player.Position), Color.WHITE);
//		
//		// draw tail
//		Vector prevPoint = null;
//		for(Vector tp : player.Tail.points) {
//			setPixel(toScreen(tp), player.Tail.color);
//			
//			if(Config.SMOOTHING) {
//				if(prevPoint != null) {
//					// draw point
//					setPixel(toScreen(tp.x, tp.y - 1), player.Tail.color, smoothAlpha);
//					// TODO: blend colors
//				}
//				
//				prevPoint = tp;
//			}
//		}
	}

	public void draw() {
		if(this.Model != null) {
			
		} else {
			
		}
		
	}

	public void setModel(Game model) {
		this.Model = model;
		this.Camera = model.Camera;
	}
	
}
