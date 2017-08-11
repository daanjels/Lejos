package sensors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor; import lejos.hardware.sensor.SensorMode;

/**
 * Touch sensor class
 * 
 * @author Arqetype
 *
 */

public class Touch {
	public static void main(String[] args) throws Exception {
		// get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();		// Keys class for buttons
		TextLCD lcddisplay = wallE.getTextLCD();	// LCD class for displaying 
		
		Port portS2 = wallE.getPort("S2");	// get a port instance
		EV3TouchSensor touchSensor = new EV3TouchSensor(portS2);	// Get an instance of the touch EV3 sensor 
		SensorMode toucher = touchSensor.getTouchMode();			// get an instance of this sensor in measurement mode
		float[] samplevalue = new float[toucher.sampleSize()];		// initialize an array of floats for fetching samples

		lcddisplay.clear();
		lcddisplay.drawString("Touch sensor test!", 0, 2);
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			toucher.fetchSample(samplevalue, 0);		// fetch a sample
			lcddisplay.drawString("Waarde: " + samplevalue[0], 1, 4);
			Thread.sleep(50);
		}
		
		lcddisplay.clear();
		lcddisplay.drawString("Afsluiten...", 0, 4);
		touchSensor.close();	// close the sensor to prevent resource leak
		System.exit(0);
	}
}