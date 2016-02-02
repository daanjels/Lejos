package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.util.Delay;
import lejos.util.TextMenu;

public class Spirograph {

	public static void main(String[] args) {
		//	define a string array to hold the options
	    String[] figures = {"Star", "Alien", "Triskelion", "Bakugan", "Exit"};
	    //	create and display a menu with the options, with a title above it: "Spyrograph"
	    TextMenu main = new TextMenu(figures, 1, "Spyrograph");
	    //	define variables for the selection and the power level
		int selection, power;
		
		//	loop indefinitely untill the user pushes a button
		for(;;)
		{
			//	clear the screen
			LCD.clear();
			//	see what was selected
			selection = main.select();
			
			//	depending on the selection perform an action
			if (selection == -1 || selection == 4)
			{
			//if 'Exit' was selected, display 'Finished' and exit the program
				LCD.clear();
				LCD.drawString("Finished",3,4);
				Delay.msDelay(1000);
				LCD.refresh();	
				return;
			}

			if (selection == 0) {
				//	Star was selected
				LCD.clear();
				LCD.drawString("Spyrograph", 3, 1);
				LCD.drawString("Star", 1, 4);
				power = 200;
				Motor.C.setSpeed(power);
				Motor.A.setSpeed(power*3);
				Motor.C.forward();
				Motor.A.forward();
				while (Motor.C.getTachoCount() < 3600);
				Motor.C.stop(true);
				Motor.A.stop(true);
				//	reset the Tacho count so other figures can still be drawn
				Motor.C.resetTachoCount();
            } else if (selection == 1) {
            	//	Alien was selected
            	LCD.clear();
            	LCD.drawString("Spyrograph", 3, 1);
            	LCD.drawString("Alien", 1, 4);
				power = 300;
				Motor.C.setSpeed(power);
				Motor.A.setSpeed(power/2);
				Motor.C.forward();
				Motor.A.forward();
				while (Motor.C.getTachoCount() < 3600);
				Motor.C.stop(true);
				Motor.A.stop(true);
				//	reset the Tacho count so other figures can still be drawn
				Motor.C.resetTachoCount();
            } else if (selection == 2) {
            	//	Triskelion was selected
				LCD.clear();
				LCD.drawString("Spyrograph", 3, 1);
				LCD.drawString("Triskelion", 1, 4);
				//	uses a separate methode to draw the treskelion
				Treskelion();
            } else if (selection == 3) {
            	//	Bakugan was selected
				LCD.clear();
				LCD.drawString("Spyrograph", 3, 1);
				LCD.drawString("Bakugan", 1, 4);
				power = 300;
				Motor.C.setSpeed(power);
				Motor.A.setSpeed(power*2);
				Motor.C.forward();
				Motor.A.forward();
				while (Motor.C.getTachoCount() < 3600);
				Motor.C.stop(true);
				Motor.A.stop(true);
				//	reset the Tacho count so other figures can still be drawn
				Motor.C.resetTachoCount();
            }
        }		

	}
	
	public static void Treskelion() {
		Motor.B.setSpeed(100);
		Motor.B.forward();
		while (Motor.B.getTachoCount() < 3600);
		Motor.B.stop(true);
		//	reset the Tacho count so other figures can still be drawn
		Motor.B.resetTachoCount();
	}
}