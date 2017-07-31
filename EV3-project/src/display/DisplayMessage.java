package display;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class DisplayMessage {

	public static void main(String[] args) {
		stringDisplay("Hello World!");
		Button.waitForAnyPress();
		stringDisplay("En nu een langere zin die zeker niet past op een lijn.");
		Button.waitForAnyPress();
		stringDisplay("De volgende zin is langer, maar past die ook op het EV3-scherm?");
		Button.waitForAnyPress();
		stringDisplay("De zin die nu volgt is weer wat anders. En nu sym-controle op koppel-tekens.");
		Button.waitForAnyPress();
		stringDisplay("De zin die nu volgt is hetzelfde. Inclusief de sym-controle op koppel-tekens. Maar deze zin maakt het langer, en zo past die niet op het EV3-scherm?");
		Button.waitForAnyPress();
	}

	private static void stringDisplay(String message) {
		message = (Chop(message));		// chop it up into lines of text
		String[] chops = message.split("\n");		// create an array of strings to store all the lines
		LCD.clear();
		if (chops.length < 9) {					// if it fits the screen go ahead
			for (int i = 0; i < chops.length; i++) {	// iterate through the array
				LCD.drawString(chops[i], 0, i);	// show the line on the EV3 display
			}
		} else {
			System.out.println("The string is too long to fit on the screen");	// display a warning in the console if the string is too long.
			// ultimately the text should be displayed using a scroll mechanism.
		}
		return;
	}

	private static String Chop(String message) {
		String chopString = "";						// define a string to store the result
		message = message.replaceAll(" \n", "\n");	// remove trailing spaces before end of lines
		message = message.replaceAll("-\n", "-");	// remove end of lines, keeping dashes
		message = message.replaceAll("\n", " ");	// replace end of lines by spaces
		
		while (message.length() > 18) {				// while the string is longer than 18 characters, chop it up
			for (int i=18; i > 0; i--) {			// start from the 18th character and go back
				if (message.substring(i,i+1).equals(" ")) {					// find a space
					chopString = chopString + message.substring(0, i) + "\n";	// put the first part as a separate line in the result
					message = message.substring(i+1);							// remove the first part of the string
					i = 0;														// stop searching for spaces
				} else if (message.substring(i,i+1).equals("-") && i < 18) {			// or find a dash
					chopString = chopString + message.substring(0, i+1) + "\n";	// put the first part as a separate line in the result
					message = message.substring(i+1);							// remove the first part of the string
					i = 0;														// stop searching for spaces
				}
			}
		}
		if (message.length() > 0) {
			chopString = chopString + message;		// append the leftover string to the result
		}
		return chopString;
	}

}
