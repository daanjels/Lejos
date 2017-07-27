package sensors;

/**
 * Use the ColorSensor in sensorport 4 to define the colour of a lego brick.
 * Using the values for Red, Green and Blue, figure out the colour.
 * NOTE: use lego bricks or something similar with bright saturated colours
 * Place the bricks very close to the sensor, like maximum 3mm.
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

public class Colour {

	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Port sp4 = wallE.getPort("S4");
		EV3ColorSensor lightsensor = new EV3ColorSensor(sp4);
		LCD.clear();
		String rgb = "R/G/B";
		String kleur = "grijs";
		
		while (!Button.ESCAPE.isDown()) {
			SampleProvider color = lightsensor.getRGBMode();
			float[] colorsample = new float[color.sampleSize()];
			color.fetchSample(colorsample, 0);
			rgb = "R:" + (int)(colorsample[0]*256) + " G:" + (int)(colorsample[1]*256) + " B:" + (int)(colorsample[2]*256);
			if (colorsample[0] < 0.04 && colorsample[1] < 0.03 && colorsample[2] < 0.02) kleur = "zwart";
			if (colorsample[0] > 0.10) kleur = "rood";
			if (colorsample[1] > 0.10) kleur = "groen";
			if (colorsample[2] > 0.03) kleur = "blauw";
			if (colorsample[0] > 0.12 && colorsample[1] > 0.04) kleur = "geel";
			if (colorsample[0] > 0.20 && colorsample[1] > 0.10 && colorsample[2] > 0.03) kleur = "wit";
			LCD.clear();
			LCD.drawString("Colour: " + kleur, 0, 1);
			LCD.drawString(rgb, 0, 3);
//			LCD.drawString("R: " + colorsample[0], 0, 4);
//			LCD.drawString("G: " + colorsample[1], 0, 5);
//			LCD.drawString("B: " + colorsample[2], 0, 6);
			Delay.msDelay(500);
		}
		lightsensor.close();
	}
}