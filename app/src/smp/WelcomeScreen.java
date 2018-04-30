package smp;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.*;

/**
 * Welcome screen for the application. This screen includes SMP's logo, motto, and a 
 * message indicating to the user to click the 'Import Library' button so that they
 * can navigate to a directory in the system where they save their songs.
 */
public class WelcomeScreen implements ActionListener {

	private static JFrame frame;

	private JLabel welcome;
	private JLabel title;
	private JLabel subtitle;
	private JLabel navigate;

	private JButton importButton;
	
	private JFileChooser chooser;
	
	private String directory;

	/**
	 * Default constructor.
	 */
	public WelcomeScreen() {
		frame = new JFrame("Welcome to SMP!");
	}
	
	/**
	 * Creates and displays the components of the welcome screen.
	 */
	public void createWelcomeScreen() {
		
		welcome = new JLabel("Welcome to SMP!");
		welcome.setFont(new Font("Arial", 1, 21));
		welcome.setForeground(Color.WHITE);
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		title = new JLabel();
		title.setIcon(new ImageIcon(getClass().getResource("/images/logo.png")));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		subtitle = new JLabel("The simplest music player in the world.");
		subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
		subtitle.setForeground(Color.WHITE);
		subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		navigate = new JLabel("Please navigate to your music library: ");
		navigate.setFont(new Font("Arial", 1, 12));
		navigate.setForeground(Color.WHITE);
		navigate.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		importButton = new JButton("Import Library");
		importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		importButton.addActionListener(this);
		
		Box vert = Box.createVerticalBox();

		vert.add(welcome);
		vert.add(title);
		vert.add(subtitle);
		vert.add(navigate);
		vert.add(importButton);
		
		((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		welcome.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		subtitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		navigate.setBorder(BorderFactory.createEmptyBorder(40,10,10,10));

		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(vert, BorderLayout.CENTER);	
		
		frame.pack();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * Finds the directory that the user has saved their songs to. Then, 
	 * opens the application's main display.
	 * 
	 * @param e - action event for when the user clicks the 'Import Library' button
	 */
	public void actionPerformed(ActionEvent e) {

		directory = importLibrary();
		
		try { 
			frame.dispose();
			Display d = new Display(directory);
			d.createDisplay();
		} 
		catch (Exception exception) {}
	}
	
	/**
	 * Opens a file explorer for the user to navigate to their library of songs
	 * and returns a string representation of the path to the library of songs.
	 * 
	 * @return String - directory to the user's library of songs
	 */
	public String importLibrary() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("File Explorer");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) 
			directory = chooser.getSelectedFile().toString().replace("\\", "/") + "/";
		
		return directory;
	}
}
