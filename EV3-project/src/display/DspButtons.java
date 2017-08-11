package display;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import myEV3.Screen;

/**
 * @author Arqetype
 *
 */
public class DspButtons {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	
	GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final int SW = g.getWidth();
	final int SH = g.getHeight();
	
	void buttons() {
		g.clear();
		g.setFont(Font.getLargeFont());
		g.drawString("Buttons", SW/2, SH/2, GraphicsLCD.BOTTOM|GraphicsLCD.HCENTER);
		g.setFont(Font.getDefaultFont());
		for(;;) {
			g.setFont(Font.getDefaultFont());
			int but = Button.waitForAnyPress(1000);
			g.clear();
			String pressed = "";
			int x = SW/2, y = SH/2;
			int anchor = GraphicsLCD.HCENTER|GraphicsLCD.VCENTER;
			if (but == 0)
				pressed = "None";
			if ((but & Button.ID_ENTER) != 0) {
				pressed += "Enter ";
				anchor = GraphicsLCD.HCENTER;
				g.setFont(Font.getSmallFont());
			}
			if ((but & Button.ID_LEFT) != 0) {
				pressed += "Left ";
				x = 0;
				anchor = GraphicsLCD.LEFT|GraphicsLCD.VCENTER;
			}
			if ((but & Button.ID_RIGHT) != 0) {
				pressed += "Right ";
				x = SW;
				anchor = GraphicsLCD.RIGHT|GraphicsLCD.VCENTER;
			}
			if ((but & Button.ID_UP) != 0) {
				pressed += "Up ";
				y = 0;
				anchor = GraphicsLCD.HCENTER|GraphicsLCD.TOP;
				g.setFont(Font.getLargeFont());
			}
			if ((but & Button.ID_DOWN) != 0) {
				pressed += "Down ";
				y = SH;
				anchor = GraphicsLCD.HCENTER|GraphicsLCD.BOTTOM;
				g.setFont(Font.getLargeFont());
			}
			if ((but & Button.ID_ESCAPE) != 0)
				pressed += "Escape ";
			g.drawString(pressed, x, y, anchor);
			if (but == Button.ID_ESCAPE)
				break;
		}
		Button.waitForAnyPress(1000);
		g.clear();
		g.refresh();
	}

	public static void main(String[] args) {
		DspButtons sample = new DspButtons();
		Screen.flow("Screen demo\nWidth: " + sample.SW + "\nHeight: "+ sample.SH);
		Button.waitForAnyPress();
		sample.buttons();
	}

}
