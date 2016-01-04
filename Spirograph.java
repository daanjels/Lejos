package be.wimdaniels;

import lejos.nxt.Motor;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class Spirograph {

	public static void main(String[] args) {

	    String[] figures = {"Star", "Alien", "Triskelion", "Bakugan", "Exit"};
		TextMenu main = new TextMenu(figures, 1, "Spyrograph");
		int selection;
		int power;
		
		for(;;)
		{
			LCD.clear();
			selection = main.select();

			if (selection == -1 || selection == 4)
			{
				LCD.clear();
				LCD.drawString("Finished",3,4);
				LCD.refresh();	
				return;
			}

			if (selection == 0) { // Star
				LCD.clear();
				LCD.drawString("Spyrograph", 3, 1);
				LCD.drawString("Star", 1, 4);
				power = 200;
				Motor.C.setSpeed(power);
				Motor.A.setSpeed(power*3);
				Motor.C.forward();
				Motor.A.forward();
				while (Motor.C.getTachoCount() < 3600);
				Motor.C.stop();
				Motor.A.stop();
//			    Button.ESCAPE.waitForPressAndRelease();
            } else if (selection == 1) { // Alien
            	LCD.clear();
            	LCD.drawString("Spyrograph", 3, 1);
            	LCD.drawString("Alien", 1, 4);
				power = 300;
				Motor.C.setSpeed(power);
				Motor.A.setSpeed(power/2);
				Motor.C.forward();
				Motor.A.forward();
				while (Motor.C.getTachoCount() < 3600);
				Motor.C.stop();
				Motor.A.stop();
//			    Button.ESCAPE.waitForPressAndRelease();
            } else if (selection == 2) { // Triskelion
				LCD.clear();
				LCD.drawString("Spyrograph", 3, 1);
				LCD.drawString("Triskelion", 1, 4);
				power = 100;
				Motor.B.setSpeed(power);
				Motor.B.forward();
				while (Motor.B.getTachoCount() < 3600);
				Motor.B.stop();
//			    Button.ESCAPE.waitForPressAndRelease();
            } else if (selection == 3) { // Bakugan
				LCD.clear();
				LCD.drawString("Spyrograph", 3, 1);
				LCD.drawString("Bakugan", 1, 4);
				power = 300;
				Motor.C.setSpeed(power);
				Motor.A.setSpeed(power*2);
				Motor.C.forward();
				Motor.A.forward();
				while (Motor.C.getTachoCount() < 3600);
				Motor.C.stop();
				Motor.A.stop();
//			    Button.ESCAPE.waitForPressAndRelease();
            }
        }		

//			if (Button.LEFT.isDown()) {
//				LCD.clear();
//				LCD.drawString("Spyrograph", 3, 1);
//				LCD.drawString("Star", 1, 4);
//				LCD.drawString("Power: 200 / 600", 1, 5);
//				power = 200;
//				Motor.C.setSpeed(power);
//				Motor.A.setSpeed(power*3);
//				Motor.C.forward();
//				Motor.A.forward();
//				while (Motor.C.getTachoCount() < 3600);
//				Motor.C.stop();
//				Motor.A.stop();
//			}
//			if (Button.LEFT.isDown()) {
//				LCD.clear();
//				LCD.drawString("Spyrograph", 3, 1);
//				LCD.drawString("Toothstar", 1, 4);
//				LCD.drawString("Power: 300 / 600", 1, 5);
//				power = 300;
//				Motor.C.setSpeed(power);
//				Motor.A.setSpeed(power*2);
//				Motor.C.forward();
//				Motor.A.forward();
//				while (Motor.C.getTachoCount() < 3600);
//				Motor.C.stop();
//				Motor.A.stop();
//			}
//			if (Button.LEFT.isDown()) {
//				LCD.clear();
//				LCD.drawString("Spyrograph", 3, 1);
//				LCD.drawString("Fractal", 1, 4);
//				LCD.drawString("Power: 100 / 600", 1, 5);
//				power = 100;
//				Motor.B.setSpeed(power);
//				Motor.A.setSpeed(power*6);
//				Motor.B.forward();
//				Motor.A.forward();
//				while (Motor.B.getTachoCount() < 3600);
//				Motor.B.stop();
//				Motor.A.stop();
//			}			
//			if (Button.LEFT.isDown()) {
//				LCD.clear();
//				LCD.drawString("Spyrograph", 3, 1);
//				LCD.drawString("Alien", 1, 4);
//				LCD.drawString("Power: 300 / 150", 1, 5);
//				power = 300;
//				Motor.C.setSpeed(power);
//				Motor.A.setSpeed(power/2);
//				Motor.C.forward();
//				Motor.A.forward();
//				while (Motor.C.getTachoCount() < 3600);
//				Motor.C.stop();
//				Motor.A.stop();
//			}
//			if (Button.LEFT.isDown()) {
//				LCD.clear();
//				LCD.drawString("Spyrograph", 3, 1);
//				LCD.drawString("Triskelion", 1, 4);
//				LCD.drawString("Power: 100", 1, 5);
//				power = 100;
//				Motor.B.setSpeed(power);
//				Motor.B.forward();
//				while (Motor.B.getTachoCount() < 3600);
//				Motor.B.stop();
//			}			

	}
}
