package motors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Stopwatch;

/**
 * @author Arqetype
 * This program will display the tachocount of both motors every 200 milliseconds
 * That way the difference between both speeds can be concluded
 * The difference should be very small, maximum 1
 */

public class TimedMotor {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void main(String[] args) {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		
		// define variables to hold row to print on
		int tachoRow = 0;
		int maxTachoCountDiff = Integer.MIN_VALUE;
		int currTachoCountDiff;

		// instantiate stopwatch for setting up timer
		Stopwatch sw = new Stopwatch();
		
		// set motorspeed
		LEFT_MOTOR.setSpeed(360);
		RIGHT_MOTOR.setSpeed(360);
		
		// start motors
		LEFT_MOTOR.forward();
		RIGHT_MOTOR.forward();
		
		// perform test 4 times
		for (int i = 0; i < 4; i++) {
			sw.reset();
			while (sw.elapsed() < 200) Thread.yield();
			
			// reset max if changed
			currTachoCountDiff = displayTachoCounts(tachoRow++);
			if (currTachoCountDiff > maxTachoCountDiff) maxTachoCountDiff = currTachoCountDiff;
		}
		
		// stop the motors
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.stop();
		
		// display max difference and wait for buttonpress
		LCD.drawString("Maximum verschil:", 0, tachoRow);
		LCD.drawString("" + maxTachoCountDiff, 0, tachoRow + 1);
		
		buttons.waitForAnyPress();
		
		LCD.clear();
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();

	}

	private static int displayTachoCounts(int row) {
		// store the tacho counts for the two motors
		int tachoCountLeft = LEFT_MOTOR.getTachoCount();
		int tachoCountRight = RIGHT_MOTOR.getTachoCount();
		
		// display tacho counts
		LCD.drawString("M1: " + tachoCountLeft + " M2: " + tachoCountRight, 0, row);
		
		// return the difference
		return Math.abs(tachoCountLeft - tachoCountRight);
	}
}
