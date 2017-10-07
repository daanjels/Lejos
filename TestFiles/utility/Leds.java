package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Leds extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 284071073788318947L;
	public static int COLOR_NONE = 0;
	public static int COLOR_GREEN = 1;
	public static int COLOR_RED = 2;
	public static int COLOR_ORANGE = 3;
	public static int PATTERN_ON = 0;
	public static int PATTERN_BLINK = 1;
	public static int PATTERN_HEARTBEAT = 2;

	private Color white = new Color(250, 245, 240);
	private Color LED_NONE = new Color(0, 30, 9);
	private Image LED;
	private Color LED_RED = new Color(200, 20, 0);
	private Color LED_GREEN = new Color(0, 120, 40);
	private Color LED_ORANGE = new Color(160, 80, 10);
	
	public Leds() 
	{
		this.setBounds(90, 266, 160, 114);
		this.setBackground(white);
		this.setOpaque(true);
		LED = createLed(COLOR_NONE);
	}
	
	public void setPattern(int pattern)
	{
		int kleur = COLOR_NONE;
		if (pattern != 0) 
		{
			kleur = pattern % 3;
			System.out.println("Calculated color: " + kleur);
			pattern = pattern / 3;
			if (kleur == 0) 
			{
				kleur = 3;
				pattern = pattern - 1;
			}
			System.out.println("Calculated pattern: " + pattern);
		}
		if (pattern == PATTERN_ON)
		{
			LED = createLed(kleur);
			this.repaint();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LED = createLed(COLOR_NONE);
			this.repaint();
		}
		if (pattern == PATTERN_BLINK)
		{
			for (int i = 0; i < 5; i++)
			{
				LED = createLed(kleur);
				this.repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LED = createLed(COLOR_NONE);
				this.repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (pattern == PATTERN_HEARTBEAT)
		{
			for (int i = 0; i < 5; i++)
			{
				LED = createLed(kleur);
				this.repaint();
				try {
					Thread.sleep(350);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LED = createLed(COLOR_NONE);
				this.repaint();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LED = createLed(kleur);
				this.repaint();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LED = createLed(COLOR_NONE);
				this.repaint();
				try {
					Thread.sleep(850);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setPattern(int color, int pattern)
	{
		if (color == COLOR_NONE)
		{
			setPattern(0);
		}
		else
		{
			setPattern(pattern * 3 + color);
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(LED, 0, 0, this);
	}
	
	private Image createLed(int kleur)
	{
		Color colour = LED_NONE;
		BufferedImage lights = new BufferedImage(160, 114, BufferedImage.TYPE_INT_RGB);
		Graphics g = lights.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(white);
		g.fillRect(0, 0, 160, 114);
		g.setColor(colour);
		int xLed[] = {60, 100, 135, 135, 100, 60, 25, 25};
		int yLed[] = {0, 0, 34, 80, 114, 114, 80, 34};
		GeneralPath shapeLed = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				xLed.length);
		shapeLed.moveTo(xLed[0], yLed[0]);
		for (int index = 1; index < xLed.length; index++)
		{
			shapeLed.lineTo(xLed[index], yLed[index]);
		};
		shapeLed.closePath();
		g2d.fill(shapeLed);
		g.fillRoundRect(0, 37, 160, 38, 38, 38);

		if (kleur == COLOR_GREEN) colour = LED_GREEN;
		if (kleur == COLOR_RED) colour = LED_RED;
		if (kleur == COLOR_ORANGE) colour = LED_ORANGE;
		if(kleur != COLOR_NONE)
		{
			Color color = colour.darker();
			color = color.darker();
			color = color.darker();
			color = color.darker();
			g.setColor(color);
			g.fillRoundRect(30, 17, 96, 80, 40, 40);
			color = color.brighter();
			g.setColor(color);
			g.fillRoundRect(32, 19, 92, 76, 35, 35);
			color = color.brighter();
			g.setColor(color);
			g.fillRoundRect(34, 21, 88, 72, 30, 30);
			color = color.brighter();
			g.setColor(color);
			g.fillRoundRect(36, 23, 84, 68, 26, 26);
			
			g.setColor(colour);
			g.fillOval(40, 27, 80, 60);
			colour = colour.brighter();
			g.setColor(colour);
			g.fillOval(45, 32, 70, 50);
			colour = colour.brighter();
			g.setColor(colour);
			g.fillOval(50, 37, 60, 40);
			colour = colour.brighter();
			g.setColor(colour);
			g.fillOval(55, 42, 20, 30);
			g.fillOval(85, 42, 20, 30);
			colour = colour.brighter();
			colour = colour.brighter();
			g.setColor(colour);
			g.fillRoundRect(58, 47, 10, 20, 10, 10);
			g.fillRoundRect(92, 47, 10, 20, 10, 10);
		}
		return lights;
	}

}
