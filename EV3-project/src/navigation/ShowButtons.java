package navigation;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;

public class ShowButtons {
	public static void main(String[] args) throws Exception {
		// get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();
		// Keys class for buttons
		Keys buttons = wallE.getKeys();
		// press the ESCAPE key to exit the program
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			// clearing the LCD screen at first
			LCD.clear();
			// press the ENTER key so the ENTER will be
			// displayed on the LCD screen
			if (buttons.getButtons() == Keys.ID_ENTER) {
				// displaying ENTER on the LCD screen
				LCD.drawString("ENTER", 0, 0);
				// leave the string ENTER on the screen for 2 seconds
				Thread.sleep(2000);
			}
		}
	}
}
