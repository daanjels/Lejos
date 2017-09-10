package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Keys extends JPanel implements ActionListener
{
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

	public Keys() 
	{
		setPreferredSize(new Dimension(178, 128));
		setBackground(new Color(255, 230, 205));
		setLayout(new GridBagLayout());
		GridBagConstraints lc = new GridBagConstraints();
		JButton button;
		lc.fill = GridBagConstraints.NONE;

		button = new JButton("/");
		lc.anchor = GridBagConstraints.FIRST_LINE_START;
		lc.weightx = 1;
		lc.weighty = 0.5;
		lc.gridx = 0;
		lc.gridy = 0;
		button.setActionCommand("keyEscape");
		button.setToolTipText("Escape");
		button.addActionListener(this);
		add(button, lc);
		
		button = new JButton("^");
		lc.anchor = GridBagConstraints.CENTER;
		lc.weighty = 1;
		lc.gridx = 1;
		lc.gridy = 1;
		button.setActionCommand("keyUp");
		button.setToolTipText("Up");
		button.addActionListener(this);
		add(button, lc);
		
		button = new JButton("<");
		lc.anchor = GridBagConstraints.FIRST_LINE_START;
		lc.gridx = 0;
		lc.gridy = 2;
		button.setActionCommand("keyLeft");
		button.setToolTipText("Left");
		button.addActionListener(this);
		add(button, lc);
		
		button = new JButton("+");
		lc.anchor = GridBagConstraints.FIRST_LINE_START;
		lc.gridx = 1;
		lc.gridy = 2;
		button.setActionCommand("keyEnter");
		button.setToolTipText("Enter");
		button.addActionListener(this);
		add(button, lc);
		
		button = new JButton(">");
		lc.anchor = GridBagConstraints.FIRST_LINE_START;
		lc.gridx = 2;
		lc.gridy = 2;
		button.setActionCommand("keyRight");
		button.setToolTipText("Right");
		button.addActionListener(this);
		add(button, lc);
		
		button = new JButton("v");
		lc.anchor = GridBagConstraints.FIRST_LINE_START;
		lc.gridx = 1;
		lc.gridy = 3;
		button.setActionCommand("keyDown");
		button.setToolTipText("Down");
		button.addActionListener(this);
		add(button, lc);
		
	}
	
	public int readButtons() 
	{
		int newButtons = getButtons();
		int pressed = newButtons & (~curButtonsS);
		curButtonsS = newButtons;
		if (pressed != 0) {
		}
		return newButtons;
	}

	public int getButtons()
	{ // used in TextLCD
		return getButtons(10);
//		int key = 0;
//		while (keyPressed == 0) 
//		{
//			TextLCD.drawString("", 0, 6);
//		}
//		key = keyPressed;
//		return key;
	}
	public int getButtons(int interval)
	{ // used in TextLCD
		int key = 0;
		while (keyPressed == 0)
		{
			TextLCD.drawString("", 0, 6);
		}
		key = keyPressed;
		Delay.msDelay(interval);
		return key;
	}
	public int getBttns()
	{ // used in LCD
		int key = 0;
		while (keyPressed == 0)
		{
			LCD.drawString("", 0, 6);
		}
		key = keyPressed;
		return key;
	}

	public int waitForAnyPress()
	{
		while (keyPressed == 0) 
		{
			Delay.msDelay(10);
		};
		Delay.msDelay(50);
		return 1;
	}

	public int waitForAnyPress(int timeout) {
		long end = (timeout == 0 ? 0x7fffffffffffffffL : System
				.currentTimeMillis() + timeout);
		try {
//			int oldDown = curButtonsE;
			while (true) {
				long curTime = System.currentTimeMillis();
				if (curTime >= end)
					return 0;
				Thread.sleep(50); // POLL_TIME
//				int newDown = curButtonsE = readButtons();
//				int pressed = newDown & (~oldDown);
				if (keyPressed != 0)
					return keyPressed;

//				oldDown = newDown;
			}
		} catch (InterruptedException e) {
			// TODO: Need to decide how to handle this properly
			// preserve state of interrupt flag
			Thread.currentThread().interrupt();
			return 0;
		} finally {
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if ("keyUp".equals(e.getActionCommand()))
		{
			keyPressed = 0x1;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyDown".equals(e.getActionCommand()))
		{
			keyPressed = 0x4;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyLeft".equals(e.getActionCommand()))
		{
			keyPressed = 0x10;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyRight".equals(e.getActionCommand()))
		{
			keyPressed = 0x8;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyEnter".equals(e.getActionCommand()))
		{
			keyPressed = 0x2;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
		if ("keyEscape".equals(e.getActionCommand()))
		{
			keyPressed = 0x20;
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}
			keyPressed = 0;
		}
	}
}
