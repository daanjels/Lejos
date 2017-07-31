package display;

import lejos.hardware.Button;
import myEV3.Screen;

public class DisplayScreen {

	public static void main(String[] args) {
		Screen.flow("De zin die nu volgt is weer wat anders. En nu sym-controle op koppel-tekens.");
		Button.waitForAnyPress();
		Screen.flow("Laten we nog eens proberen met een lange zin.\n\nEventueel met meerderde paragrafen gemaakt door end-of-line-markers. En dan eens zien of deze aangepaste Display class goed werkt.");
		Button.waitForAnyPress();
		Screen.flow("This string is far too long to fit on the LCD-display. So pressing the down button will scroll the text up and add a line at the bottom. This way strings of any length are supported. Of course the question is if it is good practice to have users press the button all the time.");
		Button.waitForAnyPress();
	}
}