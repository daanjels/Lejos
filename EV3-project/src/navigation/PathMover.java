package navigation;

/**
 * A simple class that move the wall-E form one waypoint to another
 * 
 * @author Argetype
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.utility.TextMenu;

public class PathMover {

	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);

	public static void main(String[] args) throws InterruptedException {
		// setup the wheel diameter of left (and right) motor in centimeters,
		// i.e. 2.8 cm the offset number is the distance between the center 
		// of wheel to the center of robot, i.e. half of track width
		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 2.8).offset(-7);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 2.8).offset(7);
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot ev3robot = new MovePilot(chassis);
		Navigator navbot = new Navigator(ev3robot);

		// menu options for displaying navigator controlling
		String[] menuItems = new String[2];
		menuItems[0] = "Taak 1: Zigzag";
//		menuItems[1] = "Taak 2: Heen en terug";
		// display menu
		TextMenu menu = new TextMenu(menuItems);
		menu.setTitle("NavigaTor");
		// run routine based on menu choice
		switch (menu.select()) {
		case 0:
			navigate(navbot);
			break;
		}
	}

	// navigate()
	// demo navigating using way points
	private static void navigate(Navigator nav) {
		// get EV3 brick
		EV3 wallE = (EV3) BrickFinder.getLocal();
		// instantiated LCD class for displaying and Keys class for buttons
		Keys buttons = wallE.getKeys();
		TextLCD lcddisplay = wallE.getTextLCD();
		// set up way points
		Waypoint wp1 = new Waypoint(15, 15);
		Waypoint wp2 = new Waypoint(-15, 45);
		Waypoint wp3 = new Waypoint(15, 75);
		// clear menu
		lcddisplay.clear();
		// display current location
		lcddisplay.drawString("Op 0, 0", 0, 0);
		lcddisplay.drawString("Druk ENTER om", 0, 2);
		lcddisplay.drawString("verder te gaan", 0, 3);
		// wait until ENTER key is pressed
		while (buttons.waitForAnyPress() != Keys.ID_ENTER)
			Thread.yield();
		// navigate to way point one, display new location
		lcddisplay.clear();
		nav.goTo(wp1);
		lcddisplay.drawString("Op 15, 15", 0, 0);
		lcddisplay.drawString("Druk ENTER om", 0, 2);
		lcddisplay.drawString("verder te gaan", 0, 3);
		// wait until ENTER key is pressed
		while (buttons.waitForAnyPress() != Keys.ID_ENTER)
			Thread.yield();
		// navigate to way point two, display new location
		lcddisplay.clear();
		nav.goTo(wp2);
		lcddisplay.drawString("Op -15, 30", 0, 0);
		lcddisplay.drawString("Druk ENTER om", 0, 2);
		lcddisplay.drawString("verder te gaan", 0, 3);
		// wait until ENTER key is pressed
		while (buttons.waitForAnyPress() != Keys.ID_ENTER)
			Thread.yield();
		// navigate to way point 3, display new location
		lcddisplay.clear();
		nav.goTo(wp3);
		lcddisplay.drawString("Op 15, 75", 0, 0);
		lcddisplay.drawString("Druk ENTER om", 0, 2);
		lcddisplay.drawString("te stoppen", 0, 3);
		// wait until ENTER key is pressed
		while (buttons.waitForAnyPress() != Keys.ID_ENTER)
			Thread.yield();
	}
}
