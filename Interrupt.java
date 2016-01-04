package be.wimdaniels;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Button;
import lejos.util.Delay;

public class Interrupt {

	public static void main(String[] args) {
		LCD.drawString("Interrupt",	0, 0);
		Button.waitForAnyPress();
		while(Button.readButtons()>0);
		LCD.clear();
		Motor.A.rotateTo(1440,true);
		while(Motor.A.isMoving()) {
			Delay.msDelay(200);
			LCD.drawString("Tacho: "+Motor.A.getTachoCount(), 1, 2);
			if(Button.readButtons()>0) Motor.A.stop();
		}
		LCD.drawInt(Motor.A.getTachoCount(),0,1);
		Button.waitForAnyPress();
	}

}
