package utility;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Dimension;

//import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
//import javax.swing.JPanel;

import utility.Ev3Backdrop;
import utility.TextMenu;
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
//		setBackground(new Color(245, 210, 0));
		setLocationRelativeTo(null); // centers the frame on the screen, if the size is given.
		createBrick();
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Brick walle = BrickFinder.getDefault();
		walle.setVisible(true);
		TextLCD.drawString("Hello there!", 0, 0);
//		if (buttons.getButtons() == Keys.ID_ENTER) {
//			TextLCD.drawString("Enter was pressed", 0, 1);
//			Delay.msDelay(500);
//		};
//		TextLCD.clear();
//		TextLCD.drawString("Bye bye!", 0, 0);
//		buttons.waitForAnyPress();
		
		for (int i = 0; i < 9; i++)
		{
			System.out.println("Pattern " + i);
			buttons.waitForAnyPress();
			leds.setPattern(i);
		}
		
		String[] figures = {"Triangle", "Square", "Hexagon", "Octagon", "Exit"};
		TextMenu menu = new TextMenu(figures, 1, "FigureBot");
		int selection;
		selection = menu.select(3);
		TextLCD.clear();
		TextLCD.drawString("selected: " + selection, 0, 4);
		
//		String name = TextLCD.inputName();
//		TextLCD.drawString("name is " + name, 0, 2);
		buttons.waitForAnyPress();
		System.exit(0);
	}
	
	private void createBrick() {
//		JPanel inhoud = new JPanel();
//		inhoud.setBorder(BorderFactory.createEmptyBorder(60, 80, 0, 80)); // top, left, bottom, right
//		inhoud.add(scherm);
//		inhoud.add(buttons);
//		setContentPane(inhoud);
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