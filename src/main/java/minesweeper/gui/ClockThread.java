package minesweeper.gui;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClockThread extends Thread {
	// -------------------------------------------------
	// CLASS CONSTANTS

	// A second expressed in milliseconds
	public static final long SECOND_TIME_MILLIS = 1000;

	// -------------------------------------------------
	// CLASS MEMBERS

	// Singleton instance of the thread (now I woud do it with an Enumeration,
	// probably)
	private static ClockThread instance;

	// Seconds counter
	private long seconds = 0;

	// Reference to the graphic element with the seconds that have
	// passed since the start of the game
	private JTextField clock = null;

	// -------------------------------------------------
	// CLASS METHODS

	// Se deja privado el constructor como parte del patrón Singleton,
	// para evitar que se instancien ejemplares del hilo fuera de nuestro
	// control.
	private ClockThread(JTextField reloj) {
		clock = reloj;
		seconds = 0;
	}

	// Resets the counter
	public static void reset(JTextField reloj) {
		if (instance != null) {
			// Tries to interrupt the current thread. If any problem raises,
			// we get an InterruptedException; if not, we just change our state
			instance.interrupt();
		}
		// Leave the former instance for the garbage collector
		instance = null;
		// Create a new instance
		instance = new ClockThread(reloj);
	}

	// This one will make the thread start the counter
	public static void startGame() {
		instance.start();
	}

	// Will just update the clock every XXX milliseconds
	public void run() {
		try {
			// The thread will continue, unless interrupted from the outside
			while (!interrupted()) {
				// Paint the time in the GUI (using invokeLater in order not to
				// modify
				// UI elements from another thread)
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						clock.setText(Long.toString(seconds++));
					}
				});
				// Wait for a second
				sleep(SECOND_TIME_MILLIS);
			}
		} catch (Exception e) {
			// TODO: log properly
			e.printStackTrace();
		}
	}

}