package utility;
/**
 * Subclass of JButton for the ESCAPE key
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonEscape extends JButton implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mouseEntered = false;
	private boolean mousePressed = false;

	public ButtonEscape()
	{
		this.setBounds(15, 0, 61, 30);
		this.setToolTipText("Escape (press alt+Q)");
		this.setMnemonic(KeyEvent.VK_Q);
		addMouseListener(this);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(120, 130, 140));
		GeneralPath shapeEscape = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 6);
		shapeEscape.moveTo( 0, 0);
		shapeEscape.lineTo(60, 0);
		shapeEscape.lineTo(60, 14);
		shapeEscape.lineTo(45, 29);
		shapeEscape.lineTo( 6, 29);
		shapeEscape.lineTo( 0, 27);
		shapeEscape.closePath();
		g2d.fill(shapeEscape);
		g.setColor(Color.BLACK);
		g2d.draw(shapeEscape);
		if (mouseEntered)
		{
			g.setColor(new Color(150, 160, 170));
			g2d.draw(shapeEscape);
		}
		if (mousePressed)
		{
			g.setColor(new Color(60, 65, 70));
			g2d.draw(shapeEscape);
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
