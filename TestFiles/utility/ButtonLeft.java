package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonLeft extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonLeft()
	{
		this.setBounds(45, 58, 42, 30);
		this.setToolTipText("Left key (press ctrl+alt+L)");
		this.setMnemonic(KeyEvent.VK_L);
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
	}
}
