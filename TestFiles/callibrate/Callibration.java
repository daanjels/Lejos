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
	static String[] botNames= {};
	static Keys buttons = new Keys();

	public static void main(String[] args) {
		Brick callE = BrickFinder.getDefault();
		callE.setVisible(true);
		
		startUp(); // show a startup dialog
		selectBot(); // select an exisiting bot or create a new one
		showMenu(); // show the calibration menu
		shutDown();
	}

	private static void shutDown() {
		TextLCD.clear();
		TextLCD.drawString("Shutting down", 0, 1);
		TextLCD.drawString("Calibration util.", 0, 2);
		TextLCD.screenWait(1000, 4);
		buttons.waitForAnyPress(2000);
		System.exit(0);
	}

	private static void startUp() {
		TextLCD.clear();
		TextLCD.drawString("Starting up", 0, 1);
		TextLCD.drawString("Calibration util.", 0, 2);
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
		buttons.waitForAnyPress(5000);
		createBot();
		return;
	}

	private static void createBot() {
		String robotName = TextLCD.inputName();
		TextLCD.clear();
		// TODO check if this name is already used
		bot.setName(robotName);
		addBot(robotName);
		bot.storeSettings();
		bot.showProperties();
		TextLCD.drawString("Robot created.", 0, 7);
		buttons.waitForAnyPress();
		return;
	}

	private static void addBot(String robotName) {
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

	private static String[] loadBots() throws IOException {
		FileReader fileReader = new FileReader(new File("robotbase"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}

	private static void selectBot() {
		String[] botNames = {"New robot"};
		int option = 1;
		try {
			botNames = loadBots();
		} catch (IOException e) {
			noDatabase();
			return;
		}
		TextMenu bots = new TextMenu(botNames, 1, "Choose a robot");
		option = bots.select(1);
		TextLCD.clear();
		TextLCD.drawString("selected " + botNames[option], 0, 1);
		Delay.msDelay(1000);
		if (option > 0) {
			try {
				bot.loadSettings(botNames[option]);
				return;
			} catch (FileNotFoundException e) {
				TextLCD.clear();
				TextLCD.drawString("No settings found", 0, 1);
				TextLCD.drawString("for " + botNames[option] + ".", 0, 2);
				TextLCD.drawString("Using default", 0, 3);
				TextLCD.drawString("settings.", 0, 4);
				buttons.waitForAnyPress(5000);
				return;
			}
		}
		createBot();
		return;
	}

	private static void showMenu() {
		String[] mainOptions = {"Calibrate wheels", "Calibrate base", "Calibrate drift", "Edit settings", "Store settings"};
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "*EV3 calibration*");
		int choice;
		
		while (true) {
			choice = mainMenu.select();
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
					bot.editProperties();
					break;
				case 4:
					bot.storeSettings();
					TextLCD.clear();
					TextLCD.drawString("Saving settings", 0, 1);
					TextLCD.drawString("for " + bot.getName(), 0, 2);
					TextLCD.screenWait(200, 4);
					Delay.msDelay(500);
					break;
				case -1:
					return;
			}
			Delay.msDelay(50);
		}
	}

	private static void testTravel() {
			TextLCD.clear();
			TextLCD.drawString("Wheels calibration", 0, 1);
			TextLCD.drawString("Position the robot", 0, 2);
			TextLCD.drawString("Press ENTER to start", 0, 3);
			buttons.waitForAnyPress();
			TextLCD.clear();
			TextLCD.drawString("Insert the distance:", 0, 4);
//			String input = inputNumber(3, 2, 70.0, 0, 5);
			String input = "69.95";
			double distance = Double.parseDouble(input);
			double rotations = 70.0 / bot.getWheelDiameter();// * 3.14159;
			double diameter = (distance / rotations);// / 3.14159);
			Delay.msDelay(1000);
			TextLCD.clear();
			TextLCD.drawString("wiel: " + bot.getWheelDiameter(), 0, 0);
			TextLCD.drawString("rondes: " + rotations, 0, 1);
			TextLCD.drawString("The diameter is:", 0, 2);
			TextLCD.drawString(String.format("%1$,.2f", diameter), 0, 3);
//			String input = String.valueOf((int)(diameter * 100));
			input = String.format(Locale.CANADA, "%1$,.2f", diameter);
			TextLCD.drawString(input, 0, 4);
			TextLCD.drawString("Press ENTER to save", 0, 5);
			int button = buttons.waitForAnyPress();
			if (button == Keys.ID_ENTER) {
//			bot.setWheelDiameter(input);
			}
			Delay.msDelay(200);
			return;
		}

	private static void testRotate() {
		TextLCD.clear();
		TextLCD.drawString("Base calibration", 0, 1);
		Delay.msDelay(1000);
		return;
	}

	private static void testDrift() {
		TextLCD.clear();
		TextLCD.drawString("Drift calibration", 0, 1);
		Delay.msDelay(1000);
		return;
	}
}
