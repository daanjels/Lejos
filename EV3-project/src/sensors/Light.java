package sensors;

/**
 * Use the ColorSensor in sensorport 4 to see how bright the surroundings are.
 * It checks the ambient light and returns a value between 0 and 1.
 * The values stay pretty low, pointing it to the sky yields values around 0.5.
 * I have no idea how I could put this to use, yet.
 * 
 * @author Argetype
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Light {

	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Port sp4 = wallE.getPort("S4");
		EV3ColorSensor lightsensor = new EV3ColorSensor(sp4);
		LCD.clear();
		String licht = "";
		
		while (!Button.ESCAPE.isDown()) {
			SampleProvider light = lightsensor.getAmbientMode();
			float[] sample = new float[light.sampleSize()];
			light.fetchSample(sample, 0);
			licht = "" + sample[0]*100;
			LCD.clear();
			LCD.drawString("Ambient light: ",0, 1);
			LCD.drawString(licht + "%", 0, 2);
			Delay.msDelay(333);
		}
		lightsensor.close();
	}
}
