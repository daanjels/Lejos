package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LCD extends JPanel implements ActionListener {
	/**
	 * 
	 */
	public static int keyPressed = 0;
	
	private static final long serialVersionUID = -313427542397485480L;
	private static JTextArea lcd;
	private static String[] displayText = {"", "", "", "", "", "", "", ""};
	static Keys buttons = new Keys();

	public LCD() {
		JPanel inhoud = this;
		inhoud.setBorder(BorderFactory.createEmptyBorder(60, 80, 0, 80)); // top, left, bottom, right
		JPanel scherm = createScreen();
		inhoud.add(scherm );
		JPanel buttons = createButtons();
		inhoud.add(buttons);
	}
	
	public static void main(String[] args) {
		showGUI();
		drawString("Hello", 0, 0);
		drawString("This is on line four", 0, 4);
		drawString("123456789012345678", 0, 6);
		drawString("12345", 6, 6);
		drawString("col six", 6, 2);
		clear();
		drawString("this is not in capitals", 0, 2, true);
//		double value = takeInput(10, 2, 70.00, 5);
		String value = takeInput(3, 2, 70.00, 2, 5);
		drawString(value, 0, 6);
	}
	
	/** takeInput from the EV3 brick.
	* @param digits The number of integer digits (before the decimal point): Maximum 6
	* @param floats The number of decimal figures (after the decimal point): Maximum 4
	* @param value The default value that the user can start from.
	* @param col The col to display the value.
	* @param row The row to display the value.
	*/
	
	private static String takeInput(int digits, int floats, double value, int pos, int row) {
		double[] increments = {100000, 10000, 1000, 100, 10, 1, 0.0, 0.1, 0.01, 0.001, 0.0001, 0.00001};
		double[] increment = Arrays.copyOfRange(increments, 6-digits, 11);
		int limit = digits + 1 + floats; // total number of position for the double format
		int col = pos + digits - 1; // position of the 'cursor' (before de decimal point
		String decimalValue = String.format("%1." + floats + "f", value); // double value with correct format
		int lead = limit - decimalValue.length(); // leading space

		drawString(String.format(Locale.CANADA, "%1$," + limit + "." + floats + "f", value), pos, row);
		decimalValue = decimalValue.substring(col - lead, col - lead + 1);
		drawString(decimalValue, col, row-1);
		while (buttons.getButtons() != Keys.ID_ENTER) {
			if (buttons.getButtons() == Keys.ID_UP) {
				value = value + increment[col];
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				value = value - increment[col];
				if (value < 0) value = value + increment[col];
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
				drawString(" ", col + pos, row-1);
				col = col + 1;
				System.out.println("col: " + col + " / limit: " + limit);
				if (col == limit) col = lead;
				if (col == digits) col = digits + 1;
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
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

//	private static int takeInput(int decimals, int floats, double value, int row) {
//	int limit = decimals + 1 + floats;
//	String oldValue = String.format("%1$" + limit + "." + floats + "f", value);
//	System.out.println("123456789");
//	System.out.println(oldValue);
//	String digit;
//	int s1, s2, i = limit, dig;
//	int[] newValue = {};
//	while (oldValue.length() > 1) {
//		s1 = oldValue.length()-1;
//		s2 = oldValue.length();
//		System.out.println(s1 + ":" + s2);
//		digit = oldValue.substring(s1, s2);
//		System.out.println("digit: " + digit);
//		if (digit != ",") {
//			newValue[i] = Integer.parseInt(digit);
//			oldValue = oldValue.substring(0, oldValue.length() -1);
//			System.out.println("old: " + oldValue);// + " ; new: " + newValue[i]);
//		} else {
//			newValue[i] = 0;
//		}
//		i--;
//	}
//	return row;
//}

	public static void drawString(String message, int col, int row) {
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
		displayText[row] = newMsg + "\n";
		refreshLCD();
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

	public static void screenWait(int time, int row) {
		for (int i = 0; i < 9; i++) {
			LCD.drawString("* ", i*2, row);
			Delay.msDelay(time/9);
		}
		System.out.println("");
	}

	public static String inputName() {
		String inName = "ev3";
		LCD.clear();
		LCD.drawString("EV3 calibration", 0, 0);
		LCD.drawString("Give your robot", 0, 2);
		LCD.drawString("a name:", 0, 3);
		while (buttons.getButtons() != Keys.ID_ENTER) {
			inName = takeInput(true, "EV3", 4);
		}
		LCD.drawString("> " + inName, 0, 5);
		Delay.msDelay(200);
		return inName;
	}

	private static String takeInput(boolean type, String name, int row) {
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
//			System.out.println(input);
		}
		return input;
	}

	private static void refreshLCD() {
		StringBuilder builder = new StringBuilder();
		for(String s : displayText) {
			builder.append(s);
		}
		String text = builder.toString();
		lcd.setText(text);
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

//	private static JPanel creatPanel() {
//		JPanel inhoud = new JPanel();
//		inhoud.setBorder(BorderFactory.createEmptyBorder(60, 80, 0, 80)); // top, left, bottom, right
//		JPanel scherm = createScreen();
//		inhoud.add(scherm );
//		JPanel buttons = createButtons();
//		inhoud.add(buttons);
//		return inhoud;
//	}

	private static JPanel createScreen() {
		JPanel scherm = new JPanel();
		scherm.setPreferredSize(new Dimension(178, 158));
		scherm.setBackground(new Color(235, 238, 232));
		scherm.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		lcd = new JTextArea(10, 19);
		lcd.setBackground(new Color(240, 243, 237));
		lcd.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
		lcd.setLineWrap(true);
		lcd.setFont(new Font("Monaco", 0, 14));
		lcd.setEditable(false);
		clear();
		scherm.add(lcd);
		return scherm;
	}

	private JPanel createButtons() {
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(178, 128));
		buttons.setBackground(new Color(255, 200, 150));
		JButton escBtn = new JButton("=");
		escBtn.setActionCommand("keyEscape");
		escBtn.setToolTipText("ESCAPE button");
		escBtn.addActionListener(this);
		JButton upBtn = new JButton("^");
		upBtn.setActionCommand("keyUp");
		upBtn.setToolTipText("UP button");
		upBtn.addActionListener(this);
		JButton leftBtn = new JButton("<");
		leftBtn.setActionCommand("keyLeft");
		leftBtn.setToolTipText("LEFT button");
		leftBtn.addActionListener(this);
		JButton enterBtn = new JButton("+");
		enterBtn.setActionCommand("keyEnter");
		enterBtn.setToolTipText("ENTER button");
		enterBtn.addActionListener(this);
		JButton rightBtn = new JButton(">");
		rightBtn.setActionCommand("keyRight");
		rightBtn.setToolTipText("RIGHT button");
		rightBtn.addActionListener(this);
		JButton downBtn = new JButton("v");
		downBtn.setActionCommand("keyDown");
		downBtn.setToolTipText("DOWN button");
		downBtn.addActionListener(this);
		buttons.add(escBtn);
		buttons.add(upBtn);
		buttons.add(leftBtn);
		buttons.add(enterBtn);
		buttons.add(rightBtn);
		buttons.add(downBtn);
		return buttons;
	}

	public static void clear() {
		for (int i = 0; i < 8; i++) {
			displayText[i] = "                  \n";
		}
		refreshLCD();
	}
	public static final int ID_UP = 0x1;
	public static final int ID_ENTER = 0x2;
	public static final int ID_DOWN = 0x4;
	public static final int ID_RIGHT = 0x8;
	public static final int ID_LEFT = 0x10;
	public static final int ID_ESCAPE = 0x20;

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("keyUp".equals(e.getActionCommand())) {
			System.out.print("UP - ");
			keyPressed = 0x1;
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyDown".equals(e.getActionCommand())) {
			System.out.print("DOWN - ");
			keyPressed = 0x4;
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyLeft".equals(e.getActionCommand())) {
			System.out.print("LEFT - ");
			keyPressed = 0x10;
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyRight".equals(e.getActionCommand())) {
			System.out.print("RIGHT - ");
			keyPressed = 0x8;
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyEnter".equals(e.getActionCommand())) {
			System.out.print("ENTER - ");
			keyPressed = 0x2;
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyEscape".equals(e.getActionCommand())) {
			System.out.print("ESCAPE - ");
			keyPressed = 0x20;
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
	}
}