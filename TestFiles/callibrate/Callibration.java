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
import utility.TextLCD;
import utility.TextMenu;

public class Callibration {
	
	public static final String DIR = "/";
	static Robbot bot = new Robbot();
	static int option = 1, choice = 0;
	static List<String> botNames = new ArrayList<String>();
	static Keys buttons = new Keys();
	private static boolean dirty;

	public static void main(String[] args) {
		Brick callE = BrickFinder.getDefault();
		callE.setVisible(true);
		
		startUp(); // show a startup dialog
		selectBot(); // select an exisiting bot or create a new one
		shutDown();
	}

	private static void shutDown() {
		TextLCD.clear();
		TextLCD.drawString("Shutting down", 0, 1);
		TextLCD.drawString("Calibration.", 0, 2);
		TextLCD.screenWait(1000, 4);
		buttons.waitForAnyPress(2000);
		System.exit(0);
	}

	private static void startUp() {
		TextLCD.clear();
		TextLCD.drawString("Starting up", 0, 1);
		TextLCD.drawString("Calibration.", 0, 2);
		TextLCD.screenWait(1000, 4);
		try {
			botNames = loadBots();
		} catch (IOException e) {
			noDatabase();
			return;
		}
	}

	private static void noDatabase() {
		TextLCD.clear();
		TextLCD.drawString("Could not locate ", 0, 0);
		TextLCD.drawString("the database.", 0, 1);
		TextLCD.drawString("A new database", 0, 2);
		TextLCD.drawString("will be created.", 0, 3);
		TextLCD.drawString("ENTER > continue...", 0, 5);
		TextLCD.drawString("ESCAPE > quit", 0, 6);
		int button = buttons.getButtons();
		if (button == Keys.ID_ESCAPE) shutDown();
		botNames.add("New robot");
		createBot();
		showMenu();
		return;
	}

	private static void createBot() {
		String robotName = TextLCD.inputName();
		if (robotName == null) return;
		while (botNames.contains(robotName)) {
			TextLCD.clear();
			TextLCD.drawString("This name already", 0, 1);
			TextLCD.drawString("exists. Please ", 0, 2);
			TextLCD.drawString("pick another one.", 0, 3);
			buttons.waitForAnyPress(3000);
			robotName = TextLCD.inputName();
		}
		if (robotName == null) return;
		TextLCD.clear();
		TextLCD.drawString("Building robot", 0, 1);
		TextLCD.drawString("with default", 0, 2);
		TextLCD.drawString("settings.", 0, 3);
		bot = new Robbot();
		bot.setName(robotName);
		addBot(robotName);
		bot.storeSettings();
		buttons.waitForAnyPress(2000);
		bot.editProperties();
		return;
	}

