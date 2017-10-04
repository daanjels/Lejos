package performance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Robot 
{
	private String name;
	private int wheelDiameter;
	private int wheelBase;
	private int wheelDrift;
	private int wheelReverse; // 0:both forward / 1:right forward / 2:left forward / 3:both backward
	private int linearSpeed;
	private int angularSpeed;
	private int acceleration;
	static Keys buttons = BrickFinder.getDefault().getKeys();
	
	public Robot() 
	{
		this.name = "EV3";
		this.wheelDiameter = 4240;
		this.wheelBase = 8300;
		this.wheelDrift = 1000;
		this.wheelReverse = 0;
		this.linearSpeed = 10000;
		this.angularSpeed = 6000;
		this.acceleration = 3000;
	}
	
	public String getName()
	{
		return this.name;
	}
	public double getWheelDiameter() 
	{
		double diameter = (double)wheelDiameter / 100;
		return diameter;
	}
	public double getWheelRight() 
	{
		double wheelRight = (double)wheelDiameter;
		double drift = (double)wheelDrift / 1000;
		wheelRight = wheelRight * 2 / (1/drift + 1);
		wheelRight = wheelRight / 1000;
		return wheelRight;
	}
	public double getWheelLeft() 
	{
		double wheelLeft = (double)wheelDiameter;
		double drift = (double)wheelDrift / 1000;
		wheelLeft = wheelLeft * 2 / (drift + 1);
		wheelLeft = wheelLeft / 1000;
		return wheelLeft;
	}
	public double getWheelBase() 
	{
		double base = (double)wheelBase / 100;
		return base;
	}
	public double getWheelOffset() 
	{
		double wheelOffset = getWheelBase() / 20;
		return wheelOffset;
	}
	public double getWheelDrift() 
	{
		double drift= (double)wheelDrift / 1000;
		return drift;
	}
	public boolean getReverseRight() 
	{
		boolean reverseRight = false;
		int power = this.wheelReverse;
		if (power == 2 || power == 3) reverseRight = true;
		return reverseRight;
	}
	public boolean getReverseLeft() 
	{
		boolean reverseLeft = false;
		int power = this.wheelReverse;
		if (power == 1 || power == 3) reverseLeft = true;
		return reverseLeft;
	}
	public double getLinearSpeed() 
	{
		double speed = (double)linearSpeed / 1000;
		return speed;
	}
	public double getAngularSpeed() 
	{
		double speed = (double)angularSpeed / 100;
		return speed;
	}
	public double getAcceleration() 
	{
		double accel = (double)acceleration / 1000;
		return accel;
	}
	private String showWheelDiameter()
	{
		return String.format(Locale.CANADA, "%1$,.2f mm", getWheelDiameter());
	}
	private String showWheelBase()
	{
		return String.format(Locale.CANADA, "%1$,.2f mm", getWheelBase());
	}
	private String showWheelDrift()
	{
		return String.format(Locale.CANADA, "%1$,.3f", getWheelDrift());
	}
	private String showLinearSpeed()
	{
		return String.format(Locale.CANADA, "%1$,.1f cm/s", getLinearSpeed());
	}
	private String showAngularSpeed()
	{
		return String.format(Locale.CANADA, "%1$,.1f d/s", getAngularSpeed());
	}
	private String showAcceleration()
	{
		return String.format(Locale.CANADA, "%1$,.1f cm/s/s", getAcceleration());
	}

	public void showProperties() 
	{
		LCD.clear();
		LCD.drawString("Name: " + this.name, 0, 0);
		LCD.drawString(" Wheel:   ", 0, 1);
		LCD.drawString(" Base:    ", 0, 2);
		LCD.drawString(" Drift:   ", 0, 3);
		LCD.drawString(" Linear:  ", 0, 4);
		LCD.drawString(" Angular: ", 0, 5);
		LCD.drawString(" Accel.:  ", 0, 6);

		LCD.drawString(showWheelDiameter(), 9, 1);
		LCD.drawString(showWheelBase(), 9, 2);
		LCD.drawString(showWheelDrift(), 9, 3);
		LCD.drawString(showLinearSpeed(), 9, 4);
		LCD.drawString(showAngularSpeed(), 9, 5);
		LCD.drawString(showAcceleration(), 9, 6);
		return;
	}
	
	private void showProperty(int i) 
	{
		switch (i) 
		{
			case 0: 
				LCD.drawString("         ", 9, 1);
				LCD.drawString(showWheelDiameter(), 9, 1);
				break;
			case 1: 
				LCD.drawString("         ", 9, 2);
				LCD.drawString(showWheelBase(), 9, 2);
				break;
			case 2: 
				LCD.drawString("         ", 9, 3);
				LCD.drawString(showWheelDrift(), 9, 3);
				break;
			case 3: 
				LCD.drawString("         ", 9, 4);
				LCD.drawString(showLinearSpeed(), 9, 4);
				break;
			case 4: 
				LCD.drawString("         ", 9, 5);
				LCD.drawString(showAngularSpeed(), 9, 5);
				break;
			case 5: 
				LCD.drawString("         ", 9, 6);
				LCD.drawString(showAcceleration(), 9, 6);
				break;
		}
	}

	private int[] getProperties() 
	{
		int[] props = new int[6];
		props[0] = this.wheelDiameter;
		props[1] = this.wheelBase;
		props[2] = this.wheelDrift;
		props[3] = this.linearSpeed;
		props[4] = this.angularSpeed;
		props[5] = this.acceleration;
		return props;
	}

	private void setProperties(int[] props) 
	{
		this.wheelDiameter = props[0];
		this.wheelBase = props[1];
		this.wheelDrift = props[2];
		this.linearSpeed = props[3];
		this.angularSpeed = props[4];
		this.acceleration = props[5];
	}

	public boolean editProperties() 
	{
		boolean isDirty = false;
		int choice = 0;
		int[] oldProps = getProperties();
		int[] properties = getProperties();
		int[] increments = {5, 5, 1, 500, 500, 100};
		showProperties();
//		Delay.msDelay(500);
//		while (buttons.getButtons() != Keys.ID_ESCAPE) {
//			LCD.drawString(">", 0, choice+1);
//			if (buttons.getButtons() == Keys.ID_UP) {
//				setProperties(properties);
//				showProperty(choice);
//				LCD.drawString(" ", 0, choice+1);
//				choice--;
//				if (choice < 0) choice = 5;
//				LCD.drawString(">", 0, choice+1);
//			} else if (buttons.getButtons() == Keys.ID_DOWN) {
//				setProperties(properties);
//				showProperty(choice);
//				LCD.drawString(" ", 0, choice+1);
//				choice++;
//				if (choice > 5) choice = 0;
//				LCD.drawString(">", 0, choice+1);
//			} else if (buttons.getButtons() == Keys.ID_LEFT) {
//				properties[choice] = properties[choice] - increments[choice];
//				setProperties(properties);
//				showProperty(choice);
//			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
//				properties[choice] = properties[choice] + increments[choice];
//				setProperties(properties);
//				showProperty(choice);
//			} else if (buttons.getButtons() == Keys.ID_ENTER) {
//				setProperties(properties);
//				Delay.msDelay(200);
//				return;
//			}
//			Delay.msDelay(200);
//		}
//		Delay.msDelay(200);
//		setProperties(oldProps);
//		return;
		while(true) // this construct works better than what I did before
		{
			int button;
			LCD.drawString(">", 0, choice+1);
			do
			{
				button = buttons.waitForAnyPress();
			}
			while (button == 0);
			LCD.drawString(">", 0, choice+1);
			if (button == Keys.ID_UP)
			{
				setProperties(properties);
				showProperty(choice);
				LCD.drawString(" ", 0, choice+1);
				choice--;
				if (choice < 0) choice = 5;
				LCD.drawString(">", 0, choice+1);
			}
			if (button == Keys.ID_DOWN)
			{
				setProperties(properties);
				showProperty(choice);
				LCD.drawString(" ", 0, choice+1);
				choice++;
				if (choice > 5) choice = 0;
				LCD.drawString(">", 0, choice+1);
			}
			if (button == Keys.ID_LEFT)
			{
				isDirty = true;
				properties[choice] = properties[choice] - increments[choice];
				setProperties(properties);
				showProperty(choice);
			}
			if (button == Keys.ID_RIGHT)
			{
				isDirty = true;
				properties[choice] = properties[choice] + increments[choice];
				setProperties(properties);
				showProperty(choice);
			}
			if (button == Keys.ID_ENTER) 
			{
				setProperties(properties);
				Delay.msDelay(100);
				return isDirty;
			}
			if(button == Keys.ID_ESCAPE)
			{
				if (isDirty) 
				{
					LCD.clear();
					LCD.drawString("Back to old", 0, 2);
					LCD.drawString("settings", 0, 3);
					Delay.msDelay(1000);
					setProperties(oldProps);
				}
				return isDirty;
			}
		}
	}

	public void storeSettings() 
	{
		LCD.clear();
		LCD.drawString("Store settings", 0, 1);
		try {
			PrintWriter writer = new PrintWriter("/home/lejos/programs/" + this.name + ".txt", "UTF-8");
			writer.println("Robot Settings");
			writer.println("Robot Name: " + this.name);
			writer.println("Wheel diameter: " + this.wheelDiameter);
			writer.println("Wheel base: " + this.wheelBase);
			writer.println("Wheel drift: " + this.wheelDrift);
			writer.println("Wheel direction: " + this.wheelReverse); // 0 forward / 1 right reverse / 2 left reverse / 3 both reverse
			writer.println("Linear speed: " + this.linearSpeed);
			writer.println("Angular speed: " + this.angularSpeed);
			writer.println("Default acceleration: " + this.acceleration);
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}

	public void loadSettings(String botName) throws FileNotFoundException {
		String line;
		String[] values;
		Scanner in = new Scanner(new File("/home/lejos/programs/" + botName + ".txt"));
		line = in.nextLine();	// "New robot"
		line = in.nextLine();
		values = line.split(" ");
		this.setName(values[2]);
		line = in.nextLine();
		values = line.split(" ");
		this.setWheelDiameter(values[2]);
		line = in.nextLine();
		values = line.split(" ");
		this.setWheelBase(values[2]);
		line = in.nextLine();
		values = line.split(" ");
		this.setWheelDrift(values[2]);
		line = in.nextLine();
		values = line.split(" ");
		this.setWheelReverse(values[2]) ;// wheel direction
		line = in.nextLine();
		values = line.split(" ");
		this.setLinearSpeed(values[2]);
		line = in.nextLine();
		values = line.split(" ");
		this.setAngularSpeed(values[2]);
		line = in.nextLine();
		values = line.split(" ");
		this.setAcceleration(values[2]);
		
		in.close();
	}

	public void setName(String robotName) {
		this.name = robotName;
	}
	public void setWheelDiameter(String string) {
		int diameter = Integer.parseInt(string);
		this.wheelDiameter = diameter;
	}
	public void setWheelBase(String string) {
		int base = Integer.parseInt(string);
		this.wheelBase = base;
	}
	void setWheelDrift(String string) {
		int drift = Integer.parseInt(string);
		this.wheelDrift = drift;
	}
	private void setWheelReverse(String string) {
		int reverse = Integer.parseInt(string);
		this.wheelReverse = reverse;
	}

	private void setLinearSpeed(String string) {
		int speed = Integer.parseInt(string);
		this.linearSpeed = speed;
	}
	private void setAngularSpeed(String string) {
		int speed = Integer.parseInt(string);
		this.angularSpeed = speed;
	}
	private void setAcceleration(String string) {
		int accel = Integer.parseInt(string);
		this.acceleration = accel;
	}

}