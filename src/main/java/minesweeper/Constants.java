package minesweeper;

import java.awt.Color;

/**
 * Originally created july - 15 - 2005 - been playing with this for a long time!
 * :')
 */
public interface Constants {

	// Paths for the images (gif)
	String QUESTION_MARK_PATH = "questionMark.gif";
	String SMILEY_PATH = "smiley.gif";
	String WRONG_FLAG_PATH = "wrongFlag.gif";
	String FLAG_PATH = "flag.gif";
	String MINE_PATH = "mine.gif";
	String RED_MINE_PATH = "redMine.gif";

	// Font for the numbers shown
	String FONT = "Dialog";

	// Start again
	String RESET = "Volver a empezar";

	// Slot marked as suspicious by the user
	char X = 'x';

	// Font size
	int FONT_SIZE = 7;

	// Width and height for the box with the remaining mines information
	int BOX_WIDTH = 55;
	int BOX_HEIGHT = 20;

	// Reset the clock
	String ZERO = "0";

	// Slots: width and height
	int SLOT_WIDTH = 15;
	int SLOT_HEIGHT = 15;

	// Empty slot content
	String EMPTY_SLOT_TEXT = "V";

	// Ending messages
	String CONGRATULATIONS = "Enhorabuena";
	String YOU_HAVE_FINISHED_IN = "Has acabado en ";
	String SECONDS = " segundos";

	// Background colors
	Color HIDDEN_BACKGROUND = Color.LIGHT_GRAY;
	Color VISIBLE_BACKGROUND = Color.WHITE;

	// Clock width and height
	int CLOCK_WIDTH = 40;
	int CLOCK_HEIGHT = 20;

	// Map rows
	int ROWS = 16;

	// Map columns
	int COLUMNS = 30;

	// Number of mines
	int NUMBER_OF_MINES = 99;

}
