package utility;
/**
 * Subclass of JButton for the DOWN key
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonDown extends JButton implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mouseEntered = false;
	private boolean mousePressed = false;

	public ButtonDown()
	{
		this.setBounds(83, 75, 84, 49);
		this.setToolTipText("Down key (press alt+D)");
		this.setMnemonic(KeyEvent.VK_D);
		addMouseListener(this);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(120, 130, 140));
		int xDown[] = { 0, 23, 61, 84, 74, 74, 64, 64, 62, 22, 20, 20, 10, 10};
		int yDown[] = {26, 48, 48, 26, 16,  0,  0, 14, 18, 18, 14,  0,  0, 16};
		GeneralPath shapeDown = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				xDown.length);
		shapeDown.moveTo(xDown[0], yDown[0]);
		for (int index = 1; index < xDown.length; index++)
		{
			shapeDown.lineTo(xDown[index], yDown[index]);
		};
		shapeDown.closePath();
		g2d.fill(shapeDown);
		if (mouseEntered)
		{
			g.setColor(new Color(150, 160, 170));
			g2d.draw(shapeDown);
		}
		if (mousePressed)
		{
			g.setColor(new Color(60, 65, 70));
			g2d.draw(shapeDown);
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
