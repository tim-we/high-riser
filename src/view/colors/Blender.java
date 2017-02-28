package view.colors;

import javafx.scene.paint.Color;

public class Blender {
	
	/**
	 * blends two colors
	 * @param mode blending mode
	 * @param a top color
	 * @param b bottom color
	 * @return blended color
	 */
	public static Color blend(BlendMode mode, Color a, Color b) {
		switch(mode) {
			case ADDITIVE:
				return additive(a, b);
			default: 
				return porterduff(a,b);
		}
	}
	
	/**
	 * blend two colors with the porter duff algorithm
	 * @param a top color
	 * @param b bottom color
	 * @return
	 */
	private static Color porterduff(Color a, Color b) {
		final double c = 1.0 - a.getOpacity();
		final double alpha = a.getOpacity() + c * b.getOpacity();
		
		if(alpha == 0) { return Color.BLACK; }
		
		final double aci = 1.0 / alpha;
		final double aab = c * b.getOpacity();
		
		return new Color(
			aci * (a.getOpacity() * a.getRed()		+  aab * b.getRed()),
			aci * (a.getOpacity() * a.getGreen()	+  aab * b.getGreen()),
			aci * (a.getOpacity() * a.getBlue()     +  aab * b.getBlue()),
			alpha
		);
		
	}
	
	private static Color additive(Color a, Color b) {
		final double c = 1.0 - a.getOpacity();
		final double alpha = a.getOpacity() + c * b.getOpacity();
		
		if(alpha == 0) { return Color.BLACK; }
		
		return new Color(
				a.getRed()		+ b.getOpacity() * b.getRed(),
				a.getGreen()	+ b.getOpacity() * b.getGreen(),
				a.getBlue()		+ b.getOpacity() * b.getBlue(),
				alpha
			);
	}
	
}
