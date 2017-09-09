package utility;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utility.TextMenu;
import utility.Keys;

@SuppressWarnings("serial")
public class Brick extends JFrame {
	public static Keys buttons = new Keys();
	public static TextLCD scherm = new TextLCD();
	
	public Brick() {
		setTitle("EV3 - Brick");
		setSize(300, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(245, 245, 250));
		setLocationRelativeTo(null);
		createBrick();
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
		
		String[] figures = {"Triangle", "Square", "Hexagon", "Octagon", "Exit"};
		// create and display a menu with the options, with a title above it: "FirureBot"
		TextMenu menu = new TextMenu(figures, 1, "FigureBot");
		// define variable for the selection
		int selection;
		selection = menu.select(3);
		TextLCD.clear();
		TextLCD.drawString("selected: " + selection, 0, 4);
		
//		String name = TextLCD.inputName();
//		TextLCD.drawString("name is " + name, 0, 2);
	}
	
	private void createBrick() {
		JPanel inhoud = new JPanel();
		inhoud.setBorder(BorderFactory.createEmptyBorder(60, 80, 0, 80)); // top, left, bottom, right
//		TextLCD scherm = new TextLCD();
		inhoud.add(scherm);
//		buttons = new Keys();
		inhoud.add(buttons);
		setContentPane(inhoud);
	}

	public static Keys getKeys() {
		return buttons;
	}
	
	public static TextLCD getTextLCD() {
		return scherm;
	}

}