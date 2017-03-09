package view;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.Config;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;
import model.*;

public class LighthouseView extends JavaFXView implements View {
	
	private LighthouseNetwork LHNetwork = new LighthouseNetwork();
	
	private byte[] data = new byte[28 * 14 * 3];
	
	protected double VIEW_WIDTH = 28;
	protected double VIEW_HEIGHT = 14;
	
	private WritableImage img = new WritableImage(28,14);
	private PixelReader pr = img.getPixelReader();
	
	private boolean skip = false;
	
	public LighthouseView() throws UnknownHostException, IOException {
		super();
		
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
		if(skip) {
			skip = false;
		} else {
			skip = true;
			
			render();
			
			toByteArray();
			
			try {
				LHNetwork.send(data);
			} catch (IOException e) {
				System.out.println("could not send image");
			}
		}
		
	}
	
	/*
	// constants
	
	public static final int WINDOW_WIDTH = 10;	
	public static final int WINDOW_HEIGHT = 16;
	
	public static final int WINDOW_X_OFFSET = 3;	
	public static final int WINDOW_Y_OFFSET = 12;
	
	*/
	
}
