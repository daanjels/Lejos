package utility;
/**
 * Subclass of JButton for the RIGHT key
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonRight extends JButton implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mouseEntered = false;
	private boolean mousePressed = false;

	public ButtonRight()
	{
		this.setBounds(163, 58, 42, 31);
		this.setToolTipText("Right key (press ctrl+alt+R)");
		this.setMnemonic(KeyEvent.VK_R);
		addMouseListener(this);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(120, 130, 140));
		GeneralPath shapeRight = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
		shapeRight.moveTo(0, 0);
		shapeRight.lineTo(20, 0);
		shapeRight.curveTo(42, 0, 42, 30, 20, 30);
		shapeRight.lineTo(0, 30);
		shapeRight.closePath();
		g2d.fill(shapeRight);
		if (mouseEntered)
		{
			g.setColor(new Color(150, 160, 170));
			g2d.draw(shapeRight);
		}
		if (mousePressed)
		{
			g.setColor(new Color(60, 65, 70));
			g2d.draw(shapeRight);
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
