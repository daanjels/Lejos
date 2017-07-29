package trials;

public class StringToDisplay {

	public static void main(String[] args) {
		// place the string to display in this variable
		String tekst = "Dit is een zin om te tonen in EV3-programma.\nEn hier volgt een tweede zinnetje.\nEn hier volgt een derde zinnetje.\nEn hier volgt een vierde.\n";
		tekst = (Chop(tekst));		// chop it up into lines of text
//		System.out.println(tekst);	// display it in the console
		String[] chops = tekst.split("\n");		// create an array of strings to store all the lines
		if (chops.length < 9) {					// if it fits the screen go ahead
			for (int i = 0; i < chops.length; i++) {	// iterate through the array
				System.out.println("LCD.drawString(\"" + chops[i] + "\", 0, " + i + ");");	// show the EV3 code on the console
			}
		} else {
			System.out.println("The string is too long to fit on the screen");	// display a warning in the console if the string is too long.
			// ultimately the text should be displayed using a scroll mechanism.
		}
		System.exit(0);
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
				} else if (message.substring(i,i+1).equals("-")) {			// or find a dash
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
