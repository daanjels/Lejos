package performance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.internal.ev3.EV3LCDManager;
import lejos.internal.ev3.EV3LCDManager.LCDLayer;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;
import lejos.utility.TextMenu;
import performance.Robot;

public class Calibration {
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static Keys buttons = BrickFinder.getDefault().getKeys();
	static Robot bot = new Robot();
	static String title = "EV3 calibration";
	static String[] botNames = {"New Robot"};
	
	public static void main(String[] args) {
		// make sure outputstream is not displayed on the LCD
		EV3LCDManager manager =EV3LCDManager.getLocalLCDManager();
		LCDLayer layer = manager.getLayer("STDOUT");
		layer.setVisible(false);
		
		startUp(); // show a startup dialog
		selectBot(); // select an exisiting bot or create a new one
		showMenu(); // show the calibration menu
		shutDown();
	}
	
	private static void showMenu() {
		String[] mainOptions = {"Calibrate wheels", "Calibrate base", "Calibrate drift", "Edit settings", "Store settings"};
		TextMenu mainMenu = new TextMenu(mainOptions, 1, "*EV3 calibration*");
		int choice;
		
		while (buttons.getButtons() != Keys.ID_ESCAPE) {
			LCD.clear();
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
					buttons.waitForAnyPress();
					Delay.msDelay(200);
					break;
				case 4:
					bot.storeSettings();
					LCD.drawString("Saving settings", 0, 1);
					LCD.drawString("for " + bot.getName(), 0, 2);
					showWait(3, 200);
					Delay.msDelay(500);
					break;
			}
			Delay.msDelay(100);
		}
		Delay.msDelay(200);
		return;
	}

	private static void testTravel() {
		Chassis car = buildCar();
		LCD.clear();
		LCD.drawString("Wheels calibration", 0, 1);
		LCD.drawString("Position the robot", 0, 2);
		LCD.drawString("Press ENTER to start", 0, 3);
		buttons.waitForAnyPress();
		System.out.println(car.getPoseProvider().getPose());
		car.travel(70.0);
		car.waitComplete();
		Delay.msDelay(500);
		LCD.clear();
		LCD.drawString("Insert the distance:", 0, 4);
		String input= inputNumber(3, 2, 70.0, 0, 5);
		double distance = Double.parseDouble(input);
		double rotations = 70.0 / bot.getWheelDiameter();// * 3.14159;
		double diameter = (distance / rotations);// / 3.14159);
		LCD.clear();
		LCD.drawString("wiel: " + bot.getWheelDiameter(), 0, 0);
		LCD.drawString("rondes: " + rotations, 0, 1);
		LCD.drawString("The diameter is:", 0, 2);
//		LCD.drawString(String.format("%1$,.2f", diameter), 0, 3);
//		String input = String.valueOf((int)(diameter * 100));
		input = String.format(Locale.CANADA, "%1$,.2f", diameter);
		LCD.drawString(input, 0, 4);
		LCD.drawString("Press ENTER to save", 0, 5);
		buttons.waitForAnyPress();
		if (buttons.getButtons() == Keys.ID_ENTER) {
//			bot.setWheelDiameter(input);
		}
		Delay.msDelay(200);
		return;
	}


	private static void testRotate() {
		LCD.clear();
		LCD.drawString("Base calibration", 0, 1);
		Delay.msDelay(1000);
		return;
	}


	private static void testDrift() {
		LCD.clear();
		LCD.drawString("Drift calibration", 0, 1);
		Delay.msDelay(1000);
		return;
	}



	private static void noDatabase() {
		LCD.clear();
		LCD.drawString("Could not locate ", 0, 0);
		LCD.drawString("the database", 0, 1);
		LCD.drawString("A new robot will", 0, 2);
		LCD.drawString("be created", 0, 3);
		buttons.waitForAnyPress();
		Delay.msDelay(200);
		createBot();
		return;
	}

	private static String[] loadBots() throws IOException {
		FileReader fileReader = new FileReader(new File("/home/lejos/programs/robotbase"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		lines.add("New robot");
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines.toArray(new String[lines.size()]);
	}

	private static void createBot() {
		String robotName = inputName();
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
			File file = new File("/home/lejos/programs/robotbase");
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

	private static String inputName() {
		String inName = "ev3";
		LCD.clear();
		LCD.drawString(title, 0, 0);
		LCD.drawString("Give your robot", 0, 2);
		LCD.drawString("a name:", 0, 3);
		while (buttons.getButtons() != Keys.ID_ENTER) {
			inName = inputString(true, inName, 4);
		}
		LCD.drawString("> " + inName, 0, 5);
		Delay.msDelay(200);
		return inName;
	}

	/** Take double value input from the EV3 brick.
	* @param digits The number of integer digits (before the decimal point): Maximum 6
	* @param floats The number of decimal figures (after the decimal point): Maximum 4
	* @param value The default value (double) that the user can start from.
	* @param pos The starting position to display the value.
	* @param row The row to display the value.
	*/
	
	private static String inputNumber(int digits, int floats, double value, int pos, int row) {
		double[] increments = {100000, 10000, 1000, 100, 10, 1, 0.0, 0.1, 0.01, 0.001, 0.0001, 0.00001};
		double[] increment = Arrays.copyOfRange(increments, 6-digits, 11);
		int limit = digits + 1 + floats; // total number of position for the double format
		int col = pos + digits - 1; // position of the 'cursor' (before the decimal point)
		String decimalValue = String.format("%1." + floats + "f", value); // double value with correct format
		int lead = limit - decimalValue.length(); // leading space

		LCD.drawString(String.format(Locale.CANADA, "%1$," + limit + "." + floats + "f", value), pos, row);
		decimalValue = decimalValue.substring(col - lead, col - lead + 1);
		LCD.drawString(decimalValue, col, row, true);
		while (buttons.getButtons() != Keys.ID_ENTER) {
			if (buttons.getButtons() == Keys.ID_UP) {
				value = value + increment[col];
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				value = value - increment[col];
				if (value < 0) value = value + increment[col];
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
//				LCD.drawString(" ", col + pos, row-1);
				LCD.drawString(" ", col + pos, row);
				col = col + 1;
				System.out.println("col: " + col + " / limit: " + limit);
				if (col == limit) col = lead;
				if (col == digits) col = digits + 1;
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
//				LCD.drawString(" ", col + pos, row-1);
				LCD.drawString(" ", col + pos, row);
				col = col - 1;
				if (col == digits) col = digits - 1; // skip the decimal point
				if (decimalValue == "_" || col < 0) col = limit - 1; // if at the start, go to the end
			}
			decimalValue = String.format("%1." + floats + "f", value);
			if (decimalValue.length() > limit) {
				value = value - increment[col];
				decimalValue = String.format("%1." + floats + "f", value);
			}
			lead = limit - decimalValue.length();
			LCD.drawString(String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value), pos, row);
			if (col - lead < 0) {
				decimalValue = "_";
			} else {
				decimalValue = decimalValue.substring(col - lead, col - lead + 1);
			}
			LCD.drawString(decimalValue, col + pos, row, true);
			Delay.msDelay(200);
		}
		buttons.waitForAnyPress();
		return String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value);
	}
	
	private static String inputString(boolean type, String name, int row) {
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz-0123456789";
		if (type == false) alpha = "0123456789.";
		int chr = 0;
		int pos = 0;
		String input = "";
		String[] in = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
		for (int i = 0; i < name.length(); i++) {
			in[i] = name.substring(i, i+1);
			LCD.drawString(in[i], i, row);
		}
		chr = alpha.indexOf(in[0]);
		Delay.msDelay(150);
		while(buttons.getButtons() != Keys.ID_ENTER) {
			if (buttons.getButtons() == Keys.ID_UP) {
				chr = chr + 1;
				if (chr>=alpha.length()) chr = 0;
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
				in[pos] = alpha.substring(chr, chr+1);
			} else if (buttons.getButtons() == Keys.ID_DOWN) {
				chr = chr - 1;
				if (chr<0) chr = alpha.length()-1;
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
				in[pos] = alpha.substring(chr, chr+1);
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
				in[pos] = alpha.substring(chr, chr+1);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
				pos = pos + 1;
				if (pos > 13) pos = 0;
				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
			} else if (buttons.getButtons() == Keys.ID_LEFT) {
				in[pos] = alpha.substring(chr, chr+1);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
				pos = pos - 1;
				if (pos < 0) pos = 13;
				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
			}
			Delay.msDelay(150);
		}
		in[pos] = alpha.substring(chr, chr+1);
		Delay.msDelay(200);
		for (int i = 0; i < in.length; i++) {
			input = input+in[i];
			System.out.println(input);
		}
		return input;
	}

	private static void startUp() {
		LCD.clear();
		LCD.drawString(title, 0, 0);
		LCD.drawString("Loading catalog", 0, 3);
		showWait(4, 100);
		try {
			botNames = loadBots();
		} catch (IOException e) {
			noDatabase();
			return;
		}
		return;
	}

	private static void shutDown() {
		LCD.clear();
		LCD.drawString(title, 0, 0);
		LCD.drawString("Shutting down", 0, 3);
		showWait(4, 100);
		System.exit(0);
	}

	private static void showWait(int row, int wait) {
		for (int i = 0; i < 18; i= i + 2) {
			LCD.drawString(".", i, row);
			Delay.msDelay(wait);
		}
	}

	private static Chassis buildCar() {
		Wheel rightWheel = WheeledChassis.modelWheel(RIGHT_MOTOR, bot.getWheelRight()).offset(bot.getWheelOffset()).invert(bot.getReverseRight());
		Wheel leftWheel = WheeledChassis.modelWheel(LEFT_MOTOR, bot.getWheelLeft()).offset(-bot.getWheelOffset()).invert(bot.getReverseLeft());
		Chassis car = new WheeledChassis(new Wheel[] {rightWheel, leftWheel},WheeledChassis.TYPE_DIFFERENTIAL);
		car.setLinearSpeed(bot.getLinearSpeed());
		car.setAngularSpeed(bot.getAngularSpeed());
		car.setAcceleration(bot.getAcceleration(), bot.getAcceleration()*5);
		return car;
	}

	private static void selectBot() {
		String[] botNames = {"New robot"};
		int option = 1;
		LCD.clear();
		try {
			botNames = loadBots();
		} catch (IOException e) {
			noDatabase();
			return;
		}
		TextMenu bots = new TextMenu(botNames, 1, "Choose a robot");
		option = bots.select(1);
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