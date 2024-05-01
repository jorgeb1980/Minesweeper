package minesweeper.gui;

import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
	// ----------------------------------------------
	// CLASS METHODS

	public Main() {
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Main window, with a grid bag layout
		var gridBagLayout1 = new GridBagLayout();
		var mainWindow = new JFrame();
		mainWindow.getContentPane().setLayout(gridBagLayout1);
		// Aspect and behavior of the main window
		mainWindow.setLocation(200, 200);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setSize(570, 500);
		mainWindow.setTitle("Minesweeper");
		// Create a game panel
		var pj = new GamePanel();
		pj.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		mainWindow.getContentPane().add(
				pj,
				new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(10, 10, 10, 10), 0, 0)
		);
		// Show the panel (in a centered position)
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
	}

}