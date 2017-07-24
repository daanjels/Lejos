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

public class HexagonMover {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.22).offset(-9.2).invert(true);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.22).offset(9.2);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		pilot.setAngularSpeed(20); // degrees per second
		pilot.setLinearAcceleration(5);
		pilot.setLinearSpeed(10); // centimeters per second

		int direction = 60;
		String message = "Going clockwise";
		
//		LCD.drawString("0123456789abcdefgh")
		LCD.drawString("The HexagonBot ", 0, 0);
		LCD.drawString("rides again !!", 0, 1);
		LCD.drawString("Press RIGHT or ", 0, 2);
		LCD.drawString("LEFT to choose a", 0, 3);
		LCD.drawString("turning direction", 0, 4);

		if (buttons.waitForAnyPress() == Keys.ID_LEFT) {
			direction = -60;
			message = "Counterclockwise";
		}
		LCD.drawString(message, 0, 6);

		for (int sides = 0; sides < 6; sides++) // loop 6 times to trace out a square
		{
			pilot.travel(50); // travel 50 centimeter
			pilot.rotate(direction); // rotate 90 degrees
		}

		// press the ESCAPE button to stop moving
		while (pilot.isMoving()) {
		if (buttons.getButtons() == Keys.ID_ESCAPE)
		pilot.stop();
		}

		// block the thread until a button is pressed
		LCD.clear();
		LCD.drawString("Finished!", 0, 4);
		Delay.msDelay(2000);
		LEFT_MOTOR.close();
		RIGHT_MOTOR.close();
	}	
}