package trials;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * @author Arqetype
 * Just a simple class to show something on the screen of the EV3 brick
 * You can run it as leJOS EV3 program. It will be compiled, downloaded and run on EV3
 * You can also debug your program using Debug As > leJOS EV3 program
 */
public class EersteKlas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LCD.drawString("Plugin test", 0, 4);
		Delay.msDelay(5000);
	}
}