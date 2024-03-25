package view;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class LighthouseView extends View {
	
	private LighthouseNetwork LHNetwork = new LighthouseNetwork(Config.USER, Config.PASSWORD);
	
	private byte[] data = new byte[28 * 14 * 3];
	
	private WritableImage img = new WritableImage(28,14);
	private PixelReader pr = img.getPixelReader();
	
	private long lastFrame;
	private int MSPerFrame = 33; // milliseconds per frame
	
	private Canvas canvas = new Canvas(28,14);
	
	public LighthouseView() throws UnknownHostException, IOException {
		super(28,14,2);
		
		setCTX(canvas);
		
		// low res settings for radius, tail width & gap fill
		setValues(0.05, 0.042, 0.03);
		setCaveColor(new Color(0, 0, 0.15, 1));
		
		setFPS(30);
		
		lastFrame = System.currentTimeMillis();
		
		LHNetwork.connect();
		
		System.out.println("connected to the lighthouse!");
	}
	
	// convert to lighthouse API data format
	private void toByteArray() {
		
		// unfortunately the javafx GraphicsContext/Canvas do not offer methods to read pixels
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
			// skip frames to reduce framerate
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
	
}
