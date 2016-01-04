package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.nxt.Button;
import lejos.util.Delay;

public class TachoMeter {
	public static void main(String[] args) {
		LCD.drawString("Tachometer", 3, 0);
		Motor.A.setSpeed(720);
		Motor.A.forward();
		Delay.msDelay(2000);
		LCD.drawString("2 sec: "+Motor.A.getTachoCount(),0,1);
		Motor.A.stop();
		LCD.drawString("After stop: "+Motor.A.getTachoCount(),0,2);
		Motor.A.backward();
		while (Motor.A.getTachoCount() > 0);
		LCD.drawString("On zero: "+Motor.A.getTachoCount(),0,3);
		Motor.A.stop();
		LCD.drawString("After stop: "+Motor.A.getTachoCount(),0,4);
		LCD.drawString("Press any button",0,6);
		LCD.drawString("to quit", 0, 7);
		Button.waitForAnyPress();
	}
}
