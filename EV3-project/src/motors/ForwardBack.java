package motors;

/**
 * @author Arqetype
 * A simple motor test using tow EV3 motors
 * The left motor should be connected to port A
 * The right motor should be connected to port C
 * Press a key to make the motors go forward
 * Press a key to make the motors go backward
 * Press a key to make the motors stop
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; import lejos.hardware.port.MotorPort;

public class ForwardBack {

	public static void main(String[] args) {
		EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
		
		// get EV3 brick
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		
		// instantiated LCD class for displaying and Keys class for buttons
		Keys buttons = ev3brick.getKeys();
		
		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		
		// move robot forward and display status on LCD 
		// change directions when button is pressed 
		LEFT_MOTOR.forward();
		RIGHT_MOTOR.forward();
		LCD.drawString("Vooruit!", 0, 0);
		
		// block the thread until a button is pressed
		buttons.waitForAnyPress();
		
		// move robot backward and display status on LCD 
		LEFT_MOTOR.backward();
		RIGHT_MOTOR.backward();
		
		// block the thread until a button is pressed
		LCD.drawString("Achteruit!", 0, 1);
		buttons.waitForAnyPress();
		
		// stop robot and display status on LCD 
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.stop();
		LCD.drawString("Gestopt!", 0, 2);
		
		// exit program after any button pressed
		buttons.waitForAnyPress();
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();

	}

}