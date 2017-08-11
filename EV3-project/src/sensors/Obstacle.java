package sensors;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * Program to avoid obstacles
 * 
 * @author Arqetype
 *
 */

public class Obstacle {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);

	public static void main(String[] args) {
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.22).offset(-9.2);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.22).offset(9.2);
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot wallE = new MovePilot(chassis);
		
		wallE.setAngularSpeed(45);
		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		SensorMode toucher = touch.getTouchMode();			// get an instance of this sensor in measurement mode

		LCD.drawString("Avoiding obstacles", 0, 1);
		LCD.drawString("Press a button", 0, 3);
		LCD.drawString("to start", 0, 4);
		Button.waitForAnyPress();
		
		while (!Button.ESCAPE.isDown()) {
			wallE.forward();
			if (toucher.sampleSize() == 0) {
				wallE.stop();
				wallE.travel(-10);
				wallE.rotate(90);
			}
		}
		touch.close();
	}
}
