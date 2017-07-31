package display;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class DisplayMessage {

	public static void main(String[] args) {
		wordWrap("Hello World!");
		Button.waitForAnyPress();
//		wordWrap("This sentence was created for the sole purpose of testing my wordwrap method.");
//		Button.waitForAnyPress();
//		wordWrap("This sentence can be useful to see if I correctly split words-with-dashes. Even for hyphens at the end-of-the-line.");
//		Button.waitForAnyPress();
//		wordWrap("This paragraph is seperate from the next.\nThis can be done using an end of line marker.\nYou can see that it works.");
//		Button.waitForAnyPress();
//		wordWrap("Spaces are kept (spaces are only removed for wrapping).\n   As this example\n   proves.\n\nSee?");
//		Button.waitForAnyPress();
		wordWrap("This string is far too long to fit on the LCD-display. So pressing the down button will scroll the text up and add a line at the bottom. This way strings of any length are supported. Of course the question is if it is good practice to have users press the button all the time.");
		Button.waitForAnyPress();
	}

	private static void wordWrap(String message) {
		String chopString = "";						// define a string to store the result
		int counter = 1;
		String[] messages = {""};
		if (message.contains("\n")) {
			messages = message.split("\n");		// create an array of strings to store the paragraphs of the original message
			counter = messages.length;
		}
		
		for (int count = 0; count < counter; count++) {
			if (counter > 1) {
				message = messages[count];
			}
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
			if (counter > 1) chopString = chopString + "\n";	// keep the original line-endings
		}
		String[] chops = chopString.split("\n");		// create an array of strings to store all the lines
		LCD.clear();
		if (chops.length < 8) {					// if it fits the screen go ahead
			for (int i = 0; i < chops.length; i++) {	// iterate through the array
				LCD.drawString(chops[i], 0, i);	// show the line on the EV3 display
			}
		} else {
			for (int i = 0; i < chops.length; i++) {	// iterate through the array
				if (i == chops.length-1) {
					LCD.drawString("" + chops[i], 0, 7);	// show the last line on the EV3 display
				} else if (i > 6) {							// if the text is longer than 7 lines
					LCD.drawString("        \\/     ", 0, 7);	// display an arrow at the bottom
					while (BrickFinder.getLocal().getKeys().getButtons() != Keys.ID_DOWN);	// wait for the user to press the down button
					LCD.scroll();							// scroll the screen up
					LCD.drawString("" + chops[i], 0, 6);	// show the next string on line 7
					Delay.msDelay(250);						// wait to prevent the key press be used again
				}
				else {
					LCD.drawString("" + chops[i], 0, i);	// show the line on the EV3 display
				}
			}
		}
		return;
	}

}
