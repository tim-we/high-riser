package view;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class LighthouseView extends JavaFXView implements View {
	
	private LighthouseNetwork LHNetwork = new LighthouseNetwork();
	
	private byte[] data = new byte[28 * 14 * 3];
	
	private WritableImage img = new WritableImage(28,14);
	private PixelReader pr = img.getPixelReader();
	
	private long lastFrame;
	private int MSPerFrame = 33; // milliseconds per frame
	
	public LighthouseView() throws UnknownHostException, IOException {
		super();
		
		setSize(28,14);
		
		setFPS(30);
		
		lastFrame = System.currentTimeMillis();
		
		LHNetwork.connect();
	}
	
	private void toByteArray() {
		
		canvas.snapshot(null, img);
		
		int i = 0;
		
		for(int y = 0; y<14; y++) {
			for(int x = 0; x<28; x++) {
				Color color = pr.getColor(x, y);
				
				data[i + 0] = (byte) (color.getRed()	* 255);
				data[i + 1] = (byte) (color.getGreen()	* 255);
				data[i + 2] = (byte) (color.getBlue()	* 255);
				
				i += 3;
			}
		}
		
		assert(i == (28 * 14 * 3));
	}
	
	public void draw() {
		if(System.currentTimeMillis() - lastFrame < MSPerFrame) {
			return;
		}		
		
		render();
		
		toByteArray();
		
		lastFrame = System.currentTimeMillis();
		
		try {
			LHNetwork.send(data);
		} catch (IOException e) {
			System.out.println("could not send image");
		}
	}
	
	public void setFPS(int fps) {
		if(fps < 1) { throw new IllegalArgumentException("FPS must be > 0"); }
		
		MSPerFrame = 1000 / fps;
	}
	
	/*
	// constants
	
	public static final int WINDOW_WIDTH = 10;	
	public static final int WINDOW_HEIGHT = 16;
	
	public static final int WINDOW_X_OFFSET = 3;	
	public static final int WINDOW_Y_OFFSET = 12;
	
	*/
	
}
