package callibrate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import utility.Delay;
import utility.Brick;
import utility.BrickFinder;
import utility.Keys;
import utility.Leds;
import utility.TextLCD;
import utility.TextMenu;

public class Callibration 
{
	private static final String DIR = "home/lejos/wheels/";
	static Robbot bot = new Robbot();
	static int option = 1, choice = 0;
	static List<String> botNames = new ArrayList<String>();
	static Keys buttons = Brick.getKeys();
	static Leds leds = Brick.getLed();
	static TextLCD lcd = Brick.getTextLCD();
	private static boolean dirty;

	public static void main(String[] args) 
	{
		Brick callE = BrickFinder.getDefault();
		callE.setVisible(true);
		
		startUp(); // show a startup dialog (if no database available, create a database and add 'new robot')
		selectBot(); // select an exisiting bot (if only 'new robot' create it)
		shutDown();
	}

	private static void shutDown() 
	{
		lcd.clear();
		lcd.drawString("Shutting down", 0, 1);
		lcd.drawString("Calibration.", 0, 2);
		lcd.screenWait(1000, 4);
		buttons.waitForAnyPress(2000);
		System.exit(0);
	}

	private static void startUp() 
	{
		lcd.clear();
		lcd.drawString("Starting up", 0, 1);
		lcd.drawString("Calibration.", 0, 2);
		lcd.screenWait(1000, 4);
		new File(DIR).mkdirs();

		try 
		{
			botNames = loadBots();
		} 
		catch (IOException e) 
		{
			noDatabase();
			return;
		}
	}

	private static void noDatabase() 
	{
		lcd.clear();
		lcd.drawString("Could not locate ", 0, 0);
		lcd.drawString("the database.", 0, 1);
		lcd.drawString("A new database", 0, 2);
		lcd.drawString("will be created.", 0, 3);
		lcd.drawString("ENTER > continue...", 0, 5);
		lcd.drawString("ESCAPE > quit", 0, 6);
		int button = buttons.waitForAnyPress();
		if (button == Keys.ID_ESCAPE) shutDown();
		botNames.add("New robot"); // the first robot in the list is the option to create a new robot
		return;
	}

	private static void createBot() 
	{
		String robotName = lcd.inputName();
		if (robotName == null) 
		{
			selectBot();
			shutDown();
		}
		while (botNames.contains(robotName)) 
		{
			lcd.clear();
			lcd.drawString("This name already", 0, 1);
			lcd.drawString("exists. Please ", 0, 2);
			lcd.drawString("pick another one.", 0, 3);
			buttons.waitForAnyPress(2000);
			robotName = lcd.inputName();
		}
		if (robotName == null) 
		{
			selectBot();
			shutDown();
		}
		lcd.drawString("Building robot", 0, 5);
		lcd.drawString("with default", 0, 6);
		lcd.drawString("settings.", 0, 7);
		bot = new Robbot();
		bot.setName(robotName);
		addBot(robotName);
		bot.storeSettings();
		buttons.waitForAnyPress(2000);
		dirty = bot.editProperties();
		System.out.println(dirty);
		return;
	}

	private static void addBot(String robotName) 
	{
		botNames.add(robotName);
		storeBots();
	}

