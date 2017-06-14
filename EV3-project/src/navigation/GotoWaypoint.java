package navigation;

/**
 * A simple class that moves wall-E to a waypoint
 * Extended with a path for Wall-E to follow
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
import lejos.robotics.pathfinding.Path;
import lejos.utility.Delay;

public class GotoWaypoint {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void main(String[] args) throws Exception {
		EV3 wallE = (EV3) BrickFinder.getLocal();
		Keys buttons = wallE.getKeys();
		buttons.waitForAnyPress();
		
		// setup the wheel diameter of left (and right) motor in centimeters, i.e. 2.8 cm
		// the offset number is the distance between the center of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 2.8).offset(-7);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 2.8).offset(7);
		
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		Navigator navbot = new Navigator(pilot);

		// define a new waypoint as destination
		Waypoint wp = new Waypoint (50, 50);
		// robot moves to the destination waypoint
//		LCD.drawString("0123456789abcdefgh")
		LCD.drawString("Ga naar het eerste", 0, 0);
		LCD.drawString("punt", 0, 1);
		navbot.goTo(wp);
		Delay.msDelay(1000);
		
		Path pad = new Path();
		pad.add(new Waypoint (75, 75));
		pad.add(new Waypoint (25, 75));
		pad.add(new Waypoint (75, 25));
		pad.add(new Waypoint (75, 75));
		navbot.setPath(pad);
		LCD.drawString("Volg het pad!", 0, 3);
		navbot.followPath(pad);

		// block the thread until a button is pressed
		LCD.clear();
//		LCD.drawString("0123456789abcdefgh")
		LCD.drawString("Eindpunt bereikt!", 0, 0);
		buttons.waitForAnyPress();
		
		// something goes wrong, the EV3 stalls after execution...
	}
	
}
