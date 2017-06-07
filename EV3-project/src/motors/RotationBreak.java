package motors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

/**
 * @author Arqetype
 * variant to inertia class
 * turn the motor around twenty times and display tacho continuously
 * when a button is pressed, stop the motor
 * when it has fully stopped, display final tachocount
 */

public class RotationBreak {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		int tacho = 0;
		Sound.twoBeeps();
		LCD.drawString("Druk om te starten", 0, 0);
		buttons.waitForAnyPress();
		Delay.msDelay(500);
		LCD.clear();
		LCD.drawString("Tachograaf: ", 0, 1);
		
		// rotate 7200 degrees while displaying tachocount
		leftMotor.rotate(7200, true);
		while (leftMotor.isMoving()) {
			tacho = leftMotor.getTachoCount();
			LCD.drawString("" + tacho, 12, 1);
			// stop the motor if a button is pressed
			if (buttons.readButtons() > 0) leftMotor.stop();
		}
		
		// wait for the motor to stop completely
		while (leftMotor.getRotationSpeed() > 0);
		
		// display tachocount after motor has fully stopped
		LCD.drawString("Na volledige stop", 0, 3);
		LCD.drawString("Tachograaf: " + leftMotor.getTachoCount(), 0, 4);
		
		LCD.drawString("Het verschil: ", 0, 6);
		LCD.drawString("" + (leftMotor.getTachoCount() - tacho), 14, 6);
		
		// End program when button is pressed
		buttons.waitForAnyPress();
		LCD.clear();
		leftMotor.close();
	}
}
