package chassis.preformance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private static final String DIR = "home/lejos/robots/"; // should try to move the database elsewhere

	
	static Keys buttons = Brick.getKeys(); // can't set this inside main using callE because it is static
	static Robbot bot = new Robbot();
	static int option = 1, choice = 0;
	static List<String> botNames = new ArrayList<String>();
	static String title = "EV3 calibration";
	private static boolean dirty;
	static Leds leds = Brick.getLed();
	static TextLCD lcd = Brick.getTextLCD(); // should try to use LCD

	
	
	public static void main(String[] args) 
	{

		
		
		
		Brick callE = BrickFinder.getDefault();
		startUp(); // show a startup dialog (if no database available, create one)
		selectBot(); // select an existing bot (if 'new robot' is selected, create one)
		shutDown();
	}

	
	
	private static void startUp() 
	{
		System.out.println("Starting up...");
		lcd.clear(); // change lcd to LCD for final code
		lcd.drawString(title, 0, 0);
		lcd.drawString("Loading catalog", 0, 3);
		lcd.screenWait(1000, 4); // showWait(1000, 4); // fill line 4 with dots in 1000 milliseconds
		// new File(DIR).mkdirs(); // directory already exists
		try 
		{
			botNames = loadBots();
		} 
		catch (IOException e) 
		{
			noDatabase();
			return;
		}
		// return;
	}

	private static void shutDown() 
	{
		System.out.println("OK, we're closing down...");
		lcd.clear(); // change lcd to LCD for final code
		lcd.drawString(title, 0, 0);
		lcd.drawString("Shutting down", 0, 3);
		lcd.screenWait(1000, 4); // showWait(1000, 4);
		buttons.waitForAnyPress(1000);
		System.exit(0);
	}

	private static void showMenu() 
	{
		System.out.println("Now show the callibration menu");
		dirty = false; // we just loaded the settings so they are not dirty
		String[] mainOptions = { "Calibrate wheels", "Calibrate base", "Calibrate drift", "Edit settings",
				"Store settings", "Main menu" };
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "*EV3 calibration*");

		while (true) 
		{
			choice = mainMenu.select(choice);
			switch (choice) {
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
				// dirty = bot.editProperties(); //
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
				if (dirty) 
				{
					bot.storeSettings();
					dirty = false;
					lcd.clear();
					lcd.drawString("Saving settings", 0, 1);
					lcd.drawString("for " + bot.getName(), 0, 2);
					lcd.screenWait(200, 4); // showWait(200, 4);
					Delay.msDelay(1500);
				} 
				else 
				{
					lcd.clear();
					lcd.drawString("No changes were", 0, 3);
					lcd.drawString("made to " + bot.getName(), 0, 4);
					Delay.msDelay(1500);
				}
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
					if (button == Keys.ID_RIGHT) 
					{
						bot.storeSettings();
						lcd.drawString("Saving settings", 0, 5);
						lcd.drawString("for " + bot.getName(), 0, 6);
						lcd.screenWait(200, 7); // showWait(200, 7);
						Delay.msDelay(1500);
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
					if (button == Keys.ID_RIGHT) 
					{
						bot.storeSettings();
						lcd.drawString("Saving settings", 0, 5);
						lcd.drawString("for " + bot.getName(), 0, 6);
						lcd.screenWait(200, 7); // showWait(200, 7);
						Delay.msDelay(1500);
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

	private static void noDatabase() 
	{
		System.out.println("Oops, can't find the database...");
		lcd.clear(); // change lcd to LCD for final code
		lcd.drawString("Could not locate ", 0, 0);
		lcd.drawString("the database.", 0, 1);
		lcd.drawString("A new database", 0, 2);
		lcd.drawString("will be created.", 0, 3);
		lcd.drawString("ENTER > continue...", 0, 5);
		lcd.drawString("ESCAPE > quit", 0, 6);
		int button = buttons.waitForAnyPress();
		if (button == Keys.ID_ESCAPE)
			shutDown();
		botNames.add("New robot");
		// the first robot in the list is the option to create a new robot

		return;
	}

	private static void createBot() 
	{
		System.out.println("Let's create a robot...");
		String robotName = lcd.inputName();
		if (robotName == null) 
		{
			System.out.println("The robotName cannot be null!");
			selectBot();
			shutDown();
		} // is this necessary?
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
		} // is this necessary?
		lcd.clear();
		lcd.drawString("Building robot", 0, 5);
		lcd.drawString("with default", 0, 6);
		lcd.drawString("settings.", 0, 7);
		bot = new Robbot();
		bot.setName(robotName);
		addBot(robotName);
		bot.storeSettings();
		buttons.waitForAnyPress(2000);
		dirty = bot.editProperties();
		if (dirty) bot.storeSettings();
//		System.out.println("Bot created: " + dirty);
		return;
	}

	private static void addBot(String robotName) 
	{
		System.out.println("Add the robot to the database");
		botNames.add(robotName);
		storeBots();
	}

	private static void storeBots() 
	{
		System.out.println("Updating the robot database");
		BufferedWriter writer = null;
		FileWriter fileWriter = null;
		File map = new File(DIR);
		if (!map.exists()) {
			Path pad = Paths.get(DIR);
			try {
				System.out.println("Directory does not exist, let's create it");
				Files.createDirectory(pad);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				shutDown();
			}
		}
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
		System.out.println("Loading the robots...");
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
		System.out.println("Show the menu to select a robot");
		if (botNames.size() < 2) // 'new robot' is the first in the list, so let's create a robot
		{
			System.out.println("Let's create a new robot");
			createBot();
			bot.storeSettings();
			showMenu();
			return;
		}
		String[] names = new String[botNames.size()];
		for (int i = 0; i < botNames.size(); i++) 
		{
			names[i] = String.valueOf(botNames.get(i));
		}
		// lcd.clear();
		TextMenu bots = new TextMenu(names, 1, "Choose a robot");
		option = bots.select(option);
		lcd.clear();
		Delay.msDelay(100);
		// TODO: This option is not yet available in the current release,
		// need to figure out how to update the TextMenu class
		if (option > 200) // pressing LEFT allows you to remove a bot
		{
			option = option - 200;
			lcd.drawString("Do you want to", 0, 1);
			lcd.drawString("rename " + names[option] + "?", 0, 2);
			lcd.drawString("ENTER > yes", 0, 5);
			lcd.drawString("ESCAPE > no", 0, 6);
			int button = buttons.waitForAnyPress();
			if (button == Keys.ID_ENTER) {
				String oldName = names[option];
				botNames.remove(option);
				lcd.clear();
				lcd.drawString("Give a new name:", 0, 3);
				String newName = lcd.inputString(true, oldName, 4);
				if (newName != null) {
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
				if (newName == null)
					return;
				System.out.println(DIR + oldName + ".txt");
				File f = new File(DIR + oldName + ".txt");
				f.delete();
				// Need to remove the original settingsfile
				addBot(newName);
				try {
					bot.loadSettings(oldName);
				} catch (FileNotFoundException e) {

				}
				bot.setName(newName);
				bot.storeSettings();
				lcd.drawString(names[option] + " will be renamed", 0, 3);
				Delay.msDelay(1000);
			}
			selectBot();
		}
		if (option > 100) {
			option = option - 100;
			lcd.drawString("Do you want to", 0, 1);
			lcd.drawString("remove " + names[option] + "?", 0, 2);
			lcd.drawString("ENTER > yes", 0, 4);
			lcd.drawString("ESCAPE > no", 0, 5);
			int button = buttons.waitForAnyPress();
			if (button == Keys.ID_ENTER) {
				botNames.remove(option);
				System.out.println(DIR + names[option] + ".txt is removed");
				File f = new File(DIR + names[option] + ".txt");
				f.delete();
				storeBots();
				lcd.clear();
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
			lcd.drawString("Are you sure you", 0, 1);
			lcd.drawString("want to quit?", 0, 2);
			lcd.drawString("ESCAPE > yes", 0, 4);
			lcd.drawString("other > no", 0, 5);
			int button = buttons.waitForAnyPress();
			if (button == Keys.ID_ESCAPE) {
				return;
			}
			selectBot();
		}
		createBot();
		showMenu();
		return;
	}

	private static void testTravel() {
		lcd.clear();
		lcd.drawString("Wheels calibration", 0, 1);
		lcd.drawString("Position the robot", 0, 2);
		lcd.drawString("Press ENTER", 0, 3);
		lcd.drawString("to start", 0, 4);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER)
			return;

		// Chassis car = buildCar();
		// Travel instructions
		leds.setPattern(1, 2);

		lcd.clear();
		lcd.drawString("Insert the distance:", 0, 4);
		String input = lcd.inputNumber(3, 2, 70.0, 0, 5);
		if (input == null)
			return;
		double distance = Double.parseDouble(input);
		double rotations = 70.0 / bot.getWheelDiameter();
		double diameter = (distance / rotations);
		Delay.msDelay(300);
		lcd.clear();
		if (diameter == bot.getWheelDiameter()) {
			lcd.clear();
			lcd.drawString("The wheel diameter", 0, 2);
			lcd.drawString("of " + bot.getName() + " is ", 0, 3);
			lcd.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		// lcd.drawString("wiel: " + bot.getWheelDiameter(), 0, 0);
		// lcd.drawString("rondes: " + rotations, 0, 1);
		lcd.drawString("The new diameter:", 0, 2);
		// lcd.drawString(String.format("%1$,.2f", diameter), 0, 3);
		// String input = String.valueOf((int)(diameter * 100));
		input = String.format(Locale.CANADA, "%1$,.2f", diameter);
		lcd.drawString(input, 0, 3);
		lcd.drawString("Press ENTER to", 0, 5);
		lcd.drawString("apply changes", 0, 6);

		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) {
			dirty = true;
			bot.setWheelDiameter("" + (int) (diameter * 100));
			lcd.clear();
			lcd.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		} else {
			lcd.clear();
			lcd.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}

	private static void testRotate() {
		lcd.clear();
		lcd.drawString("Wheelbase ", 0, 0);
		lcd.drawString("calibration", 0, 1);
		lcd.drawString("Position the robot", 0, 2);
		lcd.drawString("Mark centerfront", 0, 3);
		lcd.drawString("Press ENTER", 0, 4);
		lcd.drawString("to start", 0, 5);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER)
			return;
		// rotate instructions
		leds.setPattern(2, 2);

		lcd.clear();
		lcd.drawString("Insert the gap", 0, 2);
		lcd.drawString("between the mark", 0, 3);
		lcd.drawString("and centerfront", 0, 4);
		String input = lcd.inputNumber(3, 2, 0.0, 0, 6);
		if (input == null)
			return;
		double angle = Double.parseDouble(input);
		double correction = 1 + (angle / 360);
		double base = bot.getWheelBase() * correction;
		Delay.msDelay(300);
		lcd.clear();
		if (base == bot.getWheelBase()) {
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
		if (button == Keys.ID_ENTER) {
			bot.setWheelBase("" + (int) (base * 100));
			dirty = true;
			lcd.clear();
			lcd.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		} else {
			lcd.clear();
			lcd.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}

	private static void testDrift() {
		lcd.clear();
		lcd.drawString("Wheel drift", 0, 0);
		lcd.drawString("calibration", 0, 1);
		lcd.drawString("Position the robot", 0, 2);
		lcd.drawString("Mark centerfront", 0, 3);
		lcd.drawString("Press ENTER", 0, 4);
		lcd.drawString("to start", 0, 5);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER)
			return;
		// drift instructions
		leds.setPattern(3, 2);

		lcd.clear();
		lcd.drawString("Insert the gap", 0, 2);
		lcd.drawString("between the mark", 0, 3);
		lcd.drawString("and centerfront", 0, 4);
		String input = lcd.inputNumber(3, 2, 0.0, 0, 6);
		if (input == null)
			return;
		double distance = Double.parseDouble(input);
		double correction = 1 + (distance / 1000);
		double drift = bot.getWheelDrift() * correction;
		Delay.msDelay(300);
		lcd.clear();
		if (drift == bot.getWheelDrift()) {
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
		if (button == Keys.ID_ENTER) {
			bot.setWheelDrift("" + (int) (drift * 1000));
			dirty = true;
			lcd.clear();
			lcd.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		} else {
			lcd.clear();
			lcd.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}
}
