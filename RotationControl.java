package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class RotationControl {

	public static void main(String[] args) {
		LCD.drawString("Rotation Control", 0, 0);
		LCD.drawString("Press any button",0,4);
		LCD.drawString("to start", 0, 5);
		Button.waitForAnyPress();
		LCD.clear();
		LCD.drawString("Rotation Control", 0, 0);
		Motor.A.rotate(360*4);
		LCD.drawString("Tacho: "+Motor.A.getTachoCount(), 1, 2);
		Motor.A.rotateTo(0);
		LCD.drawString("Tacho: "+Motor.A.getTachoCount(), 1, 3);
		LCD.drawString("Press any button",0,6);
		LCD.drawString("to quit", 0, 7);
		Button.waitForAnyPress();
	}
}
// the motor stops within 1 degree of the specified angle!
