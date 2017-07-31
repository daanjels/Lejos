package display;

import lejos.hardware.Button;
import myEV3.Screen;

public class DisplayScreen {

	public static void main(String[] args) {
		Screen.flow("This paragraph shows that new-line characters and dashes are supported.\n   this piece\n   shows that\n   spaces are kept\nAnd by now you've surely noticed the scroll-functionality. Califragialisticexpialodouscious is a long word that can't break, so it simply flow to the next line.");
		Button.waitForAnyPress();
		Screen.flow("This string is far too long to fit on the LCD-display. So pressing the down button will scroll the text up and add a line at the bottom. Whether it is good practice to have users press the button all the time is another question.");
		Button.waitForAnyPress();
	}
}