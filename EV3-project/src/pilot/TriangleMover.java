package pilot;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;

public class TriangleMover {

	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);	
	
	public static void main(String[] args) throws Exception {
		LCD.drawString("   Plotting out   ", 0, 2);
		LCD.drawString("    a triangle    ", 0, 3);
		LCD.drawString("taking right turns", 0, 4);
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.22).offset(9.2);
		Wheel wheel2 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.22).offset(-9.2).invert(true);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(20); // degrees per second
		pilot.setLinearAcceleration(5);
		pilot.setLinearSpeed(10); // centimeters per second
		
		// loop 3 times to trace out a square
		for (int sides = 0; sides < 3; sides++) {
			pilot.travel(50);	// travel 50 centimeter
			pilot.rotate(120);	// rotate 120 degrees
		}

		LCD.clear();
		LCD.drawString("..................", 0, 2);
		LCD.drawString(".    Finished    .", 0, 3);
		LCD.drawString("..................", 0, 4);
		Delay.msDelay(3000);
		RIGHT_MOTOR.close();
		LEFT_MOTOR.close();
	}	
}