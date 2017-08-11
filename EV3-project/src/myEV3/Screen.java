package myEV3;
 /**
  * An 'empty' class to add a method that extends the LCD functionality
  * The 'flow' method will take a string (with or without new-line characters) and flow them on the screen
  * Text will break on spaces
  * New-line characters are kept, making paragraphs possible
  * Words also break on dashes
  * Words longer than 18 characters will flow to the next line
  * Text that flow on more than 8 lines will be scrolled through, using the down button
  * 
  * @author Arqetype
  * @version 1.0
  */

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Screen {
	
	private Screen() {
	}
	
	/**
	 * Flow a message on the display, using word wrap, with scroll support
	 * @param message String to flow on the display. New-line characters are supported.
	 */
	public static void flow(String message) {
		String chopString = "";						// define a string to store the result
		int counter = 1;							// counter stores the number of paragraphs, default is 1
		String[] messages = {""};					// string array to store the paragraphs
		if (message.contains("\n")) {			// if new-line character is used...
			messages = message.split("\n");		// create an array of strings to store the paragraphs of the original message
			counter = messages.length;			// set the counter to the number of paragraphs
		}
		
		for (int count = 0; count < counter; count++) {	// iterate through the paragraphs
			if (counter > 1) {
				message = messages[count];				// pick the current paragraph
			}
			while (message.length() > 18) {				// while the paragraph is longer than 18 characters, chop it up
				for (int i=18; i > 0; i--) {			// start from the 18th character and go back
					if (message.substring(i,i+1).equals(" ")) {						// find a space
						chopString = chopString + message.substring(0, i) + "\n";		// put the first part as a separate line in the result, removing the space
						message = message.substring(i+1);								// remove the first part of the string
						i = 0;															// stop searching
					} else if (message.substring(i,i+1).equals("-") && i < 18) {	// or find a dash
						chopString = chopString + message.substring(0, i+1) + "\n";		// put the first part as a separate line in the result
						message = message.substring(i+1);								// remove the first part of the string
						i = 0;															// stop searching
					}
					if (i == 1) {
						chopString = chopString + message.substring(0, 18) + "\n";
						message = message.substring(18);
					}
				}
			}
			if (message.length() > 0) {
				chopString = chopString + message;		// append the leftover string to the result
			}
			if (counter > 1) chopString = chopString + "\n";	// keep the original paragraphs
		}
		String[] chops = chopString.split("\n");		// create an array of strings to store all the lines
		LCD.clear();
		for (int i = 0; i < chops.length; i++) {	// iterate through the array
			if (i == chops.length-1 && i > 7) {
				LCD.drawString("" + chops[i], 0, 7);	// show the last line on the EV3 display
				Delay.msDelay(500);
			} else if (i > 6) {							// if the text is longer than 7 lines
				LCD.drawString("\\/     ", 7, 7);	// display an arrow at the bottom
				while (BrickFinder.getLocal().getKeys().getButtons() != Keys.ID_DOWN);	// wait for the user to press the down button
				LCD.scroll();							// scroll the screen up
				LCD.drawString("  ", 7, 6);				// remove the arrow that moved up
				LCD.drawString("" + chops[i], 0, 6);	// show the next string on line 7
				Delay.msDelay(250);						// wait to prevent the key press be used again
			}
			else {
				LCD.drawString("" + chops[i], 0, i);	// show the line on the EV3 display
				Delay.msDelay(100);
			}
		}
		return;
	}

}
