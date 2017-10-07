package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Ev3Backdrop extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color shade = new Color(60, 62, 65);
	private Color white = new Color(250, 245, 240);
	private Color dimWhite = new Color(240, 235, 230);
	private Color buttonGray = new Color(120, 130, 140);
	private Color lightGray = new Color(160, 164, 173);
	private Color black = new Color(20, 30, 40);
	private Color red = new Color(205, 50, 0);
	private Color screen = new Color(155, 205, 155, 255);

	public Ev3Backdrop()
	{
		setOpaque(false);
		setBounds(0, 0, 340 + 178*4, 512);	// we need the bounds to position the panel
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(340, 512);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
//		Graphics2D g2d = (Graphics2D) g;
		
		//	draw sidebars shade
		g.setColor(shade);
		g.fillRoundRect(30, 114, 280, 276, 10, 10);
		//	draw sidebars fill
		g.setColor(dimWhite);
		g.fillRoundRect(30, 114, 280, 274, 10, 10);
		//	draw body shade
		g.setColor(shade);
		g.fillRoundRect(44, 30, 252, 427, 10, 10);
		//	draw bottom fill
		g.setColor(buttonGray);
		g.fillRoundRect(45, 410, 250, 45, 10, 10);
		//	draw body fill
		g.setColor(white);
		g.fillRect(45, 250, 250, 165);
		//	draw display black shade
		g.setColor(black);
		g.fillRoundRect(42, 30, 256, 220, 10, 10);
		//	draw display shade
		g.setColor(shade);
		g.fillRoundRect(42, 30, 256, 216, 10, 10);
		//	draw display highlight
		g.setColor(new Color(180, 190, 200));
		g.fillRoundRect(43, 30, 254, 204, 10, 10);
		//	draw display fill
		g.setColor(lightGray);
		g.fillRoundRect(43, 32, 252, 200, 16, 16);
		//	draw display inset
		g.setColor(black);
		g.fillRoundRect(62, 50, 216, 149, 15, 15);
		//	draw display background
		g.setColor(screen);
		g.fillRoundRect(76, 56, 188, 136, 10, 10);

		//	draw connection from display to buttons
		g.setColor(black);
		g.fillRect(166, 195, 8, 71);
		//	draw EV3 logo
		g.setColor(dimWhite);
		g.setFont(new Font("Eurostile", Font.BOLD, 28));
		g.drawString("EV3", 58, 443);
		//	draw lego logo
		g.setColor(red);
		g.fillRect(255, 420, 26, 26);
		g.setColor(black);
		g.drawRect(255, 420, 26, 26);
		g.setColor(white);
		g.setFont(new Font("Arial Narrow", Font.BOLD, 10));
		g.drawString("LEGO", 257, 437);

		//	draw LED zone
//		g.setColor(black);
//		int xLed[] = {150, 190, 225, 225, 190, 150, 115, 115};
//		int yLed[] = {266, 266, 300, 346, 380, 380, 346, 300};
//		GeneralPath shapeLed = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
//				xLed.length);
//		shapeLed.moveTo(xLed[0], yLed[0]);
//		for (int index = 1; index < xLed.length; index++)
//		{
//			shapeLed.lineTo(xLed[index], yLed[index]);
//		};
//		shapeLed.closePath();
//		g2d.fill(shapeLed);
//		g.fillRoundRect(90, 304, 160, 38, 38, 38);

//		//	draw ESCAPE button
//		g.setColor(buttonGray);
//		GeneralPath shapeEscape = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 6);
//		shapeEscape.moveTo( 60, 249);
//		shapeEscape.lineTo(120, 249);
//		shapeEscape.lineTo(120, 263);
//		shapeEscape.lineTo(105, 278);
//		shapeEscape.lineTo( 66, 278);
//		shapeEscape.lineTo( 60, 272);
//		shapeEscape.closePath();
//		g2d.fill(shapeEscape);
//		g.setColor(black);
//		g2d.draw(shapeEscape);
//		
//		//	draw UP button
//		g.setColor(buttonGray);
//		int xUp[] = {151, 189, 212, 202, 202, 192, 192, 190, 150, 148, 148, 138, 138, 128};
//		int yUp[] = {272, 272, 294, 304, 320, 320, 306, 302, 302, 306, 320, 320, 304, 294};
//		GeneralPath shapeUp = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
//				xUp.length);
//		shapeUp.moveTo(xUp[0], yUp[0]);
//		for (int index = 1; index < xUp.length; index++)
//		{
//			shapeUp.lineTo(xUp[index], yUp[index]);
//		};
//		shapeUp.closePath();
//		g2d.fill(shapeUp);
//		
//		//	draw LEFT button
//		g.setColor(buttonGray);
//		GeneralPath shapeLeft = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
//		shapeLeft.moveTo(132, 308);
//		shapeLeft.lineTo(112, 308);
//		shapeLeft.curveTo(90, 306, 90, 338, 112, 338);
//		shapeLeft.lineTo(132, 338);
//		shapeLeft.closePath();
//		g2d.fill(shapeLeft);
//		
//		//	draw ENTER button
//		g.setColor(new Color(70, 80, 90));
//		g.fillRoundRect(154, 306, 32, 32, 10, 10);
//		
//		//	draw RIGHT button
//		g.setColor(buttonGray);
//		GeneralPath shapeRight = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 4);
//		shapeRight.moveTo(208, 308);
//		shapeRight.lineTo(228, 308);
//		shapeRight.curveTo(250, 306, 250, 338, 228, 338);
//		shapeRight.lineTo(208, 338);
//		shapeRight.closePath();
//		g2d.fill(shapeRight);
//		
//		//	draw DOWN button
//		g.setColor(buttonGray);
//		int xDown[] = {151, 189, 212, 202, 202, 192, 192, 190, 150, 148, 148, 138, 138, 128};
//		int yDown[] = {373, 373, 351, 341, 325, 325, 339, 343, 343, 339, 325, 325, 341, 351};
//		GeneralPath shapeDown = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
//				xDown.length);
//		shapeDown.moveTo(xDown[0], yDown[0]);
//		for (int index = 1; index < xDown.length; index++)
//		{
//			shapeDown.lineTo(xDown[index], yDown[index]);
//		};
//		shapeDown.closePath();
//		g2d.fill(shapeDown);
	}
}
