package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonDown extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonDown()
	{
		this.setBounds(83, 75, 84, 48);
		this.setToolTipText("Down key (press ctrl+alt+D)");
		this.setMnemonic(KeyEvent.VK_D);
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
	}
}
