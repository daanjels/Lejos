package utility;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import utility.Ev3Backdrop;
//import utility.TextMenu;
import utility.Keys;

public class Brick extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Keys buttons = new Keys();
	public static TextLCD scherm = new TextLCD();
	public static Leds leds = new Leds();
	
	public Brick() {
		setTitle("EV3 - Virtual Brick");
		setSize(new Dimension(340, 512)); // this sets the size of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(225, 150, 45)); // background colour warm yellow
		setLocationRelativeTo(null); // centers the frame on the screen, if the size is given.
		createBrick(); // build a virtual brick
//		this.pack();
		this.setVisible(true); // show the frame
	}

	public static void main(String[] args) {
//		main can be used to test the possibilities of the virtual brick
		Brick walle = BrickFinder.getDefault(); // this makes it compliant to EV3 code
//		walle.setVisible(true);
		scherm.drawString("Hello there!", 0, 0);
//		if (buttons.getButtons() == Keys.ID_ENTER) {
//			scherm.drawString("Enter was pressed", 0, 1);
//			Delay.msDelay(500);
//		};
//		scherm.clear();
//		scherm.drawString("Bye bye!", 0, 0);
		
		for (int i = 1; i < 9; i++)
		{
			buttons.waitForAnyPress();
			scherm.clear();
			scherm.drawString("Pattern " + i, 0, i-1);
			leds.setPattern(i);
		}
		
//		String[] figures = {"Triangle", "Square", "Hexagon", "Octagon", "Exit"};
//		TextMenu menu = new TextMenu(figures, 1, "FigureBot");
//		int selection;
//		selection = menu.select(3);
//		scherm.clear();
//		scherm.drawString("selected: " + selection, 0, 4);
		
//		String name = scherm.inputName();
//		scherm.drawString("name is " + name, 0, 2);
		buttons.waitForAnyPress();
		System.exit(0);
	}
	
	private void createBrick() {
		JLayeredPane layer = new JLayeredPane();
		layer.setPreferredSize(new Dimension(340, 512));

		layer.add(new Ev3Backdrop(), JLayeredPane.DEFAULT_LAYER);
		layer.add(scherm, new Integer(9));
		layer.add(leds,  new Integer(10));
		layer.add(buttons, new Integer(11));

		setContentPane(layer);
	}

	public static Keys getKeys() {
		return buttons;
	}
	
	public static TextLCD getTextLCD() {
		return scherm;
	}

	public static Leds getLed() {
		return leds;
	}
}