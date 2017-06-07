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
 * drive a robot forward for 10 seconds
 * display the time on screen
 */

public class MotorSpeed {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void main(String[] args) {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		LCD.drawString("Press a key to start!", 0, 3);
		buttons.waitForAnyPress();
		
		Stopwatch watch = new Stopwatch();
		
		LEFT_MOTOR.forward();
		RIGHT_MOTOR.forward();
		
		LCD.clear();
		LCD.drawString("Run motor for 10 seconds", 0, 0);
		watch.reset();
		
		while (watch.elapsed() < 10000) {
			Thread.yield();
			LCD.drawString("" + watch.elapsed(), 0, 1);
		}
		
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.stop();
		
		Delay.msDelay(5000);
		
		LCD.clear();
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();
	}
}
