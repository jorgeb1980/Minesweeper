package minesweeper;

import java.awt.Color;

/**
 * Originally created july - 15 - 2005 - been playing with this for a long time!
 * :')
 */
public interface Constants {

	// Paths for the images (gif)
	public static final String QUESTION_MARK_PATH = "questionMark.gif";
	public static final String SMILEY_PATH = "smiley.gif";
	public static final String WRONG_FLAG_PATH = "wrongFlag.gif";
	public static final String FLAG_PATH = "flag.gif";
	public static final String MINE_PATH = "mine.gif";
	public static final String RED_MINE_PATH = "redMine.gif";

	// Font for the numbers shown
	public static final String FONT = "Dialog";

	// Start again
	public static final String RESET = "Volver a empezar";

	// Slot marked as suspicious by the user
	public static final char X = 'x';

	// Font size
	public static final int FONT_SIZE = 7;

	// Width and height for the box with the remaining mines information
	public static final int BOX_WIDTH = 55;
	public static final int BOX_HEIGHT = 20;

	// Reset the clock
	public static final String ZERO = "0";

	// Slots: width and height
	public static final int SLOT_WIDTH = 15;
	public static final int SLOT_HEIGHT = 15;

	// Empty slot content
	public static final String EMPTY_SLOT_TEXT = "V";

	// Ending messages
	public static final String CONGRATULATIONS = "Enhorabuena";
	public static final String YOU_HAVE_FINISHED_IN = "Has acabado en ";
	public static final String SECONDS = " segundos";

	// Background colors
	public static final Color HIDDEN_BACKGROUND = Color.LIGHT_GRAY;
	public static final Color VISIBLE_BACKGROUND = Color.WHITE;

	// Clock width and height
	public static final int CLOCK_WIDTH = 40;
	public static final int CLOCK_HEIGHT = 20;

	// Map rows
	public static final int ROWS = 16;

	// Map columns
	public static final int COLUMNS = 30;

	// Number of mines
	public static final int NUMBER_OF_MINES = 99;

}
