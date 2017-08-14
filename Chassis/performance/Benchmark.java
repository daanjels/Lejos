package performance;

/**
 * Test a wheeled chassis.
 * Four testcases:
 * - Run a straight line
 * - Turn on the spot
 * - Run a square figure
 * - Run a Yin-Yang figure
 * Option to change settings:
 * - wheel diameter
 * - wheelbase
 * - drift
 * - speed
 * - acceleration
 * 
 * @author Argetype
 * @version 1.0
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.internal.ev3.EV3LCDManager;
import lejos.internal.ev3.EV3LCDManager.LCDLayer;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class Benchmark {
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static Keys buttons = BrickFinder.getDefault().getKeys();
	static double[] props = {4.24, 4.15, 0.996, 10.0, 60.0, 5.0};
	// set properties to start with, they can be changed at runtime
 	// - wheelradius				4.26
	// - wheel offset form center	4.14
	// - wheel drift				0.997
	// - linearspeed				10.0
	// - angular speed				60
	// - acceleration				3.0
	
	public static void main(String[] args) throws Exception {
		
		// hide outputstream from the LCD
		EV3LCDManager manager =EV3LCDManager.getLocalLCDManager();
		LCDLayer layer = manager.getLayer("STDOUT");
		layer.setVisible(false);
		
		// create a list of options to display in the menu
		String[] mainOptions = {"Straight line", "Turnaround", "Square CW/CCW", "Yin/Yang", "Settings"};
		// create a menu with the options, with a title above it: "Benchmark"
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "  * Benchmark *   ");
		// create an integer for the selected item in the menu
		int selection;
		
		// check the battery, if it is below 5.5 abort
		if (BrickFinder.getLocal().getPower().getVoltage() < 5.5) {
			Sound.beep();
			LCD.drawString("WARNING!", 0, 0);
			LCD.drawString("Battery is low...", 0, 1);
			LCD.drawString(String.valueOf(BrickFinder.getDefault().getPower().getVoltage()), 0, 2);
			LCD.drawString("Aborting testrun!", 0, 4);
			Delay.msDelay(4000);
			System.exit(0);
		}
		
		// display startup screen
		LCD.drawString("Benchmark test", 0, 0);
		LCD.drawString("to check accuracy", 0, 1);
		LCD.drawString("of EV3 robots.", 0, 2);
		LCD.drawString("by Arqetype", 0, 4);
		LCD.drawString("Version 1.0", 0, 5);
		LCD.drawString("Battery: " + String.valueOf(BrickFinder.getDefault().getPower().getVoltage()), 0, 7);
		buttons.waitForAnyPress();
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			LCD.clear();
			selection = mainMenu.select();		// display the menu
			if (selection == 0) { //Straight line
				line();
			} else if (selection == 1) { // Turnaround
				Delay.msDelay(200);
				turnaround();
			} else if (selection == 2) { // Square
				Delay.msDelay(200);
				square();
			} else if (selection == 3) { // Yin-Yang
				Delay.msDelay(200);
				curve();
			} else if (selection == 4) { // Settings
				Delay.msDelay(200);
				settings();
			} else if (selection == -1) { // Escape quits
				LCD.clear();
				LCD.drawString("Closing down", 0, 3);
				Delay.msDelay(1000);
				layer.close(); // close the outputstream
				System.exit(0);
				return;
			}
		}
	}

	private static void turnaround() {
		double turn = 180.0;
		LCD.clear();
		LCD.drawString("Press ENTER to", 0, 0);
		LCD.drawString("make 10 turns of ", 0, 1);
		LCD.drawString("180 degrees.", 0, 2);
		LCD.drawString("LEFT or RIGHT to", 0, 3);
		LCD.drawString("change direction", 0, 4);
		LCD.drawString("ESCAPE for menu", 0, 7);
		LCD.drawString("RIGHT", 8, 3, true);	// highlight the 'RIGHT' option
		System.out.println("Turnaround test _________________");	// print to the log
		Chassis car = buildCar();	// create a wheeledchassis object
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {	// check key presses
			if (buttons.getButtons() == Keys.ID_LEFT) {
				LCD.drawString("RIGHT", 8, 3, false);
				LCD.drawString("LEFT", 0, 3, true);	// highlight the 'LEFT' option
				System.out.println("Change to left turns.");
				Delay.msDelay(100);
				turn = -180.0;
				car.rotate(turn);
				car.waitComplete();
				Delay.msDelay(100);
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
				LCD.drawString("RIGHT", 8, 3, true);	// highlight the 'RIGHT' option
				LCD.drawString("LEFT", 0, 3, false);
				System.out.println("Change to right turns.");
				Delay.msDelay(100);
				turn = 180.0;
				car.rotate(turn);
				car.waitComplete();
				Delay.msDelay(100);
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
			} else if (buttons.getButtons() == Keys.ID_ENTER) {
				System.out.println("Perform 10 turns.");
				LCD.clear();
				LCD.drawString("Performing", 0, 0);
				LCD.drawString("10 turns", 0, 1);
				for (int i = 1; i < 10; i++) {
					LCD.drawInt(i, 2, 7, 3);
					car.rotate(turn);
					car.waitComplete();
					Delay.msDelay(100);
					System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				}
				LCD.clear();
				LCD.drawString("Press ENTER to", 0, 0);
				LCD.drawString("make 10 turns of ", 0, 1);
				LCD.drawString("180 degrees.", 0, 2);
				LCD.drawString("LEFT or RIGHT to", 0, 3);
				LCD.drawString("change direction", 0, 4);
				LCD.drawString("ESCAPE for menu", 0, 7);
				if (turn > 0) LCD.drawString("RIGHT", 8, 3, true);	// highlight the 'RIGHT' option
				else LCD.drawString("LEFT", 0, 3, true);
			}
			Delay.msDelay(200);	// wait before checking the next key pressed to avoid 'double presses'
		}
		Delay.msDelay(200);	// wait before checking the next key pressed to avoid 'double presses'
		return;
	}

	private static void line() {
		double turn = 180.0;
		int dist = 100;
		LCD.clear();
		LCD.drawString("Line test",0,0);
		LCD.drawString("Make your choice",0,1);
		LCD.drawString("> Clockwise", 0, 2);
		LCD.drawString("- Distance: " + dist, 0, 3);
		LCD.drawString("ENTER: start test", 0, 6);
		LCD.drawString("ESC: back to menu", 0, 7);
		System.out.println("Straight line test ______________");	// print to the log
		 
		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_RIGHT) {
				LCD.drawString("> Clockwise       ", 0, 2);
				turn = 180.0;
				Delay.msDelay(200);	// wait before checking the next key pressed to avoid 'double presses'
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
				LCD.drawString("< Counterclockwise", 0, 2);	// change arrow and text on the display
				turn = -180.0;
				Delay.msDelay(200);	// wait before checking the next key pressed to avoid 'double presses'
			} else if (buttons.getButtons() == Keys.ID_UP) {
				dist = dist + 10;	// increase distance to travel in 10 cm increments
				LCD.drawInt(dist, 4, 11, 3);
				Delay.msDelay(200);	// wait before checking the next key pressed to avoid 'double presses'
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				dist = dist - 10;	// decrease distance to travel in 10 cm increments
				if (dist < 10) dist = 10;	// bottom limit is 10
				LCD.drawInt(dist, 4, 11, 3);
				Delay.msDelay(200);	// wait before checking the next key pressed to avoid 'double presses'
			} else if (buttons.getButtons() == Keys.ID_ENTER) {
				if (turn == 180) {
					LCD.clear();
					LCD.drawString("Wall-E will run a ", 0, 0);
					LCD.drawString("straight line and ", 0, 1);
					LCD.drawString("turn 180 degrees ", 0, 2);
					LCD.drawString("in clockwise ", 0, 3);
					LCD.drawString("direction to test ", 0, 4);
					LCD.drawString("its accuracy.", 0, 5);
				} else {
					LCD.clear();
					LCD.drawString("Wall-E will run a ", 0, 0);
					LCD.drawString("straight line and ", 0, 1);
					LCD.drawString("turn 180 degrees ", 0, 2);
					LCD.drawString("in counterclock-", 0, 3);
					LCD.drawString("wise direction to ", 0, 4);
					LCD.drawString("test its accuracy.", 0, 5);
				}
				Chassis car = buildCar();	// create a wheeledchassis object
				System.out.println("Starting position");
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				car.travel(dist);
				car.waitComplete();
				Delay.msDelay(500);
				car.rotate(turn);
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				LCD.clear();
				LCD.drawString("Measure the", 0, 0);
				LCD.drawString("distance.", 0, 1);
				LCD.drawString("ENTER to continue...", 0, 3);
				while (buttons.getButtons() != Keys.ID_ENTER) {
					if (buttons.getButtons() == Keys.ID_RIGHT) {
						car.rotate(180);
						car.waitComplete();
						System.out.println("Extra right turn");
						System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
						Delay.msDelay(200);
					}if (buttons.getButtons() == Keys.ID_LEFT) {
						car.rotate(-180);
						car.waitComplete();
						System.out.println("Extra left turn");
						System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
						Delay.msDelay(200);
					} else 
					Delay.msDelay(200);
				}
				LCD.clear();
				LCD.drawString("Going back home...", 0, 2);
				car.travel(dist);
				car.waitComplete();
				Delay.msDelay(500);
				car.rotate(180);
				car.waitComplete();
				System.out.println("Back to starting position");
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				
				// TODO can we avoid this double message?
				LCD.clear();	// reprint the line test message
				LCD.drawString("Line test",0,0);
				LCD.drawString("Make your choice",0,1);
				if (turn == 180) LCD.drawString("> Clockwise", 0, 2);
				else LCD.drawString("< Counterclockwise", 0, 2);
				LCD.drawString("- Distance: " + dist, 0, 3);
				LCD.drawString("ENTER: start test", 0, 6);
				LCD.drawString("ESC: back to menu", 0, 7);
			}
		}
		Delay.msDelay(200);
		return;
	}

	private static void square() {
		double turn = 90.0;
		LCD.clear();
		LCD.drawString("Square test",0,0);
		LCD.drawString("Make your choice",0,1);
		LCD.drawString("> Clockwise", 0, 2);
		LCD.drawString("ENTER: start test", 0, 6);
		LCD.drawString("ESC: back to menu", 0, 7);
		System.out.println("Square test _____________________");	// print to the log
		
		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_RIGHT) {
				LCD.drawString("> Clockwise       ", 0, 2);
				turn = 90.0;
				Delay.msDelay(200);
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
				LCD.drawString("< Counterclockwise", 0, 2);
				turn = -90.0;
				Delay.msDelay(200);
			} else if (buttons.getButtons() == Keys.ID_DOWN) {	// option to change settings from within the square method...
				settings();
				Delay.msDelay(200);
				LCD.clear();
				LCD.drawString("Square test",0,0);
				LCD.drawString("Make your choice",0,1);
				if (turn == 90.0) LCD.drawString("> Clockwise", 0, 2);
				else LCD.drawString("< Counterclockwise", 0, 2);
				LCD.drawString("ENTER: start test", 0, 6);
				LCD.drawString("ESC: back to menu", 0, 7);
			} else if (buttons.getButtons() == Keys.ID_ENTER) {
				if (turn == 90.0) {
					LCD.clear();
					LCD.drawString("Wall-E will run a ", 0, 0);
					LCD.drawString("square in clock-", 0, 1);
					LCD.drawString("wise direction to", 0, 2);
					LCD.drawString("test its accuracy.", 0, 3);
				} else {
					LCD.clear();
					LCD.drawString("Wall-E will run a ", 0, 0);
					LCD.drawString("square in counter-", 0, 1);
					LCD.drawString("clockwise ", 0, 2);
					LCD.drawString("direction to test ", 0, 3);
					LCD.drawString("its accuracy.", 0, 4);
				}
				
				Chassis car = buildCar();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				LCD.clear();
				LCD.drawString("Running a square", 0, 1);
				Delay.msDelay(500);
				for (int i = 1; i < 5; i++) {
					LCD.clear(2);
					LCD.clear(3);
					LCD.drawString("Side " + i, 0, 2);
					car.travel(50.0);
					car.waitComplete();
					System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
					LCD.drawString("Side " + i + " done.", 0, 2);
					Delay.msDelay(500);
					LCD.drawString("Turning", 0, 3);
					car.rotate(turn);
					car.waitComplete();
					System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
					Delay.msDelay(500);
				}
				
				LCD.clear();
				LCD.drawString("Square test",0,0);
				LCD.drawString("Make your choice",0,1);
				if (turn == 90.0) LCD.drawString("> Clockwise", 0, 2);
				else LCD.drawString("< Counterclockwise", 0, 2);
				LCD.drawString("ENTER: start test", 0, 6);
				LCD.drawString("ESC: back to menu", 0, 7);
			}
		}
		Delay.msDelay(200);
		return;
	}
	
	private static void curve() {
		double turn = 1;	// with arcs a positive radius is clockwise
		int radius = 20;
		LCD.clear();
		LCD.drawString("Yin Yang test    ", 0, 0);
		LCD.drawString("Make your choice ", 0, 1);
		LCD.drawString("> Clockwise      ", 0, 2);
		LCD.drawString("- Radius: " + radius, 0, 3);
		LCD.drawString("ENTER: start test", 0, 6);
		LCD.drawString("ESC: back to menu", 0, 7);
		System.out.println("Yin - Yang test _________________");	// print to the log
		
		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_RIGHT) {
				LCD.drawString("> Clockwise       ", 0, 2);
				turn = 1;
				Delay.msDelay(250);
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
				LCD.drawString("< Counterclockwise", 0, 2);
				turn = -1;
				Delay.msDelay(250);
			} else if (buttons.getButtons() == Keys.ID_UP) {
				radius = radius + 5;
				if (radius > 150) radius = 150;
				LCD.drawInt(radius, 3, 10, 3);
				Delay.msDelay(250);
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				radius = radius - 5;
				if (radius < 10) radius = 10;
				LCD.drawInt(radius, 3, 10, 3);
				Delay.msDelay(250);
			} else if (buttons.getButtons() == Keys.ID_ENTER) {
				Chassis car = buildCar();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				if (turn > 0) {
					LCD.clear();
					LCD.drawString("Wall-E will run a ", 0, 0);
					LCD.drawString("Yin figure to test", 0, 1);
					LCD.drawString("its accuracy.", 0, 2);
					LCD.drawString("", 0, 3);
					LCD.drawString("", 0, 4);
				} else {
					LCD.clear();
					LCD.drawString("Wall-E will run a ", 0, 0);
					LCD.drawString("Yang figure to", 0, 1);
					LCD.drawString("test its accuracy.", 0, 2);
					LCD.drawString("", 0, 3);
					LCD.drawString("", 0, 4);
				}
				car.setAngularSpeed(props[4]/4);	// set angular speed to a quarter of the rotation speed
				// TODO figure out a formula to calculate the optimal angular speed for an arc in relation to its radius
				Delay.msDelay(1000);
				car.arc(turn * radius, 180);
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				car.arc(-turn * radius, 180);
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				car.setAngularSpeed(props[4]/8);	// set angular speed to an eighth for the larger arc
				car.arc(-turn*2*radius, 180);
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				car.setAngularSpeed(props[4]);	// reset angular speed to rotation speed
				car.rotate(180);
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(2000);
				
				LCD.clear();
				LCD.drawString("Yin Yang test",0,0);
				LCD.drawString("Make your choice",0,1);
				if (turn > 0) LCD.drawString("> Clockwise", 0, 2);
				else LCD.drawString("< Counterclockwise", 0, 2);
				LCD.drawString("- Radius: " + radius, 0, 3);
				LCD.drawString("ENTER: start test", 0, 6);
				LCD.drawString("ESC: back to menu", 0, 7);
			}
		}
		Delay.msDelay(200);
		return;
	}

	private static void settings() {
		int option = 0;
		// props: wheelradius / wheel offset form center / drift / linearspeed / angular speed / acceleration 
		int[] sets = {(int) (props[0] * 100), (int) (props[1] * 100), (int) (props[2] * 1000), (int) (props[3] * 10), (int) (props[4] * 1), (int) (props[5] * 100)};
		int[] increments =	{1,		1,		1,		5,		5,		5,};
		int[] min = 		{100,	300,	950,	10,		10,		50};
		int[] max =			{1000,	1000,	1050,	200,	360,	700};
		
		LCD.clear();
		LCD.drawString("   - Settings -   ", 0, 0);
		LCD.drawString("> Wheel:          ", 0, 1);
		LCD.drawString("  Base:           ", 0, 2);
		LCD.drawString("  Drift:          ", 0, 3);
		LCD.drawString("  Linear:         ", 0, 4);
		LCD.drawString("  Angular:        ", 0, 5);
		LCD.drawString("  Accel.:         ", 0, 6);
		LCD.drawString("      ENTER: apply", 0, 7);
		
		for(int i = 0; i < 6; i++) {
			LCD.drawInt(sets[i], 4, 10, i+1);
		}
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_UP) {
				LCD.drawString(" ", 0, option+1);
				option = option -1;
				if (option < 0) option = 5;
				LCD.drawString(">", 0, option+1);
				Delay.msDelay(200);
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				LCD.drawString(" ", 0, option+1);
				option = option +1;
				if (option > 5) option = 0;
				LCD.drawString(">", 0, option+1);
				Delay.msDelay(200);
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
				sets[option] = sets[option] - increments[option];
				if (sets[option] < min[option]) sets[option] = min[option];
				LCD.drawInt(sets[option], 4, 10, option+1);
				Delay.msDelay(200);
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
				sets[option] = sets[option] + increments[option];
				if (sets[option] > max[option]) sets[option] = max[option];
				LCD.drawInt(sets[option], 4, 10, option+1);
				Delay.msDelay(200);
			} else if (buttons.getButtons() == Keys.ID_ENTER) {
				LCD.clear();
				LCD.drawString("Applying settings", 0, 0);
				Delay.msDelay(100);
				LCD.drawString("Wheelradius: " + sets[0], 0, 1);
				props[0] = (double)sets[0] / 100.0;
				Delay.msDelay(100);
				LCD.drawString("Wheelbase:   " + sets[1], 0, 2);
				props[1] = (double)sets[1] / 100.0;
				Delay.msDelay(100);
				LCD.drawString("Drift:       " + sets[2], 0, 3);
				props[2] = (double)sets[2] / 1000.0;
				Delay.msDelay(100);
				LCD.drawString("Linear:      " + sets[3], 0, 4);
				props[3] = (double)sets[3] / 10.0;
				Delay.msDelay(100);
				LCD.drawString("Angular      " + sets[4], 0, 5);
				props[4] = (double)sets[4] / 1.0;
				Delay.msDelay(100);
				LCD.drawString("Acceleration:" + sets[5], 0, 6);
				props[5] = (double)sets[5] / 100.0;
				Delay.msDelay(500);
				LCD.drawString("Done!", 0, 7);
				Delay.msDelay(1000);
				return;
			}
		}
		Delay.msDelay(250);
		return;
	}

	private static Chassis buildCar() {
		double wiel = props[0];
		double afstand = props[1];
		double afwijking = props[2];
		double snelheid = props[3];
		double boogsnel = props[4];
		double versnel = props[5];
		double wielLinks = wiel * 2 / (afwijking + 1);
		double wielRechts = wiel * 2 / (1/afwijking + 1);
		System.out.println("Battery: " + BrickFinder.getDefault().getPower().getVoltage());
		for (int i = 0; i < props.length; i++) {
			System.out.println(props[i]);
		}
		System.out.println("Wheel left: " + wielLinks);
		System.out.println("Wheel right: " + wielRechts);
		Wheel wheel1 = WheeledChassis.modelWheel(RIGHT_MOTOR, wielRechts).offset(afstand);
		Wheel wheel2 = WheeledChassis.modelWheel(LEFT_MOTOR, wielLinks).offset(-afstand);
		Chassis car = new WheeledChassis(new Wheel[] {wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
		car.setLinearSpeed(snelheid);
		car.setAngularSpeed(boogsnel);
		car.setAcceleration(versnel, versnel*5);
		return car;
	}
}