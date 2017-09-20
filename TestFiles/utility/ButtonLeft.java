package utility;
/**
 * Subclass of JButton for the LEFT key
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonLeft extends JButton implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mouseEntered = false;
	private boolean mousePressed = false;

	public ButtonLeft()
	{
		this.setBounds(45, 58, 42, 31);
		this.setToolTipText("Left key (press ctrl+alt+L)");
		this.setMnemonic(KeyEvent.VK_L);
		addMouseListener(this);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(120, 130, 140));
		GeneralPath shapeLeft = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
		shapeLeft.moveTo(42, 0);
		shapeLeft.lineTo(22, 0);
		shapeLeft.curveTo(0, 0, 0, 30, 22, 30);
		shapeLeft.lineTo(42, 30);
		shapeLeft.closePath();
		g2d.fill(shapeLeft);
		if (mouseEntered)
		{
			g.setColor(new Color(150, 160, 170));
			g2d.draw(shapeLeft);
		}
		if (mousePressed)
		{
			g.setColor(new Color(60, 65, 70));
			g2d.draw(shapeLeft);
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
