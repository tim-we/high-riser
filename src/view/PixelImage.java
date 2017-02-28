package view;

import java.util.Arrays;

import javafx.scene.paint.Color;
import view.colors.BlendMode;
import view.colors.Blender;

public class PixelImage {
	/* default background color / color of empty pixels */
	public static final Color BGCOLOR = Color.BLACK;
	
	private int width;
	private int height;
	
	/* this 1dim-array of colors contains the color of each pixel going from top-left to bottom-right */
	private Color[] imageData;
	
	/**
	 * Constructor: Creates a new PixelImage with given width and height
	 * @param width The width of the PixelImage
	 * @param height the height of the PixelImage
	 */
	public PixelImage(int width, int height) {
		if(width<0 || height<0) { throw new IllegalArgumentException(); }
		
		this.width = width;
		this.height = height;
		this.imageData = new Color[width*height];
	}
	
	/* Constructor: creates empty PixelImage */
	public PixelImage() {
		this.width = 28;
		this.height = 14;
		this.imageData = new Color[width*height];
	}
	
	/**
	 * Constructor: Duplicates a given PixelImage
	 * @param img the PixelImage to duplicate
	 */
	public PixelImage(PixelImage img) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.imageData = new Color[width*height];
		
		this.imageData = Arrays.copyOf(img.getColorArray(), img.getColorArray().length);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	/**
	 * clears the actual PixelImage
	 */
	public void clear() {
		imageData = new Color[width*height];
	}
	
	/**
	 * Sets the PixelImage to a color
	 * @param color the Color the PixelImage shall show
	 */
	public void fill(Color color) {
		for(int i=0; i<imageData.length; i++) {
			if(imageData[i] == null) {
				imageData[i] = color;
			} else {
				imageData[i] = Blender.blend(BlendMode.NORMAL, imageData[i], color);
			}
		}
	} 
	
	/**
	 * Gives the index of a column, row position in the imageData array
	 * @param column the requested column
	 * @param row the requested row
	 * @return The position in the Array
	 */
	private int getArrayIndex(int column, int row) {
		if(column<0 || width<=column) { return -1; }
		if(row<0 || height<=row) { return -1; }
		
		return (row*width + column);
	}
	
	/**
	 * Gives the Color of a column, row position
	 * @param column the requested column
	 * @param row the requested row
	 * @param emptyColor the color to return if the position in the array is null
	 * @return {Color} of selected pixel or emptyColor if null or {null} if out of bounds
	 */
	public Color getPixel(int column, int row, Color emptyColor) {
		int i = getArrayIndex(column, row);
		
		if(i<0) { return null; }
		
		return this.imageData[i]==null ? emptyColor : this.imageData[i];
	}
	
	/**
	 * Returns the color of the requested position
	 * @param x the column
	 * @param y the row
	 * @return the color at x,y position or background-color if none there yet
	 */
	public Color getPixel(int x, int y) {
		return getPixel(x, y, BGCOLOR);
	}
	
	/**
	 * Sets the column, row position on a color
	 * @param column the requested column
	 * @param row the requested row
	 * @param color the color for this position
	 */
	public void setPixel(int column, int row, Color color) {
		int i = getArrayIndex(column, row);
			
		if(i>=0) { this.imageData[i] = color; }
	}
	
	public Color[] getColorArray() { return imageData; }
	
	private byte dbl2Byte(double value) {
		assert(0d <= value && value <= 1d);
		return (byte) Math.round(value * 255d);
	}
	
	/**
	 * Calculates a byteArray, as needed for Lighthouse from the
	 * ImageData array which contains the Colors of our PixelImage
	 * @return The ByteArray as needed for Lighthouse
	 */
	public byte[] getByteArray() {
		byte[] buf = new byte[width*height*3];
		
		int i = 0;
		
		for(int row=height-1; row>=0; row--) {
			for(int col=0; col<width; col++) {
				Color clr = getPixel(col, row, BGCOLOR);			
				
				buf[i]	  = dbl2Byte(clr.getRed());
				buf[i +1] = dbl2Byte(clr.getGreen());
				buf[i +2] = dbl2Byte(clr.getBlue());
				
				i = i+3;
			}
		}
		
		return buf;
	}
}
