package pilot;

/**
 * Wall-E displays a choicelist on his screen
 * It shows some geometric figures 
 * He will drive around creating these figures
 * 
 * @author Argetype
 */

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class FigureBot {

	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);

	public static void main(String[] args) throws Exception {		
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

		// define a string array to hold the options
		String[] figures = {"Triangle", "Square", "Hexagon", "Octagon", "Exit"};

		// create and display a menu with the options, with a title above it: "FirureBot"
		TextMenu main = new TextMenu(figures, 1, "FigureBot");

		// define variable for the selection
		int selection;
		String figure = "square";
		int sides = 4;
		double angle = 90;
				
		//	loop indefinitely until the user pushes a button
		for(;;)
		{
			LCD.clear();
			//	see what was selected
			selection = main.select();
			
			//	depending on the selection perform an action
			if (selection == -1 || selection == 4)
			{
			//if 'Exit' or the back-button was selected, display 'Finished' and exit the program
				LCD.clear();
				LCD.drawString("Finished",3,4);
				Delay.msDelay(1000);
				LCD.refresh();	
				LEFT_MOTOR.close();
				RIGHT_MOTOR.close();
				System.exit(0);
				return;
			}
			if (selection == 0) {			//	Triangle was selected
				figure = "triangle";
				sides = 3;
				angle = 120;
			} else if (selection == 1) {	//	Square was selected
				figure = "square";
				sides = 4;
				angle = 90;
			} else if (selection == 2) {	//	Hexagon was selected
				figure = "hexagon";
				sides = 6;
				angle = 60;
			} else if (selection == 3) {	//	Octagon was selected
				figure = "octagon";
				sides = 8;
				angle = 45;
			}
			LCD.clear();
			LCD.drawString("Plotting out a", 0, 3);
			LCD.drawString(figure + ".", 0, 4);
			for (int i = 0; i < sides; i++) { // trace out the chosen figure
				pilot.travel(50); // go forward 50 cm
				pilot.rotate(angle); // rotate angle degrees
			}

		}
	}
}
