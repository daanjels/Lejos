package drawing;

/**
 * Two thousand nine teen.
 * A Writ3r bot that can draw on a surface
 * It will create the figure 2 0 1 9
 * As a wish to all good people out there
 * 
 * @author Argetype
 * @version 1.0
 */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.internal.ev3.EV3LCDManager;
import lejos.internal.ev3.EV3LCDManager.LCDLayer;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class Ttnt {
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3MediumRegulatedMotor ARM_MOTOR = new EV3MediumRegulatedMotor(MotorPort.C);
	static Keys buttons = BrickFinder.getDefault().getKeys();
	static double[] props = {4.24, 5.65, 1.000, 10.0, 50.0, 3.0};
	// set properties to start with, they can be changed at runtime
 	// - wheelradius				4.24
	// - wheel offset form center	5.65
	// - wheel drift				1.000
	// - linearspeed				10.0
	// - angular speed				50
	// - acceleration				3.0
	private static Songs mt;
	static boolean muse = false;
	
	public static void main(String[] args) throws Exception {
		
		// hide outputstream from the LCD
		EV3LCDManager manager =EV3LCDManager.getLocalLCDManager();
		LCDLayer layer = manager.getLayer("STDOUT");
		layer.setVisible(false);
		
		// create a list of options to display in the menu
		String[] mainOptions = {"2019", "Settings", "Music", "Quit"};
		// create a menu with the options, with a title above it: "2 0 1 9"
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "  *  2 0 1 9  *  ");
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
		LCD.drawString("Drawing figures", 0, 0);
		LCD.drawString("In this case", 0, 1);
		LCD.drawString("2019", 0, 2);
		LCD.drawString("by Arqetype", 0, 4);
		LCD.drawString("Version 1.0", 0, 5);
		LCD.drawString("Battery: " + String.valueOf(BrickFinder.getDefault().getPower().getVoltage()), 0, 7);
		buttons.waitForAnyPress();
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			LCD.clear();
			selection = mainMenu.select();		// display the menu
			if (selection == 0) { // Draw 2019
				draw2019();
			} else if (selection == 1) { // Change settings
				Delay.msDelay(200);
				settings();
			} else if (selection == 2) { // Music on or off
				Delay.msDelay(200);
				toggleMusic();
			} else if (selection == 3) { // Quit
				LCD.clear();
				LCD.drawString("Closing down", 0, 3);
				Delay.msDelay(1000);
				layer.close(); // close the outputstream
				System.exit(0);
				return;
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

	private static void toggleMusic() {
		LCD.clear();
		if (muse == false) {
			muse = true;
			LCD.drawString("Music is ON", 0 ,0);
		}
		else {
			muse = false;
			LCD.drawString("Music is OFF", 0 ,0);
		}
		Delay.msDelay(1000);
		return;
	}

	private static void draw2019() {
		LCD.clear();
		LCD.drawString("Position your",0,0);
		LCD.drawString("robot on a large",0,1);
		LCD.drawString("piece of paper", 0, 2);
		LCD.drawString("Press ENTER", 0, 3);
		LCD.drawString("to start", 0, 6);
		LCD.drawString("ESC: back to menu", 0, 7);
		System.out.println("Show the menu ______________");	// print to the log
		Delay.msDelay(200);
		mt = new Songs();
		 
		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			if (buttons.getButtons() == Keys.ID_ENTER) {
				Chassis car = buildCar();	// create a wheeledchassis object
				System.out.println("Go to starting position");
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				LCD.clear();
				LCD.drawString("Coming in",0,0);
				car.travel(20); // enter the page
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Start position",0,0);
				car.rotate(-90);
				car.waitComplete();
				Delay.msDelay(1000);
				lowerArm(); // put arm down
				if (muse == true) mt.start(); // start playing the music
				LCD.clear();
				LCD.drawString("Upper arc",0,0);
//				car.arc(10, 270); // two upper arc / total width = 20
				car.arc(6, 270); // two upper arc / total width = 12
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Lower arc",0,0);
//				car.arc(-10, 90); // two lower arc / total height = 30
				car.arc(-6, 90); // two lower arc / total height = 18
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Rotate -90",0,0);
				car.rotate(-90);
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Travel 20",0,0);
//				car.travel(20); // two baseline
				car.travel(12); // two baseline
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				liftArm(); // lift arm up
				Delay.msDelay(1000);
				LCD.clear();
				LCD.drawString("Travel 15",0,0);
//				car.travel(15);
				car.travel(10); // 4 + 6
				car.waitComplete();
				Delay.msDelay(500);
				LCD.clear();
				LCD.drawString("Lower the pen",0,0);
				lowerArm(); // put arm down
				Delay.msDelay(1000);

//				Draw the zero as a circle 
				LCD.clear();
				LCD.drawString("Drawing the zero", 0, 0);
//				car.arc(-10, 360);
				car.arc(-7, 360);
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				LCD.clear();
				LCD.drawString("Lift the pen",0,0);
				liftArm(); // lift arm up
				Delay.msDelay(1000);
				LCD.clear();
				LCD.drawString("Travel 15",0,0);
//				car.travel(15);
				car.travel(10);
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Rotate -90",0,0);
				car.rotate(-90);
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Lower the pen",0,0);
				lowerArm(); // put arm down
				Delay.msDelay(1000);
				LCD.clear();
				LCD.drawString("Travel 30",0,0);
//				car.travel(30); // line of 1
				car.travel(18); // line of 1
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(500);
				LCD.clear();
				LCD.drawString("Lift the pen",0,0);
				liftArm(); // lift arm up
				Delay.msDelay(1000);
				LCD.clear();
				LCD.drawString("Rotate -90",0,0);
				car.rotate(90);
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Travel 15",0,0);
//				car.travel(15);
				car.travel(10); // 6 + 4
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Set start 9",0,0);
//				car.arc(10, 100); // that's a little bit extra
				car.arc(6, 95); // that's a little bit extra
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Lower the pen",0,0);
				lowerArm(); // put arm down
				Delay.msDelay(1000);
				LCD.clear();
				LCD.drawString("Nine circle",0,0);
//				car.arc(10, 360);
				car.arc(6, 360);
				car.waitComplete();
				LCD.clear();
				LCD.drawString("Nine arc bottom",0,0);
//				car.arc(20, 90);
				car.arc(13, 90); // that's a little bit extra
				car.waitComplete();
				System.out.println(car.getPoseProvider().getPose());	// print calculated position to the log
				Delay.msDelay(1000);
				LCD.clear();
				LCD.drawString("Lift the pen",0,0);
				liftArm(); // lift arm up
				car.rotate(-90);
				car.waitComplete();
				car.travel(30);
				car.waitComplete();
				Delay.msDelay(1000);
				return;
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
	
	private static void liftArm() {
		ARM_MOTOR.rotate(-450);
	}
	private static void lowerArm() {
		ARM_MOTOR.rotate(450);
	}
}