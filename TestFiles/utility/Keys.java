package utility;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
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
//		setPreferredSize(new Dimension(178, 128));
//		setBackground(new Color(255, 230, 205));
		this.setOpaque(false);
		setBounds(45, 245, 250, 165);

		JButton button;
		JLayeredPane layer = new JLayeredPane();
		layer.setPreferredSize(new Dimension(250, 200));

		button = new ButtonEscape();
		button.setActionCommand("keyEscape");
		button.addActionListener(this);
		layer.add(button, new Integer(10));
		
		button = new ButtonUp();
		button.setActionCommand("keyUp");
		button.addActionListener(this);
		layer.add(button, new Integer(11));
		
		button = new ButtonLeft();
		button.setActionCommand("keyLeft");
		button.addActionListener(this);
		layer.add(button, new Integer(13));
		
		button = new ButtonEnter();
		button.setActionCommand("keyEnter");
		button.addActionListener(this);
		layer.add(button, new Integer(15));
		
		button = new ButtonRight();
		button.setActionCommand("keyRight");
		button.addActionListener(this);
		layer.add(button, new Integer(14));
		
		button = new ButtonDown();
		button.setActionCommand("keyDown");
		button.addActionListener(this);
		layer.add(button, new Integer(12));
		
		add(layer);
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
			System.out.print("");
		}
		System.out.print(keyPressed);
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
		int key = 0;
		while (keyPressed == 0) 
		{
			System.out.print("");
//			Delay.msDelay(10);
		};
		System.out.print("wait: " + keyPressed);
		key = keyPressed;
		Delay.msDelay(10);
		return key;
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
			System.out.println("UP");
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
			System.out.println("DOWN");
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
			System.out.println("LEFT");
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
			System.out.println("RIGHT");
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
			System.out.println("ENTER");
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
			System.out.println("ESCAPE");
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
