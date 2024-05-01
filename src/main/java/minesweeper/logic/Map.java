package minesweeper.logic;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static minesweeper.Constants.*;

public class Map {
	// -----------------------------------------------
	// CLASS MEMBERS

	// Slots matrix
	final private Slot[][] matrix;

	// -----------------------------------------------
	// CLASS METHODS

	/** Builds a populated Map */
	public Map() {
		// Create the (empty) matrix
		matrix = new Slot[ROWS][COLUMNS];
		for (int theRow = 0; theRow < ROWS; theRow++) {
			for (int theColumn = 0; theColumn < COLUMNS; theColumn++) {
				// A new Slot for each position
				matrix[theRow][theColumn] = new Slot();
			}
		}
		// Populate the matrix
		populateMap();
	}
	
	// Getters

	public boolean isMine(int theRow, int theColumn) {
		return matrix[theRow][theColumn].isMine();
	}

	public boolean isHidden(int theRow, int theColumn) {
		return matrix[theRow][theColumn].isHidden();
	}

	public boolean isSuspicious(int theRow, int theColumn) {
		return matrix[theRow][theColumn].isSuspicious();
	}

	public boolean isQuestionMark(int theRow, int theColumn) {
		return matrix[theRow][theColumn].isQuestionMark();
	}

	// Setters

	public void setMine(int theRow, int theColumn) {
		matrix[theRow][theColumn].setMine(true);
	}

	public void setHasBeenVisitedThisTurn(int theRow, int theColumn, boolean valor) {
		matrix[theRow][theColumn].setVisitedThisTurn(valor);
	}

	private void clear(int theRow, int theColumn) {
		matrix[theRow][theColumn].setHidden(false);
		matrix[theRow][theColumn].setSuspicious(false);
		matrix[theRow][theColumn].setQuestionMark(false);
	}

	public void setSuspicious(int theRow, int theColumn) {
		matrix[theRow][theColumn].setSuspicious(true);
	}

	public void setFreeOfSuspicion(int theRow, int theColumn) {
		matrix[theRow][theColumn].setSuspicious(false);
	}

	public void setQuestionMark(int theRow, int theColumn) {
		matrix[theRow][theColumn].setQuestionMark(true);
	}

	public void clearQuestionMark(int theRow, int theColumn) {
		matrix[theRow][theColumn].setQuestionMark(false);
	}

	/**
	 * This is called to clear the 'hasBeenVisited' flag in all the game grid
	 */
	private void renewGameGrid() {
		for (var theRow = 0; theRow < ROWS; theRow++) {
			for (var theColumn = 0; theColumn < COLUMNS; theColumn++) {
				// New turn, nothing has been visited
				var slot = matrix[theRow][theColumn];
				slot.setVisitedThisTurn(false);
				slot.setMine(false);
				slot.setHidden(true);
				slot.setSuspicious(false);
				slot.setQuestionMark(false);
			}
		}
	}

	/** Has the current game ended? */
	public boolean hasWon() {
		// Is every bombed slot marked as suspicious?  Is there any hidden slot left?
		boolean ret = true;
		int theRow = 0;
		while ((theRow < ROWS) && ret) {
			int theColumn = 0;
			while (theColumn < COLUMNS && ret) {
				var slot = matrix[theRow][theColumn];
				// If there is any unmarked hidden slot, or a wrongly marked bombed
				//	slot, we are not yet done
				if ((slot.isHidden() && !slot.isSuspicious()) || (slot.isHidden() && !slot.isMine())) {
					ret = false;
				}
				theColumn++;
			}
			theRow++;
		}
		return ret;

	}

	/**
	 * Critical part of the program: this must check if a player's click has
	 * stept into a bomb
	 */
	public void click(int theRow, int theColumn) throws BombException {
		if (!isSuspicious(theRow, theColumn)) {
			// Bomb found? Then throw exception to be managed
			if (matrix[theRow][theColumn].isMine()) throw new BombException(theRow, theColumn);
			else // Clear recursively the game board around the slot
				clickI(theRow, theColumn);
		}
	}	

