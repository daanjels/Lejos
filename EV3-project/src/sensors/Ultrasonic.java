package sensors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Class to test the Ev3 ultrasonic sensor
 * 
 * @author Arqetype
 *
 */
public class Ultrasonic {
	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();	// get EV3 brick
		Keys buttons = wallE.getKeys();
		TextLCD lcddisplay = wallE.getTextLCD();
		
		Port portS1 = wallE.getPort("S1");	// get a port instance
		
		EV3UltrasonicSensor sonicSensor = new EV3UltrasonicSensor(portS1);	// Get an instance of the Ultrasonic EV3 sensor
		SampleProvider sonicdistance = sonicSensor.getDistanceMode();	// get an instance of this sensor in measurement mode
		float[] sonicSample = new float[sonicdistance.sampleSize()];	// initialize an array of floats for fetching samples
		float sonicResult;

		lcddisplay.clear();
		lcddisplay.drawString("UltraSonic sensor", 0, 0);
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			sonicdistance.fetchSample(sonicSample, 0);	// fetch a sample
			sonicResult = sonicSample[0] * 700;
			if (sonicResult > 0) {
				lcddisplay.drawString("Afstand: cm      ", 1, 2);
				lcddisplay.drawString("Afstand: cm " + sonicResult, 1, 2);
				Delay.msDelay(200);
			}
		}
		sonicSensor.close();	// close the sensor to prevent resource leak
		
		lcddisplay.clear();
		lcddisplay.drawString("Sonar sensor", 0, 0);
		EV3IRSensor sonarSensor = new EV3IRSensor(portS1);
		SampleProvider sonarDistance = sonarSensor.getDistanceMode();
		float[] sonarSample = new float[sonarDistance.sampleSize()];	// initialize an array of floats for fetching samples
		double sonarResult;

		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			sonarDistance.fetchSample(sonarSample, 0);	// fetch a sample
			sonarResult = sonarSample[0] * 0.5;
			if (sonarResult > 0) {
				lcddisplay.drawString("Afstand: cm      ", 1, 2);
				lcddisplay.drawString("Sonar: cm " + sonarResult, 1, 2);
				Delay.msDelay(200);
			}
		}
		
		lcddisplay.clear();
		lcddisplay.drawString("Afsluiten...", 0, 4);
		sonarSensor.close();
		System.exit(0);
	}
}
