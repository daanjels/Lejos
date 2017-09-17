package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class ButtonEscape extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonEscape()
	{
		this.setBounds(15, 0, 60, 30);
		this.setToolTipText("Escape (press ctrl+alt+Q)");
		this.setMnemonic(KeyEvent.VK_Q);
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
	}
}
