package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import utility.Keys;

public class LCD extends JPanel {
	public static int keyPressed = 0;
	
	private static final long serialVersionUID = -313427542397485480L;
	private static JTextArea lcd;
	private static String[] displayText = {"", "", "", "", "", "", "", ""};
	static Keys buttons = new Keys();

	public static void main(String[] args) {
		showGUI();
		drawString("Hello", 0, 0);
		drawString("This is on line four", 0, 4);
		drawString("123456789012345678", 0, 6);
		drawString("12345", 6, 6);
		drawString("col six", 6, 2);
		Delay.msDelay(1000);
		clear();
		drawString("These are capitals", 0, 2, true);
		String value = inputNumber(3, 2, 70.00, 2, 5);
		drawString("                  ", 0, 6);
		drawString(value, 0, 6);
		Delay.msDelay(200);
		while (buttons.getBttns() == 0) {};
		clear();
		drawString("Goodbye", 0, 4);
		Delay.msDelay(2000);
	}
	
	/** Take double value input from the EV3 brick.
	* @param digits The number of integer digits (before the decimal point): Maximum 6
	* @param floats The number of decimal figures (after the decimal point): Maximum 4
	* @param value The default value (double) that the user can start from.
	* @param pos The starting position to display the value.
	* @param row The row to display the value.
	*/
	
