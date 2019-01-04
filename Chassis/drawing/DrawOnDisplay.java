package drawing;

/**
 * Simple program to draw the number 2019 on the EV3 screen
 * Added some extra's to show the number a few times at random places
 * finish with 'best wishes'
 * 
 * I should like to create an extra font size for the EV3 brick...
 * Let's put that on the idea's list
 * 
 * @author Arqetype
 *
 */

import java.util.concurrent.ThreadLocalRandom;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

public class DrawOnDisplay {
	GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
	final int SW = g.getWidth();
	final int SH = g.getHeight();

	public static void main(String[] args) {
		DrawOnDisplay sample = new DrawOnDisplay();
		sample.draw2019(0, 0);
		Delay.msDelay(1000);
		for (int i = 0; i < 2; i++) { 
			int randomX = ThreadLocalRandom.current().nextInt(0, 64+1);
			int randomY = ThreadLocalRandom.current().nextInt(0, 84+1);
			sample.draw2019(randomX, randomY);
			Delay.msDelay(1000);
		}

		sample.draw2019(32, 25);
		Delay.msDelay(500);
		sample.bestWishes();
		Button.waitForAnyPress();
//		sample.fontSizes();
//		Button.waitForAnyPress();
	}

	public void draw2019 (int x, int y) {
		g.clear();
		g.setColor(GraphicsLCD.BLACK); // or WHITE
		g.setStrokeStyle(GraphicsLCD.SOLID); //or DOTTED
		// draw an arc, circle diameter 30, top left of the circle is X and Y
		// start at 180°, turn clockwise for 270°
		g.drawArc(x, y, 30, 30, 180, -270); 
		g.drawArc(x+1, y+1, 28, 28, 180, -270); // make it a double line
		g.drawArc(x, y+29, 30, 30, 90, 90);
		g.drawArc(x+1, y+30, 28, 28, 90, 90); // make it a double line
		g.drawLine(x, y+45, x+30, y+45);
		g.drawLine(x, y+44, x+30, y+44); // make it a double line
		g.drawArc(x+35, y+15, 30, 30, 270, 360);
		g.drawArc(x+34, y+14, 32, 32, 270, 360); // make it a double line
		g.drawLine(x+75, y+44, x+75, y+1);
		g.drawLine(x+76, y+44, x+76, y+1); // make it a double line
		g.drawArc(x+85, y, 30, 30, 0, 360);
		g.drawArc(x+86, y+1, 28, 28, 0, 360); // make it a double line
		g.drawArc(x+70, y, 45, 45, 0, -90);
		g.drawArc(x+71, y+1, 43, 43, 0, -90); // make it a double line
	}
	
	public void bestWishes () {
		g.setFont(Font.getDefaultFont());
		g.drawString("best wishes", SW/2, 80, 1);
	}

//	public void fontSizes() {
//		g.clear();
//		Font lettertype = Font.getSmallFont();
//		int FH = lettertype.getHeight();
//		g.drawString("Small Font: " + FH, 5, 10, 0);
//		lettertype = Font.getDefaultFont();
//		FH = lettertype.getHeight();
//		g.drawString("Default Font: " + FH, 5, 30, 0);
//		lettertype = Font.getLargeFont();
//		FH = lettertype.getHeight();
//		g.drawString("Large Font: " + FH, 5, 50, 0);		
//	}

}