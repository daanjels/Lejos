package trials;

// import EV3 hardware packages for EV brick finding, activating keys and LCD
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.TextLCD;

public class HelloEv3 {
	public static void main(String[] args) {
		// get EV3 brick
		EV3 wall_e = (EV3) BrickFinder.getLocal();
		// instantiate LCD class for displaying and Keys class for buttons
		Keys buttons = wall_e.getKeys();
		TextLCD display = wall_e.getTextLCD();
		// drawing text on the LCD screen based on
		// coordinates
		display.drawString("Hello Eve!", 2, 4);
		// exit program after any button pressed
		buttons.waitForAnyPress();
	}
}