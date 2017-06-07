package navigation;

import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;

public class KeyPresses {
	public static void main(String[] args) throws InterruptedException {
		// get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();
		// LCD class for displaying and Keys class for buttons
		Keys buttons = wallE.getKeys();
		TextLCD lcddisplay = wallE.getTextLCD();
		// continue waiting for button pressed while the user has not pressed escape button
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			// display a message for enter
			if (buttons.getButtons() == Keys.ID_ENTER)
			{
				lcddisplay.clear();
				lcddisplay.drawString("ENTER", 7, 3);
			}
			// display a message for left button
			else if (buttons.getButtons() == Keys.ID_LEFT) {
				lcddisplay.clear();
				lcddisplay.drawString("LEFT", 0, 3);
			}
			// display a message for right button
			else if (buttons.getButtons() == Keys.ID_RIGHT) {
				lcddisplay.clear();
				lcddisplay.drawString("RIGHT", 13, 3);
			}
			// display a message for up button
			else if (buttons.getButtons() == Keys.ID_UP) {
				lcddisplay.clear();
				lcddisplay.drawString("UP", 8, 0);
			}
			// display a message for down button
			else if (buttons.getButtons() == Keys.ID_DOWN) {
				lcddisplay.clear();
				lcddisplay.drawString("DOWN", 7, 7);
			}
		}
	}
}
