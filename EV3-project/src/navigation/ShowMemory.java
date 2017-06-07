package navigation;

import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;

public class ShowMemory {
	
	public static void main(String[] args) throws InterruptedException {
	// get EV3 brick
	EV3 wallE = (EV3) BrickFinder.getLocal();
	// instantized LCD class for displaying and Keys class for buttons
	Keys buttons = wallE.getKeys();
	TextLCD lcddisplay = wallE.getTextLCD();
	// drawing text on the LCD screen based on coordinates
	lcddisplay.drawString("Free RAM:", 0, 1);
	// displaying free memory on the LCD screen
	lcddisplay.drawInt((int) Runtime.getRuntime().freeMemory(), 0, 2);
	// exit program after any button pressed
	buttons.waitForAnyPress();
	}
}
