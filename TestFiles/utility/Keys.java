package utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Keys extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8360658058142752913L;
	public static final int ID_UP = 0x1;
	public static final int ID_ENTER = 0x2;
	public static final int ID_DOWN = 0x4;
	public static final int ID_RIGHT = 0x8;
	public static final int ID_LEFT = 0x10;
	public static final int ID_ESCAPE = 0x20;
	public static final int ID_ALL = 0x3f;

	public static int keyPressed = 0;
	private int curButtonsS;

	public Keys() {
		setPreferredSize(new Dimension(178, 128));
		setBackground(new Color(255, 220, 175));

		JButton escBtn = new JButton("=");
		escBtn.setActionCommand("keyEscape");
		escBtn.setToolTipText("ESCAPE button");
		escBtn.addActionListener(this);
		escBtn.setPreferredSize(new Dimension(55, 20));
		JButton upBtn = new JButton("^");
		upBtn.setActionCommand("keyUp");
		upBtn.setToolTipText("UP button");
		upBtn.addActionListener(this);
		JButton leftBtn = new JButton("<");
		leftBtn.setActionCommand("keyLeft");
		leftBtn.setToolTipText("LEFT button");
		leftBtn.addActionListener(this);
		leftBtn.setPreferredSize(new Dimension(55, 20));
		JButton enterBtn = new JButton("+");
		enterBtn.setActionCommand("keyEnter");
		enterBtn.setToolTipText("ENTER button");
		enterBtn.addActionListener(this);
		enterBtn.setPreferredSize(new Dimension(40, 20));
		JButton rightBtn = new JButton(">");
		rightBtn.setActionCommand("keyRight");
		rightBtn.setToolTipText("RIGHT button");
		rightBtn.addActionListener(this);
		rightBtn.setPreferredSize(new Dimension(55, 20));
		JButton downBtn = new JButton("v");
		downBtn.setActionCommand("keyDown");
		downBtn.setToolTipText("DOWN button");
		downBtn.addActionListener(this);
		
		JPanel escape = new JPanel(new BorderLayout(10, 10));
		escape.setPreferredSize(new Dimension(178, 25));
		escape.setBackground(new Color(255, 220, 175));
		escape.add(escBtn, BorderLayout.WEST);
		JPanel up = new JPanel(new BorderLayout(10, 10));
		up.setPreferredSize(new Dimension(178, 25));
		up.setBackground(new Color(255, 180, 150));
		up.add(upBtn, BorderLayout.CENTER);
		JPanel center = new JPanel(new BorderLayout(10, 10));
		center.setPreferredSize(new Dimension(178, 25));
		center.setBackground(new Color(255, 220, 175));
		center.add(leftBtn, BorderLayout.WEST);
		center.add(enterBtn);
		center.add(rightBtn, BorderLayout.EAST);

		add(escape);
		add(upBtn);
		add(center);
		add(downBtn);
	}
	
	public int readButtons() {
		int newButtons = getButtons();
		int pressed = newButtons & (~curButtonsS);
		curButtonsS = newButtons;
		if (pressed != 0) {
		}
		return newButtons;
	}

	public int getButtons() { // used in TextLCD
		int key = 0;
		while (keyPressed == 0) {
			TextLCD.drawString("", 0, 6);
		}
		key = keyPressed;
		return key;
	}
	public int getButtons(int interval) { // used in TextLCD
		int key = 0;
		while (keyPressed == 0) {
			TextLCD.drawString("", 0, 6);
		}
		key = keyPressed;
		Delay.msDelay(interval);
		return key;
	}
	public int getBttns() { // used in LCD
		int key = 0;
		while (keyPressed == 0) {
			LCD.drawString("", 0, 6);
		}
		key = keyPressed;
		return key;
	}

	public int waitForAnyPress() {
//		Delay.msDelay(2000);
		while (keyPressed == 0) {};
		return 1;
	}
	
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