	private static void storeBots()
	{
		BufferedWriter writer = null;
		FileWriter fileWriter = null;
		try 
		{
			File file = new File(DIR + "robotbase");
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			fileWriter = new FileWriter(file, false);
			writer = new BufferedWriter(fileWriter);
			for (int i = 0; i < botNames.size(); i++)
			{
				writer.write(botNames.get(i) + "\n");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (writer != null) writer.close();
				if (fileWriter != null) fileWriter.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

	private static List<String> loadBots() throws IOException 
	{
		FileReader fileReader = new FileReader(new File(DIR + "robotbase"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) 
		{
			lines.add(line);
		}
		bufferedReader.close();
		return lines;
	}

	private static void selectBot() 
	{
		if (botNames.size() < 2) 
		{
			createBot();
			showMenu();
			return;
		}
		String[] names = new String[botNames.size()];
		for (int i = 0; i < botNames.size(); i++)
		{
			names[i] = String.valueOf(botNames.get(i));
		}
		TextMenu bots = new TextMenu(names, 1, "Choose a robot");
		option = bots.select(option);
		lcd.clear();
		Delay.msDelay(100);
		if (option > 200)
		{
			option = option - 200;
			lcd.drawString("Do you want to", 0, 1);
			lcd.drawString("rename " + names[option] + "?", 0, 2);
			lcd.drawString("ENTER > yes", 0, 5);
			lcd.drawString("ESCAPE > no", 0, 6);
			int button = buttons.waitForAnyPress();
			if (button == Keys.ID_ENTER)
			{
				String oldName = names[option];
				botNames.remove(option);
				lcd.clear();
				lcd.drawString("Give a new name:", 0, 3);
				String newName = lcd.inputString(true, oldName, 4);
				if (newName != null)
				{
					lcd.drawString("> " + newName + ".", 0, 5);
					Delay.msDelay(2000);
				}
				while (botNames.contains(newName)) {
					lcd.clear();
					lcd.drawString("This name already", 0, 1);
					lcd.drawString("exists. Please ", 0, 2);
					lcd.drawString("pick another one.", 0, 3);
					buttons.waitForAnyPress(3000);
					newName = lcd.inputName();
				}
				if (newName == null) return;
				System.out.println(DIR + oldName + ".txt");
				File f = new File(DIR + oldName + ".txt");
				f.delete();
//				Need to remove the original settingsfile
				addBot(newName);
				try
				{
					bot.loadSettings(oldName);
				}
				catch (FileNotFoundException e) 
				{
					
				}
				bot.setName(newName);
				bot.storeSettings();
				lcd.drawString(names[option] + " will be renamed", 0, 3);
				Delay.msDelay(1000);
			}
			selectBot();
		}
		if (option > 100)
		{
			option = option - 100;
			lcd.drawString("Do you want to", 0, 1);
			lcd.drawString("remove " + names[option] + "?", 0, 2);
			lcd.drawString("ENTER > yes", 0, 4);
			lcd.drawString("ESCAPE > no", 0, 5);
			int button = buttons.waitForAnyPress();
			if (button == Keys.ID_ENTER)
			{
				botNames.remove(option);
				System.out.println(DIR + names[option] + ".txt");
				File f = new File(DIR + names[option] + ".txt");
				f.delete();
//				Need to remove the settingsfile
				storeBots();
				lcd.drawString(names[option] + " is removed", 0, 3);
				Delay.msDelay(1000);
			}
			selectBot();
		}
		if (option > 0) 
		{
			try 
			{
				bot.loadSettings(names[option]);
				choice = 0;
				showMenu();
				return;
			} 
			catch (FileNotFoundException e) 
			{
				lcd.clear();
				lcd.drawString("No settings found", 0, 1);
				lcd.drawString("for " + names[option] + ".", 0, 2);
				lcd.drawString("Using default", 0, 3);
				lcd.drawString("settings.", 0, 4);
				buttons.waitForAnyPress(5000);
				bot = new Robbot();
				bot.setName(names[option]);
				bot.storeSettings();
				showMenu();
				return;
			}
		}
		if (option < 0) 
		{
			return;
		}
		createBot();
		showMenu();
		return;
	}

	private static void showMenu() {
		String[] mainOptions = {"Calibrate wheels", "Calibrate base", "Calibrate drift", "Edit settings", "Store settings", "Main menu"};
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "*EV3 calibration*");
		
		while (true) 
		{
			choice = mainMenu.select(choice);
			switch (choice) 
			{
				case 0:
					testTravel();
					Delay.msDelay(100);
					break;
				case 1:
					testRotate();
					Delay.msDelay(100);
					break;
				case 2:
					testDrift();
					Delay.msDelay(100);
					break;
				case 3:
					if (dirty) 
					{
						bot.editProperties();
					}
					else
					{
						dirty = bot.editProperties();
					}
					break;
				case 4:
					bot.storeSettings();
					dirty = false;
					lcd.clear();
					lcd.drawString("Saving settings", 0, 1);
					lcd.drawString("for " + bot.getName(), 0, 2);
					lcd.screenWait(200, 4);
					Delay.msDelay(500);
					break;
				case 5:
					if (dirty) 
					{
						lcd.clear();
						lcd.drawString("Settings have ", 0, 0);
						lcd.drawString("changed", 0, 1);
						lcd.drawString("LEFT -> discard", 0, 2);
						lcd.drawString("RIGHT -> save", 0, 3);
						int button = buttons.waitForAnyPress();
						dirty = false;
						if (button == Keys.ID_RIGHT) 
						{
							bot.storeSettings();
							dirty = false;
							lcd.drawString("Saving settings", 0, 5);
							lcd.drawString("for " + bot.getName(), 0, 6);
							lcd.screenWait(200, 7);
							Delay.msDelay(500);
						}
						if (button == Keys.ID_ESCAPE) 
						{
							break;
						}
					}
					selectBot();
					return;
				case -1:
					if (dirty) 
					{
						lcd.clear();
						lcd.drawString("Settings have ", 0, 0);
						lcd.drawString("changed", 0, 1);
						lcd.drawString("LEFT -> discard", 0, 2);
						lcd.drawString("RIGHT -> save", 0, 3);
						int button = buttons.waitForAnyPress();
						dirty = false;
						if (button == Keys.ID_RIGHT) 
						{
							bot.storeSettings();
							dirty = false;
							lcd.drawString("Saving settings", 0, 5);
							lcd.drawString("for " + bot.getName(), 0, 6);
							lcd.screenWait(200, 7);
							Delay.msDelay(500);
						}
						if (button == Keys.ID_ESCAPE) 
						{
							break;
						}
					}
					selectBot();
					return;
			}
			Delay.msDelay(50);
		}
	}

	private static void testTravel() 
	{
		lcd.clear();
		lcd.drawString("Wheels calibration", 0, 1);
		lcd.drawString("Position the robot", 0, 2);
		lcd.drawString("Press ENTER", 0, 3);
		lcd.drawString("to start", 0, 4);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER) return;
		
			// Travel instructions
		leds.setPattern(1, 2);

		lcd.clear();
		lcd.drawString("Insert the distance:", 0, 4);
		String input = lcd.inputNumber(3, 2, 70.0, 0, 5);
		if (input == null) return;
		double distance = Double.parseDouble(input);
		double rotations = 70.0 / bot.getWheelDiameter();
		double diameter = (distance / rotations);
		Delay.msDelay(300);
		lcd.clear();
		if (diameter == bot.getWheelDiameter()) 
		{
			lcd.clear();
			lcd.drawString("The wheel diameter", 0, 2);
			lcd.drawString("of " + bot.getName() + " is ", 0, 3);
			lcd.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
		return;
		}
//		lcd.drawString("wiel: " + bot.getWheelDiameter(), 0, 0);
//		lcd.drawString("rondes: " + rotations, 0, 1);
		lcd.drawString("The new diameter:", 0, 2);
//		lcd.drawString(String.format("%1$,.2f", diameter), 0, 3);
//		String input = String.valueOf((int)(diameter * 100));
		input = String.format(Locale.CANADA, "%1$,.2f", diameter);
		lcd.drawString(input, 0, 3);
		lcd.drawString("Press ENTER to", 0, 5);
		lcd.drawString("apply changes", 0, 6);
		
		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) 
		{
			dirty = true;
			bot.setWheelDiameter("" + (int)(diameter * 100));
			lcd.clear();
			lcd.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			lcd.clear();
			lcd.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}

	private static void testRotate() 
	{
		lcd.clear();
		lcd.drawString("Wheelbase ", 0, 0);
		lcd.drawString("calibration", 0, 1);
		lcd.drawString("Position the robot", 0, 2);
		lcd.drawString("Mark centerfront", 0, 3);
		lcd.drawString("Press ENTER", 0, 4);
		lcd.drawString("to start", 0, 5);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER) return;
// rotate instructions
		leds.setPattern(2, 2);

		lcd.clear();
		lcd.drawString("Insert the gap", 0, 2);
		lcd.drawString("between the mark", 0, 3);
		lcd.drawString("and centerfront", 0, 4);
		String input = lcd.inputNumber(3, 2, 0.0, 0, 6);
		if (input == null) return;
		double angle = Double.parseDouble(input);
		double correction = 1 + (angle / 360);
		double base = bot.getWheelBase() * correction;
		Delay.msDelay(300);
		lcd.clear();
		if (base == bot.getWheelBase()) 
		{
			lcd.clear();
			lcd.drawString("The wheel base", 0, 2);
			lcd.drawString("of " + bot.getName() + " is ", 0, 3);
			lcd.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		lcd.drawString("The new wheelbase:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.2f", base);
		lcd.drawString(input, 0, 3);
		lcd.drawString("Press ENTER to", 0, 5);
		lcd.drawString("apply changes", 0, 6);
		
		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) 
		{
			bot.setWheelBase("" + (int)(base * 100));
			dirty = true;
			lcd.clear();
			lcd.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			lcd.clear();
			lcd.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}

	private static void testDrift() 
	{
		lcd.clear();
		lcd.drawString("Wheel drift", 0, 0);
		lcd.drawString("calibration", 0, 1);
		lcd.drawString("Position the robot", 0, 2);
		lcd.drawString("Mark centerfront", 0, 3);
		lcd.drawString("Press ENTER", 0, 4);
		lcd.drawString("to start", 0, 5);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER) return;
// drift instructions
		leds.setPattern(3, 2);

		lcd.clear();
		lcd.drawString("Insert the gap", 0, 2);
		lcd.drawString("between the mark", 0, 3);
		lcd.drawString("and centerfront", 0, 4);
		String input = lcd.inputNumber(3, 2, 0.0, 0, 6);
		if (input == null) return;
		double distance = Double.parseDouble(input);
		double correction = 1 + (distance / 1000);
		double drift = bot.getWheelDrift() * correction;
		Delay.msDelay(300);
		lcd.clear();
		if (drift == bot.getWheelDrift()) 
		{
			lcd.clear();
			lcd.drawString("The wheel drift", 0, 2);
			lcd.drawString("of " + bot.getName() + " is ", 0, 3);
			lcd.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		lcd.drawString("The new wheeldirft:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.3f", drift);
		lcd.drawString(input, 0, 3);
		lcd.drawString("Press ENTER to", 0, 5);
		lcd.drawString("apply changes", 0, 6);
		
		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) 
		{
			bot.setWheelDrift("" + (int)(drift * 1000));
			dirty = true;
			lcd.clear();
			lcd.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			lcd.clear();
			lcd.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}
}
