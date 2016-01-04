package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.nxt.Button;

public class MotorMoves {

	public static void main(String[] args) {
		LCD.drawString("Program 1", 0, 0);
		Button.waitForAnyPress();
		Motor.A.forward();
		LCD.drawString("FORWARD  ", 0, 1);
		Button.waitForAnyPress();
		Motor.A.backward();
		LCD.drawString("BACKWARD", 0, 2);
		Button.waitForAnyPress();
		Motor.A.forward();
		LCD.drawString("FORWARD", 0, 2);
		Button.waitForAnyPress();
		Motor.A.stop();
	}
}
