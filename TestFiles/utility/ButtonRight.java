package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonRight extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonRight()
	{
		this.setBounds(163, 58, 42, 30);
		this.setToolTipText("Right key (press ctrl+alt+R)");
		this.setMnemonic(KeyEvent.VK_R);
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
	}
}
