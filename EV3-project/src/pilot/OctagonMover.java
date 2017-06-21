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

public class OctagonMover {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
//		LCD.drawString("0123456789abcdefgh")
		LCD.drawString("  Plotting out   ", 0, 0);
		LCD.drawString("   an octagon    ", 0, 1);
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.22).offset(-9.2);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.22).offset(9.2);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(20); // degrees per second
		pilot.setLinearAcceleration(5);
		pilot.setLinearSpeed(10); // centimeters per second
		
		// loop 8 times to trace out a square
		for (int sides = 1; sides < 9; sides++) {
		// travel 50 centimeter
		LCD.drawString("Side " + sides, 0, sides-1);
		pilot.travel(50);
		// rotate 45 degrees
		LCD.drawString(", turn " + sides, 7, sides-1);
		pilot.rotate(45);
		}
		// press the ESCAPE button to stop moving
		while (pilot.isMoving()) {
		if (buttons.getButtons() == Keys.ID_ENTER)
		pilot.stop();
		}
		// block the thread until a button is pressed
		LCD.clear();
		LCD.drawString("Plotting finished", 0, 4);
		
		buttons.waitForAnyPress();
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();
	}	
}