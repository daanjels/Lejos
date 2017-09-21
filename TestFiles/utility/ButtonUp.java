package utility;
/**
 * Subclass of JButton for the UP key
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonUp extends JButton implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mouseEntered = false;
	private boolean mousePressed = false;

	public ButtonUp()
	{
		this.setBounds(83, 22, 84, 49);
		this.setToolTipText("Up key (press alt+U)");
		this.setMnemonic(KeyEvent.VK_U);
		addMouseListener(this);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(120, 130, 140));
		int xUp[] = { 0, 23, 61, 84, 74, 74, 64, 64, 62, 22, 20, 20, 10, 10};
		int yUp[] = {22,  0,  0, 22, 32, 48, 48, 32, 30, 30, 34, 48, 48, 32};
		GeneralPath shapeUp = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				xUp.length);
		shapeUp.moveTo(xUp[0], yUp[0]);
		for (int index = 1; index < xUp.length; index++)
		{
			shapeUp.lineTo(xUp[index], yUp[index]);
		};
		shapeUp.closePath();
		g2d.fill(shapeUp);
		if (mouseEntered)
		{
			g.setColor(new Color(150, 160, 170));
			g2d.draw(shapeUp);
		}
		if (mousePressed)
		{
			g.setColor(new Color(60, 65, 70));
			g2d.draw(shapeUp);
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
