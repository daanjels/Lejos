package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.nxt.comm.LCPBTResponder;

public class MotorMoves {

	public static void main(String[] args) {
		LCPBTResponder lcpThread = new LCPBTResponder();
		//	bluetooth address: 00-16-53-12-57-E2
		lcpThread.setDaemon(true);
		lcpThread.start();
		
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
