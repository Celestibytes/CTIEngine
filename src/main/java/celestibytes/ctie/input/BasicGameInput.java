package celestibytes.ctie.input;

import celestibytes.ctie.util.Direction;

import celestibytes.lib.Tuple;

public class BasicGameInput {
	
	private static Direction playerMovingX, playerMovingY;
	
	public static void setPlayerMoving(Direction x, Direction y) {
		playerMovingX = x;
		playerMovingY = y;
	}
	
	public static void setPlayerMovingX(Direction x) {
		playerMovingX = x;
	}
	
	public static void setPlayerMovingY(Direction y) {
		playerMovingY = y;
	}
	
	public static Tuple<Direction, Direction> getPlayerMoving() {
		return new Tuple<Direction, Direction>(playerMovingX, playerMovingY);
	}
	
	public static Direction getPlayerMovingX() {
		return playerMovingX;
	}
	
	public static Direction getPlayerMovingY() {
		return playerMovingY;
	}
	
	public static void clearInput() {
		playerMovingX = Direction.CENTER;
		playerMovingY = Direction.CENTER;
	}
	
	
}
