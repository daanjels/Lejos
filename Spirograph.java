package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.util.Delay;
import lejos.util.TextMenu;

public class Spirograph {

	public static void main(String[] args) {
		//	define a string array to hold the options
	    String[] figures = {"Star", "Alien", "Bakugan", "Triskelion", "Exit"};
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
            	drawFigure(0, "Star", 200);
            } else if (selection == 1) {
            	//	Alien was selected
            	drawFigure(1, "Alien", 300);
            } else if (selection == 2) {
           	//	Bakugan was selected
            	drawFigure(3, "Bakugan", 300);
            } else if (selection == 3) {
            	//	Triskelion was selected
				Treskelion("Triskelion", 100);
            }
        }		
	}
	
	public static void Treskelion(String name, int power) {
		LCD.clear();
		LCD.drawString("Spyrograph", 3, 1);
		LCD.drawString(name, 1, 4);
		Motor.B.setSpeed(power);
		Motor.B.forward();
		while (Motor.B.getTachoCount() < 3600);
		Motor.B.stop(true);
		//	reset the Tacho count so other figures can still be drawn
		Motor.B.resetTachoCount();
	}
	
	public static void drawFigure(int selected, String name, int power) {
		LCD.clear();
		LCD.drawString("Spyrograph", 3, 1);
		LCD.drawString(name, 1, 4);
		Motor.C.setSpeed(power);
		switch (selected) {
			case 0:	Motor.A.setSpeed(power);
				break;
			case 1:	Motor.A.setSpeed(power/2);
				break;
			case 3: Motor.A.setSpeed(power*2);
				break;
		}
		Motor.C.forward();
		Motor.A.forward();
		while (Motor.C.getTachoCount() < 3600);
		Motor.C.stop(true);
		Motor.A.stop(true);
		//	reset the Tacho count so other figures can still be drawn
		Motor.C.resetTachoCount();
	}
}