	// Recursive procedure to clear the adjacent slots
	private void clickI(int theRow, int theColumn) {
		var slot = matrix[theRow][theColumn];
		// If it has a bomb, do nothing
		// If it has been 'visited' this turn, we do nothing
		// If it was revealed, we do nothing
		if (!slot.isMine() && !slot.isVisitedThisTurn() && slot.isHidden()) {
			// We visit it!
			slot.setVisitedThisTurn(true);
			// Call myself again for the adjacent slots (clockwise)
			if (getMinesAround(theRow, theColumn) == 0) {
				// Upper row
				if (theRow > 0) {
					if (theColumn > 0) {
						// Up-left
						clickI(theRow - 1, theColumn - 1);
					}
					// Up
					clickI(theRow - 1, theColumn);
					if (theColumn < COLUMNS - 1) {
						// Up right
						clickI(theRow - 1, theColumn + 1);
					}
				}
				// To the right
				if (theColumn < COLUMNS - 1) {
					clickI(theRow, theColumn + 1);
				}
				// Lower row
				if (theRow < ROWS - 1) {
					if (theColumn < COLUMNS - 1) {
						// Down-right
						clickI(theRow + 1, theColumn + 1);
					}
					// Down
					clickI(theRow + 1, theColumn);
					if (theColumn > 0) {
						// DownLeft
						clickI(theRow + 1, theColumn - 1);
					}
				}
				// To the left
				if (theColumn > 0) {
					clickI(theRow, theColumn - 1);
				}
			}

			// Reveal the slot
			clear(theRow, theColumn);
		}
	}

	/**
	 * It clears the game board and populates it with a number of bombs in
	 * random locations
	 */
	public void populateMap() {
		renewGameGrid();
		// Randomly place mines
		var minesPlaced = 0;
		var g = new Random();
		while (minesPlaced < NUMBER_OF_MINES) {
			// Generate coordinates for the mine
			var row = g.nextInt(ROWS);
			var column = g.nextInt(COLUMNS);
			// Place it if it was clear
			// Maybe the coordinates are not valid, we need a new turn of the loop
			if (!isMine(row, column)) {
				minesPlaced++;
				setMine(row, column);
			}
		}
	}

	/**
	 * Counts the number of remaining mines for the player.  Beware that it must
	 * make its count with the number of flags that the player has placed.
	 */
	public int remainingMines() {
		return NUMBER_OF_MINES - countGuessedMines();
	}

	// It counts the number of slots marked as 'suspicious' by the player
	private int countGuessedMines() {
		var counter = 0;
		for (int theRow = 0; theRow < ROWS; theRow++) {
			for (int theColumn = 0; theColumn < COLUMNS; theColumn++) {
				// If it is hidden, add 1 to the counter
				if (matrix[theRow][theColumn].isSuspicious()) {
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * Calculates the number of mines around a given slot
	 */
	public int getMinesAround(int theRow, int theColumn) {
		var ret = 0;
		// Upper row
		if (theRow > 0) {
			if (theColumn > 0) {
				// Up-left
				if (isMine(theRow - 1, theColumn - 1)) {
					ret++;
				}
			}
			// Up
			if (isMine(theRow - 1, theColumn)) {
				ret++;
			}
			if (theColumn < COLUMNS - 1) {
				// Up-right
				if (isMine(theRow - 1, theColumn + 1)) {
					ret++;
				}
			}
		}
		// To the right
		if (theColumn < COLUMNS - 1) {
			if (isMine(theRow, theColumn + 1)) {
				ret++;
			}
		}
		// Lower row
		if (theRow < ROWS - 1) {
			if (theColumn < COLUMNS - 1) {
				// Down-right
				if (isMine(theRow + 1, theColumn + 1)) {
					ret++;
				}
			}
			// Down
			if (isMine(theRow + 1, theColumn)) {
				ret++;
			}
			if (theColumn > 0) {
				// Down-left
				if (isMine(theRow + 1, theColumn - 1)) {
					ret++;
				}
			}
		}
		// To the left
		if (theColumn > 0) {
			if (isMine(theRow, theColumn - 1)) {
				ret++;
			}
		}
		return ret;
	}

	@Setter
    @Getter
    private static class Slot {
		// -----------------------------------------------
		// CLASS MEMBERS

		// Visited this turn?
		private boolean visitedThisTurn = false;
		// Does it have a mine under it?
		private boolean mine = false;
		// Still hidden?
		private boolean hidden = true;
		// Has the played dimmed it suspicious?
		private boolean suspicious = false;
		// Marked with a question mark?
		private boolean questionMark = false;

		// -----------------------------------------------
		// CLASS METHODS

		// Constructor
		public Slot() {
		}

	}

}