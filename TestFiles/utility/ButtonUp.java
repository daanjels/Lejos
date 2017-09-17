package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonUp extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonUp()
	{
		this.setBounds(83, 22, 84, 48);
		this.setToolTipText("Up key (press ctrl+alt+U)");
		this.setMnemonic(KeyEvent.VK_U);
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
	}
}
