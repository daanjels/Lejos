package utility;

public class BrickFinder {
	private static Brick defaultBrick;

	public static Brick getDefault() {
		if (defaultBrick != null) return defaultBrick;
		Brick defaultBrick = new Brick();
		return defaultBrick;
	}
}
