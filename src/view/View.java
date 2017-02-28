package view;

import controller.Options;
import javafx.scene.paint.Color;
import model.Game;
import model.Player;
import model.Vector;
import model.map.MapSegment;
import view.colors.BlendMode;
import view.colors.Blender;

public abstract class View {
	
	// constants
	public final int WIDTH = Options.WIDTH;
	public final int HEIGHT = Options.HEIGHT;
	private final int offsetX = WIDTH/2;
	
	public static final int WINDOW_WIDTH = 10;	
	public static final int WINDOW_HEIGHT = 16;
	
	public static final int WINDOW_X_OFFSET = 3;	
	public static final int WINDOW_Y_OFFSET = 12;
	
	protected Color[][] pixels = new Color[HEIGHT][WIDTH];
	
	public Color backgroundColor = new Color(0, 0, 0.42, 1);
	
	public Color wallColor = new Color(0, 1, 0, 1);
	
	public Vector Position = new Vector();
	
	private double smoothAlpha = 0;
	
	public View() {
		for(int y = 0; y<HEIGHT; y++) {
			for(int x = 0; x<WIDTH; x++) {
				pixels[y][x] = backgroundColor;
			}
		}
	}
	
	/**
	 * converts a game position to screen coordinates
	 * @param Position
	 * @return screen position
	 */
	public Vector toScreen(Vector Position) {
		return toScreen(Position.x, Position.y);
	}
	
	public Vector toScreen(int x, int y) {
		return new Vector(
				this.offsetX + x - this.Position.x,
				HEIGHT - 1 - (y - this.Position.y)
			);
	}
	
	public void setPixel(int x, int y, Color color) {
		if(0 <= x && x < WIDTH) {
			if(0 <= y && y < HEIGHT) {
				pixels[y][x] = color;
				
				return;
			}
		}
		
		//System.err.println("View.setPixel failed! x=" + x + " y=" + y);
	}
	
	public void setPixel(Vector p, Color color) {
		setPixel(p.x, p.y, color);
	}
	
	public void setPixel(Vector p, Color color, double alpha) {
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity() * alpha);
		setPixel(p.x, p.y, color);
	}
	
	public void drawMapSegment(MapSegment ms, int xPos, Game model) {
		final int y = HEIGHT - 1 - (ms.y - this.Position.y);
		
		if(y < 0 || y >= HEIGHT) {
			System.err.println("skipped map segment");
			return;
		}
		
		for(int x=0; x<WIDTH; x++) {
			Color clr = model.Map.isWall(xPos + x, ms) ? wallColor : backgroundColor;
							
			pixels[y][x] = clr;
		}
	}
	
	public void drawMapSegment(MapSegment ms, int xPos, int yOffset, double opacity, Game model) {
		final int y = HEIGHT - 1 - (ms.y - this.Position.y) + yOffset;
		
		if(y < 0 || y >= HEIGHT) { return; }
		
		for(int x=0; x<WIDTH; x++) {
			Color tmp = model.Map.isWall(xPos + x, ms) ? wallColor : backgroundColor;
			final double alpha = tmp.getOpacity() * opacity;
			Color clr = new Color(tmp.getRed(), tmp.getGreen(), tmp.getBlue(), alpha);
							
			pixels[y][x] = Blender.blend(BlendMode.NORMAL, clr, pixels[y][x]);
		}
	}
	
	// calculate view (camera) position
	private void updatePosition(Game model) {		
		
		final MapSegment ms = model.Map.data.get(Options.PLAYER_Y_POS);			
		this.Position.x = (ms.left + ms.right) / 2;
		
		if(model.Players.length == 1) {
			final Player player = model.Players[0];			
			this.Position.x = (this.Position.x + player.Position.x) / 2;
		}
		
		this.Position.y = model.Map.getYPosition();
	}
	
	protected void renderFrame(Game model) {
		
		updatePosition(model);
		
		final int xPos = this.Position.x - this.offsetX;
		smoothAlpha = model.Map.getYBuffer() * model.Map.getYBuffer();
		
		// draw map
		MapSegment previous = null;
		
		for(MapSegment part : model.Map.data) {
			
			drawMapSegment(part, xPos, model);
			
			if(Options.SMOOTHING) {
				if(previous != null) {
					drawMapSegment(part, xPos, 1, smoothAlpha, model);
				}
				
				previous = part;
			}
		}
		
		// draw players on top
		for(Player player : model.Players) {
			drawPlayer(player);
		}
	}
	
	private void drawPlayer(Player player) {
		if(!player.isAlive()) { return; }
		
		setPixel(toScreen(player.Position), Color.WHITE);
		
		// draw tail
		Vector prevPoint = null;
		for(Vector tp : player.Tail.points) {
			setPixel(toScreen(tp), player.Tail.color);
			
			if(Options.SMOOTHING) {
				if(prevPoint != null) {
					// draw point
					setPixel(toScreen(tp.x, tp.y - 1), player.Tail.color, smoothAlpha);
					// TODO: blend colors
				}
				
				prevPoint = tp;
			}
		}
	}
	
	abstract public void draw(Game model);
	
}
