package utility;
/**
 * Subclass of JButton for the ENTER key
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class ButtonEnter extends JButton implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mouseEntered = false;
	private boolean mousePressed = false;

	public ButtonEnter()
	{
		this.setBounds(109, 56, 33, 33);
		this.setToolTipText("Enter key (press ctrl+alt+E)");
		this.setMnemonic(KeyEvent.VK_E);
		addMouseListener(this);
	}
	public void paint(Graphics g)
	{
		g.setColor(new Color(70, 80, 90));
		g.fillRoundRect(0, 0, 32, 32, 10, 10);
		if (mouseEntered)
		{
			g.setColor(new Color(105, 120, 135));
			g.drawRoundRect(0, 0, 32, 32, 10, 10);
		}
		if (mousePressed)
		{
			g.setColor(new Color(35, 40, 45));
			g.drawRoundRect(0, 0, 32, 32, 10, 10);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEntered = true;
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered = false;
		repaint();
	}
}
