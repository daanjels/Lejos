package motors;

/**
 * @author Arqetype
 * A test to look at rotation functions of EV3 motors
 * The motor first rotates one round, getting a tachocount of 360
 * Then it turns to the 360 degree angle
 * The tacocount should be 360 or 359
 * Using angles is more precise than the stop() method
 * It is not as precise as I thought...
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class RotateAngles {

	public static void main(String[] args) {
		
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		LCD.drawString("Druk op een knop!", 0, 1);
		buttons.waitForAnyPress();
		LCD.clear();
		
		// set motor A to turn 360 degrees per second
		leftMotor.setSpeed(180);
		
		// rotate the motor one full revolution
		leftMotor.rotate(360);
		LCD.drawString("360 graden draaien", 0, 0);
		
		// display tachocount
		LCD.drawString("Tachograaf: " + leftMotor.getTachoCount(), 0, 1);
		Delay.msDelay(1000);
		// rotate to 360 degree angle
		leftMotor.rotateTo(360);
		LCD.drawString("Naar 360 graden", 0, 3);
		
		// display tachocount
		LCD.drawString("Tachograaf: " + leftMotor.getTachoCount(), 0, 4);
		
		LCD.drawString("Druk een knop", 2, 6);
		LCD.drawString("om te stoppen", 2, 7);
		buttons.waitForAnyPress();
		
		LCD.clear();
		leftMotor.close();
	}
}
