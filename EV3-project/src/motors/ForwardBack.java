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
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);

		// get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();

		// instantiated LCD class for displaying and Keys class for buttons
		Keys buttons = wallE.getKeys();
		LCD.drawString("Druk op een knop", 0, 1);
		LCD.drawString("om vooruit te gaan", 0, 2);
		
		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// move robot forward and display status on LCD change directions when button is pressed 
		leftMotor.forward();
		rightMotor.forward();
		LCD.clear();
		LCD.drawString("Vooruit!", 0, 0);
		LCD.drawString("Druk op een knop", 0, 1);
		LCD.drawString("om achteruit", 0, 2);
		LCD.drawString("te gaan", 0, 3);

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// move robot backward and display status on LCD 
		leftMotor.backward();
		rightMotor.backward();
		LCD.clear();
		LCD.drawString("Achteruit!", 0, 0);
		LCD.drawString("Druk op een knop", 0, 1);
		LCD.drawString("om te stoppen", 0, 2);

		// block the thread until a button is pressed
		buttons.waitForAnyPress();

		// stop robot and display status on LCD 
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