	private static String inputNumber(int digits, int floats, double value, int pos, int row) {
		double[] increments = {100000, 10000, 1000, 100, 10, 1, 0.0, 0.1, 0.01, 0.001, 0.0001, 0.00001};
		double[] increment = Arrays.copyOfRange(increments, 6-digits, 11);
		int limit = digits + 1 + floats; // total number of position for the double format
		int col = pos + digits - 1; // position of the 'cursor' (before de decimal point
		String decimalValue = String.format("%1." + floats + "f", value); // double value with correct format
		int lead = limit - decimalValue.length(); // leading space

		drawString(String.format(Locale.CANADA, "%1$," + limit + "." + floats + "f", value), pos, row);
		decimalValue = decimalValue.substring(col - lead, col - lead + 1);
		drawString(decimalValue, col, row-1);
		while (buttons.getBttns() != Keys.ID_ENTER) {
			if (buttons.getBttns() == Keys.ID_UP) {
				value = value + increment[col];
			} else if (buttons.getBttns() == Keys.ID_DOWN) {
				value = value - increment[col];
				if (value < 0) value = value + increment[col];
			} else if (buttons.getBttns() == Keys.ID_RIGHT) {
				drawString(" ", col + pos, row-1);
				col = col + 1;
				System.out.println("col: " + col + " / limit: " + limit);
				if (col == limit) col = lead;
				if (col == digits) col = digits + 1;
			} else if (buttons.getBttns() == Keys.ID_LEFT) {
				drawString(" ", col + pos, row-1);
				col = col - 1;
				if (col == digits) col = digits - 1; // skip the decimal point
				if (decimalValue == "_" || col < 0) col = limit - 1; // if at the start, go to the end
			}
			decimalValue = String.format("%1." + floats + "f", value);
			if (decimalValue.length() > limit) {
				value = value - increment[col];
				decimalValue = String.format("%1." + floats + "f", value);
			}
			lead = limit - decimalValue.length();
			drawString(String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value), pos, row);
			if (col - lead < 0) {
				decimalValue = "_";
			} else {
				decimalValue = decimalValue.substring(col - lead, col - lead + 1);
			}
			drawString(decimalValue, col + pos, row-1);
			Delay.msDelay(200);
		}
		buttons.waitForAnyPress();
		return String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value);
	}

	public static void drawString(String message, int col, int row) {
		drawString(message, col, row, false);
	}

	public static void drawString(String message, int col, int row, boolean b) {
		int max = message.length();
		String oldMsg = displayText[row];
		String newMsg = "";
		for (int i = 0; i < 18; i++) {
			if (i >= col && i < col + max) {
				newMsg = newMsg + message.substring(i - col, i - col + 1);
			} else {
				newMsg = newMsg + oldMsg.substring(i, i+1);
			}
		}
		if (b == true) newMsg = newMsg.toUpperCase();
		displayText[row] = newMsg + "\n";
		refreshLCD();
	}

	public static String inputName() {
		String inName = "ev3";
		LCD.clear();
		LCD.drawString("EV3 calibration", 0, 0);
		LCD.drawString("Give your robot", 0, 2);
		LCD.drawString("a name:", 0, 3);
		while (buttons.getButtons() != Keys.ID_ENTER) {
			inName = inputString(true, "EV3", 4);
		}
		LCD.drawString("> " + inName, 0, 5);
		Delay.msDelay(200);
		return inName;
	}

	private static String inputString(boolean type, String name, int row) {
		String alpha = " ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-0123456789";
		if (type == false) alpha = "0123456789.";
		int chr = 0;
		int pos = 0;
		String input = "";
		String[] in = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
		for (int i = 0; i < name.length(); i++) {
			in[i] = name.substring(i, i+1);
			LCD.drawString(in[i], i, row);
		}
		chr = alpha.indexOf(in[0]);
		Delay.msDelay(150);
		while(buttons.getButtons() != Keys.ID_ENTER) {
			if (buttons.getButtons() == Keys.ID_UP) {
				chr = chr + 1;
				if (chr>=alpha.length()) chr = 0;
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
				in[pos] = alpha.substring(chr, chr+1);
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				chr = chr - 1;
				if (chr<0) chr = alpha.length()-1;
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
				in[pos] = alpha.substring(chr, chr+1);
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
				in[pos] = alpha.substring(chr, chr+1);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
				pos = pos + 1;
				if (pos > 13) pos = 0;
				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
				in[pos] = alpha.substring(chr, chr+1);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
				pos = pos - 1;
				if (pos < 0) pos = 13;
				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
			}
			Delay.msDelay(150);
		}
		in[pos] = alpha.substring(chr, chr+1);
		Delay.msDelay(200);
		for (int i = 0; i < in.length; i++) {
			input = input+in[i];
		}
		return input;
	}

	public static void showGUI() {
		JFrame venster = new JFrame();
		venster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		venster.setTitle("EV3 - Display");
		venster.setSize(338, 440);
		venster.setBackground(new Color(245, 245, 250));
		venster.setLocationRelativeTo(null);
		LCD inhoud = new LCD();
		venster.setContentPane(inhoud);
		venster.setVisible(true);
	}

	public LCD() {
		JPanel inhoud = this;
		inhoud.setBorder(BorderFactory.createEmptyBorder(60, 80, 0, 80)); // top, left, bottom, right
		JPanel scherm = createScreen();
		inhoud.add(scherm );
		inhoud.add(buttons);
	}

	private static JPanel createScreen() {
		JPanel scherm = new JPanel();
		scherm.setPreferredSize(new Dimension(178, 158));
		scherm.setBackground(new Color(235, 238, 232));
		scherm.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		lcd = new JTextArea(10, 19);
		lcd.setBackground(new Color(240, 243, 237));
		lcd.setBackground(new Color(190, 210, 170));
		lcd.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
		lcd.setLineWrap(true);
		lcd.setFont(new Font("Monaco", 0, 14));
		lcd.setEditable(false);
		clear();
		scherm.add(lcd);
		return scherm;
	}

	private static void refreshLCD() {
		StringBuilder builder = new StringBuilder();
		for(String s : displayText) {
			builder.append(s);
		}
		String text = builder.toString();
		lcd.setText(text);
	}

	public static void clear() {
		for (int i = 0; i < 8; i++) {
			displayText[i] = "                  \n";
		}
		refreshLCD();
	}

	public static void screenWait(int time, int row) {
		for (int i = 0; i < 9; i++) {
			LCD.drawString("* ", i*2, row);
			Delay.msDelay(time/9);
		}
		System.out.println("");
	}

}