package motors;

/**
 * @author Arqetype
 * A simple motor test using one EV3 motor connected to port A
 * Set motor speed to 720, that is two revolutions
 * Stop the motor and display the tachometer count
 * Wait until motor has really stopped and display tachometer reading again
 * Press a key to finish
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class Inertia {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);

		//	get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();

		//	instantiate Keys class for buttons
		Keys buttons = wallE.getKeys();

		//	block the thread until a button is pressed
		buttons.waitForAnyPress();

		//	set motor to move 720 degrees per second
		leftMotor.setSpeed(720);

		//	start forward movement
		leftMotor.forward();

		//	a counter to count the number of degrees rotated
		int count = 0;
		//	continue moving until motor has rotated 720 degrees
		while (count < 720)
			count = leftMotor.getTachoCount();

		//	stop the motor
		leftMotor.stop();

		//	display the tachometer reading
		LCD.drawString("Tachograaf: " + count, 0, 0);

		//	wait for motor to actually stop.
		while (leftMotor.getRotationSpeed() > 0);

		//	display tacho count
		//	this number will be higher than previous due to motor inertia
		LCD.drawString("Tachograaf: " + leftMotor.getTachoCount(), 0, 1);

		//block the thread until a button is pressed
		buttons.waitForAnyPress();

		LCD.clear();
		leftMotor.close();

	}

}
