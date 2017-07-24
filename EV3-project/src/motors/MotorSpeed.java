package motors;

import lejos.hardware.BrickFinder; 
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor; 
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;

/**
 * @author Arqetype
 * The right motor should be connected to port B
 * The left motor should be connected to port C
 * drive a robot forward for 10 seconds
 * display the time on screen
 */

public class MotorSpeed {

	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	
	public static void main(String[] args) {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
//		LCD.drawString("0123456789abcdefgh", 0, 0);
		LCD.drawString("Druk op een knop, ", 0, 3);
		LCD.drawString("Dan rijdt EV3 10  ", 0, 4);
		LCD.drawString("seconden vooruit! ", 0, 5);
		buttons.waitForAnyPress();
		
		Stopwatch watch = new Stopwatch();
		
		RIGHT_MOTOR.forward();
		LEFT_MOTOR.forward();
		
		LCD.clear();
		LCD.drawString("Run motor for 10 seconds", 0, 0);
		watch.reset();
		
		while (watch.elapsed() < 10000) {
			Thread.yield();
			LCD.drawString("" + watch.elapsed(), 0, 1);
		}
		
		RIGHT_MOTOR.stop();
		LEFT_MOTOR.stop();
		
		Delay.msDelay(5000);
		
		LCD.clear();
		RIGHT_MOTOR.close();
		LEFT_MOTOR.close();
	}
}
