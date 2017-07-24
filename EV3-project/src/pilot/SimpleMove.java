package pilot;

/**
 * This program is good for testing new robots
 * The robot should move ahead for 50 cm and then make a right turn
 * Adjusting the settin g for wheel diameter and wheel base will get the right values for your robot
 * 
 * @author Argetype
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;

public class SimpleMove {

	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	
	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		
		LCD.drawString("Druk op een knop!", 0, 0);
		LCD.drawString("Dan gaat wall-E 50", 0, 1);
		LCD.drawString("centimeter vooruit", 0, 2);
		LCD.drawString("en draait een", 0, 3);
		LCD.drawString("kwartslag naar", 0, 4);
		LCD.drawString("rechts.", 0, 5);
		
		buttons.waitForAnyPress();
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.24).offset(9.2);
		Wheel wheel2 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.24).offset(-9.2).invert(true);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(20);		// degrees per second
		pilot.setLinearAcceleration(5);
		pilot.setLinearSpeed(10);		// centimeters per second

		pilot.travel(50);		// travel 50 centimeter
		Delay.msDelay(1000);
		pilot.rotate(90.0);		// rotate 90 degrees

		// press the ESCAPE button to stop moving
		while (pilot.isMoving()) {
		if (buttons.getButtons() == Keys.ID_ESCAPE)
		pilot.stop();
		}
		// block the thread until a button is pressed
		LCD.clear();
		LCD.drawString(".    Finished    .", 0, 4);
		Delay.msDelay(3000);
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();
	}
}