	private static void addBot(String robotName) {
		botNames.add(robotName);
		BufferedWriter writer = null;
		FileWriter fileWriter = null;
		try {
			File file = new File("robotbase");
			if (!file.exists()) {
				file.createNewFile();
				robotName = "New robot\n" + robotName;
			}
			fileWriter = new FileWriter(file, true);
			writer = new BufferedWriter(fileWriter);
			writer.write(robotName + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) writer.close();
				if (fileWriter != null) fileWriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static List<String> loadBots() throws IOException {
		FileReader fileReader = new FileReader(new File("robotbase"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines;
	}

	private static void selectBot() {
//		int option = 1;
		String[] names = new String[botNames.size()];
		for (int i = 0; i < botNames.size(); i++)
		{
			names[i] = String.valueOf(botNames.get(i));
		}
		TextMenu bots = new TextMenu(names, 1, "Choose a robot");
		option = bots.select(option);
		TextLCD.clear();
		Delay.msDelay(1000);
		if (option > 0) 
		{
			try {
				bot.loadSettings(names[option]);
				choice = 0;
				showMenu();
				return;
			} catch (FileNotFoundException e) {
				TextLCD.clear();
				TextLCD.drawString("No settings found", 0, 1);
				TextLCD.drawString("for " + names[option] + ".", 0, 2);
				TextLCD.drawString("Using default", 0, 3);
				TextLCD.drawString("settings.", 0, 4);
				buttons.waitForAnyPress(5000);
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
		dirty = false;
		String[] mainOptions = {"Calibrate wheels", "Calibrate base", "Calibrate drift", "Edit settings", "Store settings", "Main menu"};
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "*EV3 calibration*");
		
		while (true) {
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
					dirty = bot.editProperties();
					break;
				case 4:
					bot.storeSettings();
					dirty = false;
					TextLCD.clear();
					TextLCD.drawString("Saving settings", 0, 1);
					TextLCD.drawString("for " + bot.getName(), 0, 2);
					TextLCD.screenWait(200, 4);
					Delay.msDelay(500);
					break;
				case 5:
					if (dirty) 
					{
						TextLCD.clear();
						TextLCD.drawString("Settings have ", 0, 0);
						TextLCD.drawString("changed", 0, 1);
						TextLCD.drawString("ENTER -> discard", 0, 2);
						TextLCD.drawString("DOWN -> save", 0, 3);
						int button = buttons.getButtons();
						if (button == Keys.ID_DOWN) 
						{
							bot.storeSettings();
							dirty = false;
							TextLCD.drawString("Saving settings", 0, 5);
							TextLCD.drawString("for " + bot.getName(), 0, 6);
							TextLCD.screenWait(200, 7);
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
					selectBot();
					return;
			}
			Delay.msDelay(50);
		}
	}

	private static void testTravel() {
			TextLCD.clear();
			TextLCD.drawString("Wheels calibration", 0, 1);
			TextLCD.drawString("Position the robot", 0, 2);
			TextLCD.drawString("Press ENTER", 0, 3);
			TextLCD.drawString("to start", 0, 4);
			int button = buttons.getButtons();
			if (button != Keys.ID_ENTER) return;
			TextLCD.clear();
			TextLCD.drawString("Insert the distance:", 0, 4);
			String input = TextLCD.inputNumber(3, 2, 70.0, 0, 5);
			if (input == null) return;
			double distance = Double.parseDouble(input);
			double rotations = 70.0 / bot.getWheelDiameter();
			double diameter = (distance / rotations);
			Delay.msDelay(300);
			TextLCD.clear();
			if (diameter == bot.getWheelDiameter()) 
			{
				TextLCD.clear();
				TextLCD.drawString("The wheel diameter", 0, 2);
				TextLCD.drawString("of " + bot.getName() + " is ", 0, 3);
				TextLCD.drawString("nicely callibrated", 0, 4);
				Delay.msDelay(1000);
				return;
			}
//			TextLCD.drawString("wiel: " + bot.getWheelDiameter(), 0, 0);
//			TextLCD.drawString("rondes: " + rotations, 0, 1);
			TextLCD.drawString("The new diameter:", 0, 2);
//			TextLCD.drawString(String.format("%1$,.2f", diameter), 0, 3);
//			String input = String.valueOf((int)(diameter * 100));
			input = String.format(Locale.CANADA, "%1$,.2f", diameter);
			TextLCD.drawString(input, 0, 3);
			TextLCD.drawString("Press ENTER to", 0, 5);
			TextLCD.drawString("apply changes", 0, 6);
			
			button = buttons.getButtons();
			if (button == Keys.ID_ENTER) 
			{
				dirty = true;
				bot.setWheelDiameter("" + (int)(diameter * 100));
				TextLCD.clear();
				TextLCD.drawString("Applying changes", 0, 3);
				Delay.msDelay(500);
			}
			else 
			{
				TextLCD.clear();
				TextLCD.drawString("No changes made", 0, 3);
				Delay.msDelay(1000);
			}
			Delay.msDelay(200);
			return;
		}

	private static void testRotate() {
		TextLCD.clear();
		TextLCD.drawString("Wheelbase ", 0, 0);
		TextLCD.drawString("calibration", 0, 1);
		TextLCD.drawString("Position the robot", 0, 2);
		TextLCD.drawString("Mark centerfront", 0, 3);
		TextLCD.drawString("Press ENTER", 0, 4);
		TextLCD.drawString("to start", 0, 5);
		int button = buttons.getButtons();
		if (button != Keys.ID_ENTER) return;
		TextLCD.clear();
		TextLCD.drawString("Insert the gap", 0, 2);
		TextLCD.drawString("between the mark", 0, 3);
		TextLCD.drawString("and centerfront", 0, 4);
		String input = TextLCD.inputNumber(3, 2, 0.0, 0, 6);
		if (input == null) return;
		double angle = Double.parseDouble(input);
		double correction = 1 + (angle / 360);
		double base = bot.getWheelBase() * correction;
		Delay.msDelay(300);
		TextLCD.clear();
		if (base == bot.getWheelBase()) 
		{
			TextLCD.clear();
			TextLCD.drawString("The wheel base", 0, 2);
			TextLCD.drawString("of " + bot.getName() + " is ", 0, 3);
			TextLCD.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		TextLCD.drawString("The new wheelbase:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.2f", base);
		TextLCD.drawString(input, 0, 3);
		TextLCD.drawString("Press ENTER to", 0, 5);
		TextLCD.drawString("apply changes", 0, 6);
		
		button = buttons.getButtons();
		if (button == Keys.ID_ENTER) 
		{
			bot.setWheelBase("" + (int)(base * 100));
			dirty = true;
			TextLCD.clear();
			TextLCD.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			TextLCD.clear();
			TextLCD.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}

	private static void testDrift() {
		TextLCD.clear();
		TextLCD.drawString("Wheel drift", 0, 0);
		TextLCD.drawString("calibration", 0, 1);
		TextLCD.drawString("Position the robot", 0, 2);
		TextLCD.drawString("Mark centerfront", 0, 3);
		TextLCD.drawString("Press ENTER", 0, 4);
		TextLCD.drawString("to start", 0, 5);
		int button = buttons.getButtons();
		if (button != Keys.ID_ENTER) return;
		TextLCD.clear();
		TextLCD.drawString("Insert the gap", 0, 2);
		TextLCD.drawString("between the mark", 0, 3);
		TextLCD.drawString("and centerfront", 0, 4);
		String input = TextLCD.inputNumber(3, 2, 0.0, 0, 6);
		if (input == null) return;
		double distance = Double.parseDouble(input);
		double correction = 1 + (distance / 1000);
		double drift = bot.getWheelDrift() * correction;
		Delay.msDelay(300);
		TextLCD.clear();
		if (drift == bot.getWheelDrift()) 
		{
			TextLCD.clear();
			TextLCD.drawString("The wheel drift", 0, 2);
			TextLCD.drawString("of " + bot.getName() + " is ", 0, 3);
			TextLCD.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		TextLCD.drawString("The new wheeldirft:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.3f", drift);
		TextLCD.drawString(input, 0, 3);
		TextLCD.drawString("Press ENTER to", 0, 5);
		TextLCD.drawString("apply changes", 0, 6);
		
		button = buttons.getButtons();
		if (button == Keys.ID_ENTER) 
		{
			bot.setWheelDrift("" + (int)(drift * 1000));
			dirty = true;
			TextLCD.clear();
			TextLCD.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			TextLCD.clear();
			TextLCD.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}
}
