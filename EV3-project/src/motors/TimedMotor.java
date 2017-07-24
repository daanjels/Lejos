package motors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

/**
 * @author Arqetype
 * This program will display the tachocount of both motors every 200 milliseconds
 * That way the difference between both speeds can be concluded
 * The difference should be very small, maximum 1
 * Added an option to set the speed
 * 
 * In practice it is sometimes 2 or 3, at greater speeds the error is bigger
 * I think it also depends on the battery charge
 */

public class TimedMotor {

	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	
	public static void main(String[] args) {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		
		// define variables to hold row to print on
		int tachoRow = 0;
		int maxTachoCountDiff = Integer.MIN_VALUE;
		int currTachoCountDiff;
		int mySpeed = 360;
		
//		LCD.drawString("0123456789abcdefgh", 0, 0);
		LCD.drawString("Motors vergelijken", 0, 0);
		LCD.drawString("", 0, 1);
		LCD.drawString("UP: sneller", 0, 2);
		LCD.drawString("DOWN: trager", 0, 3);
		LCD.drawString("ENTER: starten", 0, 4);
		LCD.drawString("ESCAPE: stoppen", 0, 5);
		LCD.drawString("", 0, 6);
		LCD.drawString("Snelheid: " + mySpeed, 0, 7);

		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_UP) {
				mySpeed = mySpeed + 10;
				if (mySpeed > 770) mySpeed = 770;
				LCD.drawString("Snelheid: " + mySpeed + " ", 0, 7);
			}
			if (buttons.getButtons() == Keys.ID_DOWN) {
				mySpeed = mySpeed - 10;
				if (mySpeed < 30) mySpeed = 30;
				LCD.drawString("Snelheid: " + mySpeed + " ", 0, 7);
			}
			if (buttons.getButtons() == Keys.ID_ENTER) {
				tachoRow = 1;
				currTachoCountDiff = 0;
				maxTachoCountDiff = 0;
				LCD.clear();
				LCD.drawString("Timed motors", 0, 0);
				LCD.drawString("Snelheid: " + mySpeed, 0, 7);
				
				// instantiate stopwatch for setting up timer
				Stopwatch sw = new Stopwatch();
				
				RIGHT_MOTOR.resetTachoCount();
				LEFT_MOTOR.resetTachoCount();
				
				// set motorspeed
				RIGHT_MOTOR.setSpeed(mySpeed);
				LEFT_MOTOR.setSpeed(mySpeed);
				
				// start motors
				RIGHT_MOTOR.forward();
				LEFT_MOTOR.forward();
				
				// perform test 5 times
				for (int i = 0; i < 5; i++) {
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
				LCD.drawString("Max verschil: " + maxTachoCountDiff, 0, tachoRow);
//				LCD.drawString("" + maxTachoCountDiff, 0, tachoRow + 1);
			}
			Delay.msDelay(100);
		}
		
		LCD.clear();
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();

	}

	private static int displayTachoCounts(int row) {
		// store the tacho counts for the two motors
		int tachoCountRight = RIGHT_MOTOR.getTachoCount();
		int tachoCountLeft = LEFT_MOTOR.getTachoCount();
		
		// display tacho counts
		LCD.drawString("M1: " + tachoCountRight + " M2: " + tachoCountLeft, 0, row);
		
		// return the difference
		return Math.abs(tachoCountRight - tachoCountLeft);
	}
}