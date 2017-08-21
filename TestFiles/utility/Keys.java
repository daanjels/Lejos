package utility;

public class Keys {
	public static final int ID_UP = 0x1;
	public static final int ID_ENTER = 0x2;
	public static final int ID_DOWN = 0x4;
	public static final int ID_RIGHT = 0x8;
	public static final int ID_LEFT = 0x10;
	public static final int ID_ESCAPE = 0x20;
	public static final int ID_ALL = 0x3f;

	public Keys() {
		
	}
	
	public int getButtons() {
		int key = 0;
		while (LCD.keyPressed == 0) {
			LCD.drawString("waiting", 0, 6);
		}
		key = LCD.keyPressed;
		return key;
	}

	public int waitForAnyPress() {
		Delay.msDelay(2000);
		return 1;
	}

}
