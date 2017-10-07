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
		setSize(new Dimension(340, 512)); // this way the frame will be centered on the screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(225, 150, 45));
		setLocationRelativeTo(null); // centers the frame on the screen, if the size is given.
		createBrick();
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Brick walle = BrickFinder.getDefault();
		walle.setVisible(true);
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