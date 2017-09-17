package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class ButtonEnter extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonEnter()
	{
		this.setBounds(109, 56, 32, 32);
		this.setToolTipText("Enter key (press ctrl+alt+E)");
		this.setMnemonic(KeyEvent.VK_E);
	}
	public void paint(Graphics g)
	{
		g.setColor(new Color(70, 80, 90));
		g.fillRoundRect(0, 0, 32, 32, 10, 10);
	}
}
