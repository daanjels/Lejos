package utility;

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
	
	public Brick() {
		setTitle("EV3 - Virtual Brick");
//		setSize(300, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBackground(new Color(245, 245, 250));
		setLocationRelativeTo(null);
		createBrick();
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Brick walle = BrickFinder.getDefault();
		walle.setVisible(true);
		TextLCD.drawString("Hello there!", 0, 0);
		if (buttons.getButtons() == Keys.ID_ENTER) {
			TextLCD.drawString("Enter was pressed", 0, 1);
			Delay.msDelay(1000);
		};
		TextLCD.clear();
		TextLCD.drawString("Bye bye!", 0, 0);
		Delay.msDelay(1000);
		buttons.waitForAnyPress();
		
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
		layer.add(scherm, new Integer(10));
		layer.add(buttons, new Integer(11));

		setContentPane(layer);
	}

	public static Keys getKeys() {
		return buttons;
	}
	
//	public static TextLCD getTextLCD() {
	public static TextLCD getTextLCD() {
		return scherm;
	}

}