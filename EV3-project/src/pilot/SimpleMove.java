package pilot;

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

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		
		LCD.drawString("Druk op een knop!", 0, 0);
		LCD.drawString("Dan gaat wall-E 50", 0, 1);
		LCD.drawString("centimeter vooruit", 0, 2);
		LCD.drawString("en draait een", 0, 3);
		LCD.drawString("kwartslag naar", 0, 4);
		LCD.drawString("links.", 0, 5);
		
		buttons.waitForAnyPress();
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.24).offset(-9.2);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.24).offset(9.2);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(20); // degrees per second
		pilot.setLinearAcceleration(5);
		pilot.setLinearSpeed(10); // centimeters per second

		// travel 50 centimeter
		pilot.travel(50);
		// rotate 90 degrees
		Delay.msDelay(1000);
		pilot.rotate(90.0);
		// press the ESCAPE button to stop moving
		while (pilot.isMoving()) {
		if (buttons.getButtons() == Keys.ID_ESCAPE)
		pilot.stop();
		}
		// block the thread until a button is pressed
		LCD.clear();
		LCD.drawString("     Finished    .", 0, 4);
		buttons.waitForAnyPress();
	}
}
