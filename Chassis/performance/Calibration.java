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

import lejos.hardware.Brick;
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

public class Calibration 
{
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);
	static Keys buttons;
	static Robot bot = new Robot();
	static int option = 1, choice = 0;
	static List<String> botNames = new ArrayList<String>();
	static String title = "EV3 calibration";
	private static boolean dirty;
	
	public static void main(String[] args) 
	{
		// make sure outputstream is not displayed on the LCD
		EV3LCDManager manager =EV3LCDManager.getLocalLCDManager();
		LCDLayer layer = manager.getLayer("STDOUT");
		layer.setVisible(false);
		Brick callE = BrickFinder.getDefault();
		buttons = callE.getKeys();

		startUp(); // show a startup dialog
		selectBot(); // select an exisiting bot or create a new one
		shutDown();
	}
	
	private static void showMenu()
	{
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
					LCD.clear();
					LCD.drawString("Saving settings", 0, 1);
					LCD.drawString("for " + bot.getName(), 0, 2);
					showWait(200, 4);
					Delay.msDelay(500);
					break;
				case 5:
					if (dirty) 
					{
						LCD.clear();
						LCD.drawString("Settings have ", 0, 0);
						LCD.drawString("changed", 0, 1);
						LCD.drawString("ENTER -> discard", 0, 2);
						LCD.drawString("DOWN -> save", 0, 3);
						int button = buttons.waitForAnyPress();
						if (button == Keys.ID_DOWN) 
						{
							bot.storeSettings();
							dirty = false;
							LCD.drawString("Saving settings", 0, 5);
							LCD.drawString("for " + bot.getName(), 0, 6);
							showWait(200, 7);
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
		Chassis car = buildCar();
		
		LCD.clear();
		LCD.drawString("Wheels calibration", 0, 1);
		LCD.drawString("Position the robot", 0, 2);
		LCD.drawString("Press ENTER ", 0, 3);
		LCD.drawString("to start", 0, 4);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER) return;
		System.out.println(car.getPoseProvider().getPose());
		car.travel(70.0);
		car.waitComplete();
		Delay.msDelay(500);
		LCD.clear();
		LCD.drawString("Insert the distance:", 0, 4);
		String input= inputNumber(3, 2, 70.0, 0, 5);
		if (input == null) return;
		double distance = Double.parseDouble(input);
		double rotations = 70.0 / bot.getWheelDiameter();;
		double diameter = (distance / rotations);
		Delay.msDelay(300);
		LCD.clear();
		if (diameter == bot.getWheelDiameter()) 
		{
			LCD.clear();
			LCD.drawString("The wheel diameter", 0, 2);
			LCD.drawString("of " + bot.getName() + " is ", 0, 3);
			LCD.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
//		LCD.drawString("wiel: " + bot.getWheelDiameter(), 0, 0);
//		LCD.drawString("rondes: " + rotations, 0, 1);
		LCD.drawString("The diameter is:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.2f", diameter);
		LCD.drawString(input, 0, 3);
		LCD.drawString("Press ENTER to", 0, 5);
		LCD.drawString("apply changes", 0, 6);
		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) 
		{
			dirty = true;
			bot.setWheelDiameter("" + (int)(diameter * 100));
			LCD.clear();
			LCD.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			LCD.clear();
			LCD.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}


	private static void testRotate() {
		LCD.clear();
		LCD.drawString("Wheelbase ", 0, 0);
		LCD.drawString("calibration", 0, 1);
		LCD.drawString("Position the robot", 0, 2);
		LCD.drawString("Mark centerfront", 0, 3);
		LCD.drawString("Press ENTER", 0, 4);
		LCD.drawString("to start", 0, 5);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER) return;
		LCD.clear();
		LCD.drawString("Insert the gap", 0, 2);
		LCD.drawString("between the mark", 0, 3);
		LCD.drawString("and centerfront", 0, 4);
		String input = inputNumber(3, 2, 0.0, 0, 6);
		if (input == null) return;
		double angle = Double.parseDouble(input);
		double correction = 1 + (angle / 360);
		double base = bot.getWheelBase() * correction;
		Delay.msDelay(300);
		LCD.clear();
		if (base == bot.getWheelBase()) 
		{
			LCD.clear();
			LCD.drawString("The wheel base", 0, 2);
			LCD.drawString("of " + bot.getName() + " is ", 0, 3);
			LCD.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		LCD.drawString("The new wheelbase:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.2f", base);
		LCD.drawString(input, 0, 3);
		LCD.drawString("Press ENTER to", 0, 5);
		LCD.drawString("apply changes", 0, 6);
		
		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) 
		{
			bot.setWheelBase("" + (int)(base * 100));
			dirty = true;
			LCD.clear();
			LCD.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			LCD.clear();
			LCD.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}


	private static void testDrift() 
	{
		LCD.clear();
		LCD.drawString("Wheel drift", 0, 0);
		LCD.drawString("calibration", 0, 1);
		LCD.drawString("Position the robot", 0, 2);
		LCD.drawString("Mark centerfront", 0, 3);
		LCD.drawString("Press ENTER", 0, 4);
		LCD.drawString("to start", 0, 5);
		int button = buttons.waitForAnyPress();
		if (button != Keys.ID_ENTER) return;
		LCD.clear();
		LCD.drawString("Insert the gap", 0, 2);
		LCD.drawString("between the mark", 0, 3);
		LCD.drawString("and centerfront", 0, 4);
		String input = inputNumber(3, 2, 0.0, 0, 6);
		if (input == null) return;
		double distance = Double.parseDouble(input);
		double correction = 1 + (distance / 1000);
		double drift = bot.getWheelDrift() * correction;
		Delay.msDelay(300);
		LCD.clear();
		if (drift == bot.getWheelDrift()) 
		{
			LCD.clear();
			LCD.drawString("The wheel drift", 0, 2);
			LCD.drawString("of " + bot.getName() + " is ", 0, 3);
			LCD.drawString("nicely callibrated", 0, 4);
			Delay.msDelay(1000);
			return;
		}
		LCD.drawString("The new wheeldirft:", 0, 2);
		input = String.format(Locale.CANADA, "%1$,.3f", drift);
		LCD.drawString(input, 0, 3);
		LCD.drawString("Press ENTER to", 0, 5);
		LCD.drawString("apply changes", 0, 6);
		
		button = buttons.waitForAnyPress();
		if (button == Keys.ID_ENTER) 
		{
			bot.setWheelDrift("" + (int)(drift * 1000));
			dirty = true;
			LCD.clear();
			LCD.drawString("Applying changes", 0, 3);
			Delay.msDelay(500);
		}
		else 
		{
			LCD.clear();
			LCD.drawString("No changes made", 0, 3);
			Delay.msDelay(1000);
		}
		Delay.msDelay(200);
		return;
	}

	private static void noDatabase() {
		LCD.clear();
		LCD.drawString("Could not locate ", 0, 0);
		LCD.drawString("the database.", 0, 1);
		LCD.drawString("A new database", 0, 2);
		LCD.drawString("will be created.", 0, 3);
		LCD.drawString("ENTER > continue...", 0, 5);
		LCD.drawString("ESCAPE > quit", 0, 6);
		int button = buttons.waitForAnyPress();
		if (button == Keys.ID_ESCAPE) shutDown();
		botNames.add("New robot");
		createBot();
		showMenu();
		return;
	}

	private static List<String> loadBots() throws IOException {
		FileReader fileReader = new FileReader(new File("/home/lejos/programs/robotbase"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines;
	}

	private static void createBot() {
		String robotName = inputName();
		if (robotName == null) return;
		while (botNames.contains(robotName)) {
			LCD.clear();
			LCD.drawString("This name already", 0, 1);
			LCD.drawString("exists. Please ", 0, 2);
			LCD.drawString("pick another one.", 0, 3);
			buttons.waitForAnyPress(3000);
			robotName = inputName();
		}
		if (robotName == null) return;
		LCD.clear();
		LCD.drawString("Building robot", 0, 1);
		LCD.drawString("with default", 0, 2);
		LCD.drawString("settings.", 0, 3);
		bot = new Robot();
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
//		while (buttons.getButtons() != Keys.ID_ENTER) {
//			inName = inputString(true, inName, 4);
//		}
//		LCD.drawString("> " + inName, 0, 5);
//		Delay.msDelay(200);
		inName = inputString(true, "EV3", 4);
		if (inName != null)
		{
			LCD.drawString("> " + inName, 0, 5);
			Delay.msDelay(2000);
		}
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
//		while (buttons.getButtons() != Keys.ID_ENTER) {
//			if (buttons.getButtons() == Keys.ID_UP) {
//				value = value + increment[col];
//			} else if (buttons.getButtons() == Keys.ID_DOWN) {
//				value = value - increment[col];
//				if (value < 0) value = value + increment[col];
//			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
////				LCD.drawString(" ", col + pos, row-1);
//				LCD.drawString(" ", col + pos, row);
//				col = col + 1;
//				System.out.println("col: " + col + " / limit: " + limit);
//				if (col == limit) col = lead;
//				if (col == digits) col = digits + 1;
//			} else if (buttons.getButtons() == Keys.ID_LEFT) {
////				LCD.drawString(" ", col + pos, row-1);
//				LCD.drawString(" ", col + pos, row);
//				col = col - 1;
//				if (col == digits) col = digits - 1; // skip the decimal point
//				if (decimalValue == "_" || col < 0) col = limit - 1; // if at the start, go to the end
//			}
//			decimalValue = String.format("%1." + floats + "f", value);
//			if (decimalValue.length() > limit) {
//				value = value - increment[col];
//				decimalValue = String.format("%1." + floats + "f", value);
//			}
//			lead = limit - decimalValue.length();
//			LCD.drawString(String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value), pos, row);
//			if (col - lead < 0) {
//				decimalValue = "_";
//			} else {
//				decimalValue = decimalValue.substring(col - lead, col - lead + 1);
//			}
//			LCD.drawString(decimalValue, col + pos, row, true);
//			Delay.msDelay(200);
//		}
//		buttons.waitForAnyPress();
//		return String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value);
		while(true)
		{
			int button;
			do
			{
				button = buttons.waitForAnyPress();
			}
			
			while (button == 0);
			if (button == Keys.ID_UP) 
			{
				value = value + increment[col];
			} 
			if (button == Keys.ID_DOWN) 
			{
				value = value - increment[col];
				if (value < 0) value = value + increment[col];
			} 
			if (button == Keys.ID_RIGHT) 
			{
				LCD.drawString(" ", col + pos, row);
				col = col + 1;
				System.out.println("col: " + col + " / limit: " + limit);
				if (col == limit) col = lead;
				if (col == digits) col = digits + 1;
			} 
			if (button == Keys.ID_LEFT) 
			{
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
			if (col - lead < 0) 
			{
				decimalValue = "_";
			} else 
			{
				decimalValue = decimalValue.substring(col - lead, col - lead + 1);
			}
			LCD.drawString(decimalValue, col + pos, row, true);
			Delay.msDelay(200);
			if (button == Keys.ID_ENTER) 
			{
				return String.format(Locale.CANADA, "%1$" + limit + "." + floats + "f", value);
			}
			if (button == Keys.ID_ESCAPE) return null;
		}
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
//		while(buttons.getButtons() != Keys.ID_ENTER) {
//			if (buttons.getButtons() == Keys.ID_UP) {
//				chr = chr + 1;
//				if (chr>=alpha.length()) chr = 0;
//				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
//				in[pos] = alpha.substring(chr, chr+1);
//			} else if (buttons.getButtons() == Keys.ID_DOWN) {
//				chr = chr - 1;
//				if (chr<0) chr = alpha.length()-1;
//				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
//				in[pos] = alpha.substring(chr, chr+1);
//			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
//				in[pos] = alpha.substring(chr, chr+1);
//				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
//				pos = pos + 1;
//				if (pos > 13) pos = 0;
//				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
//				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
//			} else if (buttons.getButtons() == Keys.ID_LEFT) {
//				in[pos] = alpha.substring(chr, chr+1);
//				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
//				pos = pos - 1;
//				if (pos < 0) pos = 13;
//				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
//				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
//			}
//			Delay.msDelay(150);
//		}
//		in[pos] = alpha.substring(chr, chr+1);
//		Delay.msDelay(200);
//		for (int i = 0; i < in.length; i++) {
//			input = input+in[i];
//			System.out.println(input);
//		}
//		return input;
		while(true)
		{
			int button;
			do
			{
				button = buttons.waitForAnyPress();
			}
			
			while (button == 0);
			if (button == Keys.ID_UP) 
			{
				chr = chr + 1;
				if (chr>=alpha.length()) chr = 0;
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
				in[pos] = alpha.substring(chr, chr+1);
			}
			if (button == Keys.ID_DOWN) 
			{
				chr = chr - 1;
				if (chr<0) chr = alpha.length()-1;
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
				in[pos] = alpha.substring(chr, chr+1);
			}
			if (button == Keys.ID_RIGHT) 
			{
				in[pos] = alpha.substring(chr, chr+1);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
				pos = pos + 1;
				if (pos > 13) pos = 0;
				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
			}
			if (button == Keys.ID_LEFT) 
			{
				in[pos] = alpha.substring(chr, chr+1);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, false);
				pos = pos - 1;
				if (pos < 0) pos = 13;
				if (in[pos] != "") chr = alpha.indexOf(in[pos]);
				LCD.drawString(alpha.substring(chr, chr+1), pos, row, true);
			}
			if (button == Keys.ID_ENTER) 
			{
				in[pos] = alpha.substring(chr, chr+1);
				for (int i = 0; i < in.length; i++) {
					input = input+in[i];
				}
				return input;
			}
			if (button == Keys.ID_ESCAPE) return null;
		}
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
		buttons.waitForAnyPress(2000);
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

	private static void selectBot() 
	{
		String[] names = new String[botNames.size()];
		for (int i = 0; i < botNames.size(); i++)
		{
			names[i] = String.valueOf(botNames.get(i));
		}
		LCD.clear();
		TextMenu bots = new TextMenu(names, 1, "Choose a robot");
		option = bots.select(option);
		LCD.clear();
		if (option > 0) 
		{
			try {
				bot.loadSettings(names[option]);
				choice = 0;
				showMenu();
				return;
			} catch (FileNotFoundException e) {
				LCD.clear();
				LCD.drawString("No settings found", 0, 1);
				LCD.drawString("for " + names[option] + ".", 0, 2);
				LCD.drawString("Using default", 0, 3);
				LCD.drawString("settings.", 0, 4);
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

}