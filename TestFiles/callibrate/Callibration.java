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

import lejos.utility.Delay;
//import lejos.utility.TextMenu;
import utility.Keys;
import utility.LCD;

public class Callibration {
	
	public static final String DIR = "/";
	static Robbot bot = new Robbot();
	static String[] botNames= {};
	static Keys buttons = new Keys();

	public static void main(String[] args) {
//		Screen display = new Screen();

		LCD.showGUI();
		startUp(); // show a startup dialog
		selectBot(); // select an exisiting bot or create a new one
//		showMenu(); // show the calibration menu
		shutDown();
	}

	private static void shutDown() {
		LCD.clear();
		LCD.drawString("Shutting down", 0, 1);
		LCD.drawString("Calibration util", 0, 2);
		LCD.screenWait(1000, 4);
		System.exit(0);
	}

	private static void startUp() {
		LCD.clear();
		LCD.drawString("Starting up", 0, 1);
		LCD.drawString("Calibration util", 0, 2);
		LCD.screenWait(1000, 4);
		try {
			botNames = loadBots();
		} catch (IOException e) {
			noDatabase();
			return;
		}
	}

	private static void noDatabase() {
		LCD.clear();
		LCD.drawString("Could not locate ", 0, 0);
		LCD.drawString("the database", 0, 1);
		LCD.drawString("A new robot will", 0, 2);
		LCD.drawString("be created", 0, 3);
		buttons.waitForAnyPress();
		Delay.msDelay(2000);
		createBot();
		return;
	}

	private static void createBot() {
		String robotName = LCD.inputName();
		// TODO check if this name is already used
		bot.setName(robotName);
		addBot(robotName);
		bot.storeSettings();
		bot.showProperties();
		LCD.drawString("Robot created", 0, 7);
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
		LCD.clear();
		LCD.drawString("List: " + botNames[0], 0, 0);
		try {
			botNames = loadBots();
		} catch (IOException e) {
			noDatabase();
			return;
		}
//		TextMenu bots = new TextMenu(botNames, 1, "Choose a robot");
//		option = bots.select(1);
		option = 1;
		LCD.drawString("selected " + botNames[option], 0, 1);
		buttons.waitForAnyPress();
		if (option > 0) {
			try {
				bot.loadSettings(botNames[option]);
				return;
			} catch (FileNotFoundException e) {
				LCD.drawString("No settings found", 0, 1);
				LCD.drawString("for " + botNames[option], 0, 2);
				buttons.waitForAnyPress();
				return;
			}
		}
		createBot();
		return;
	}

}
