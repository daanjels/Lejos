package motors;

/**
 * @author Arqetype
 * A simple motor test using two EV3 motors
 * The left motor should be connected to port C
 * The right motor should be connected to port B
 * Press a key to make the motors go forward
 * Press a key to make the motors go backward
 * Press a key to make the motors stop
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class ForwardBack {

	public static void main(String[] args) {

		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);

		// get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();

		// instantiated LCD class for displaying and Keys class for buttons
		Keys buttons = wallE.getKeys();
		LCD.drawString("Motor tester", 0, 0);
		LCD.drawString("Linkermotor:", 0, 1);
		LCD.drawString("-LEFT -> vooruit", 0, 2);
		LCD.drawString("-DOWN -> achteruit", 0, 3);
		LCD.drawString("Rechtermotor:", 0, 4);
		LCD.drawString("-RIGHT-> vooruit", 0, 5);
		LCD.drawString("-UP   -> achteruit", 0, 6);
		LCD.drawString("ESAPE -> verlaten", 0, 7);
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_RIGHT) {
				leftMotor.stop();
				rightMotor.stop();
				rightMotor.forward();
			}
			if (buttons.getButtons() == Keys.ID_LEFT) {
				leftMotor.stop();
				rightMotor.stop();
				leftMotor.forward();
			}
			if (buttons.getButtons() == Keys.ID_UP) {
				leftMotor.stop();
				rightMotor.stop();
				rightMotor.backward();
			}
			if (buttons.getButtons() == Keys.ID_DOWN) {
				leftMotor.stop();
				rightMotor.stop();
				leftMotor.backward();
			}
		}
		leftMotor.stop();
		rightMotor.stop();
		LCD.clear();
		LCD.drawString("Gestopt!", 0, 2);
		Delay.msDelay(5000);
		
		// exit program after any button pressed, it is good practice to close the motors
		leftMotor.close();
		rightMotor.close();
		System.exit(0);
	}
}