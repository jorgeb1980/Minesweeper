package minesweeper.logic;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BombException extends Exception {
	// ---------------------------------------------------
	// CLASS MEMBERS

	@Serial
	private static final long serialVersionUID = 3683219459388761408L;
	// Row and column
	private int row;
	private int column;

	// ---------------------------------------------------
	// CLASS METHODS

	/**
	 * Constructor
	 *
	 * @param theRow    Row of the bomb location
	 * @param theColumn Column of the bomb location
	 */
	public BombException(int theRow, int theColumn) {
		row = theRow;
		column = theColumn;
	}
}