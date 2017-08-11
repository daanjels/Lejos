package display;

import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class DspScroll {
	GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final int SW = g.getWidth();
	final int SH = g.getHeight();
	
	void TopDown () {
		int line = g.getFont().getHeight();
		
//		for (int i = 0; i <16; i++) {
//			g.clear();
//			g.drawString("Hello there! " + line, i*4, i*line/2, GraphicsLCD.LEFT|GraphicsLCD.BASELINE);
//			Delay.msDelay(250);
//		}
//
//		Delay.msDelay(2000);

		g.drawString("Hello there", 0, SH - line, GraphicsLCD.LEFT);	// put a string at the bottom
		g.setColor(GraphicsLCD.WHITE);	// color is used when filling

		//		for (int i = 0; i < 7; i++) {	// move the string up the display
//			Delay.msDelay(250);
//			// copyArea(Source X, Source Y, Source Width, Source Height, Dest. X, Dest. Y, anchor)
//			g.copyArea(0, SH - (i+1) * line, SW, line, 0, SH - (i+2)*line, 0);
//			g.fillRect(0, SH - (i+1) * line, SW, line);
//		}
//		for (int i = 6; i >= 0; i--) {	// move the string down the display
//			Delay.msDelay(250);
//			g.copyArea(0, SH - (i + 2) * line, SW, line, 0, SH - (i + 1) * line, 0);
//			g.fillRect(0, SH - (i + 2) * line, SW, line);
//		}
//
//		Delay.msDelay(2000);
		
		for (int i = 0; i < 7*line; i++) {	// move per pixel
			Delay.msDelay(10);
			g.copyArea(0, SH - line - i, SW, line, 0, SH - line - (i + 1), 0);
			g.fillRect(0, SH - i, SW, 1);
//			g.refresh();
		}
		for (int i = 7*line - 1; i >= 0; i--) {
			Delay.msDelay(10);
			g.copyArea(0, SH - line - (i + 1), SW, line, 0, SH - line - i, 0);
			g.fillRect(0, SH - line - (i + 1), SW, 1);
//			g.refresh();
		}
		
		g.setAutoRefresh(false);
		for (int i = 0; i < 7*line; i++) {	// move per pixel
			Delay.msDelay(10);
			g.copyArea(0, SH - line - i, SW, line, 0, SH - line - (i + 1), 0);
			g.fillRect(0, SH - i, SW, 1);
			g.refresh();	// this is the trick
		}
		for (int i = 7*line - 1; i >= 0; i--) {
			Delay.msDelay(10);
			g.copyArea(0, SH - line - (i + 1), SW, line, 0, SH - line - i, 0);
			g.fillRect(0, SH - line - (i + 1), SW, 1);
			g.refresh();	// this is the trick
		}
		g.setAutoRefresh(true);
		g.refresh();

		Delay.msDelay(2000);
		g.setColor(GraphicsLCD.BLACK);
	}

	public static void main(String[] args) {
		DspScroll sample = new DspScroll();
		sample.TopDown();
	}

}
