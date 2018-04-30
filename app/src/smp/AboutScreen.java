package smp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * An about screen for the application. Contains information about SMP, how it
 * is used, and credits.
 */
public class AboutScreen {

	private static JFrame frame;
	
	private JLabel title;
	private JLabel subtitle;
	
	private JLabel about;
	private JLabel aboutExplanation;
	
	private JLabel howToUse;
	private JLabel howToUseExplanation;
	
	private JLabel credits;
	private JLabel credit1;
	private JLabel credit2;
	private JLabel credit3;
	
	/**
	 * Default constructor.
	 */
	public AboutScreen() {
		frame = new JFrame("About");
	}
	
	/**
	 * Creates and displays the components of the about screen.
	 */
	public void createAboutScreen() {
		
		title = new JLabel();
		title.setIcon(new ImageIcon(getClass().getResource("/images/logo.png")));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		subtitle = new JLabel("The simplest music player in the world.");
		subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
		subtitle.setForeground(Color.WHITE);
		subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		about = new JLabel("ABOUT");
		about.setFont(new Font("Arial", 1, 21));
		about.setAlignmentX(Component.CENTER_ALIGNMENT);
		about.setForeground(Color.WHITE);
		
		aboutExplanation = new JLabel("<html>"
				+ "Simplistic Music Player (SMP) is a music playing application designed to be the most simple, "
				+ "easy to use, and lighweight media software available. It uses advanced API's to implement only "
				+ "the necessary music playing functionality, modern algorithms to search and load your songs quickly, "
				+ "and Java's Swing framework to give it a sharp looking design!"
				+ "</html>");
		aboutExplanation.setAlignmentX(Component.CENTER_ALIGNMENT);
		aboutExplanation.setForeground(Color.WHITE);
		
		howToUse = new JLabel("HOW TO USE");
		howToUse.setFont(new Font("Arial", 1, 21));
		howToUse.setAlignmentX(Component.CENTER_ALIGNMENT);
		howToUse.setForeground(Color.WHITE);
		
		howToUseExplanation = new JLabel("<html>"
				+ "To use SMP, simply import your current library of songs (File > Import New Library). Then, "
				+ "you can select songs by clicking on them or using the 'Back' and 'Next' buttons. To play a song, "
				+ "click the 'Play' button, and to pause a song, click the 'Pause' button."
				+ "</html>");
		howToUseExplanation.setAlignmentX(Component.CENTER_ALIGNMENT);
		howToUseExplanation.setForeground(Color.WHITE);


		credits = new JLabel("CREDITS");
		credits.setFont(new Font("Arial", 1, 21));
		credits.setAlignmentX(Component.CENTER_ALIGNMENT);
		credits.setForeground(Color.WHITE);
		
		credit1 = new JLabel("Terin Dhadda");
		credit1.setAlignmentX(Component.CENTER_ALIGNMENT);
		credit1.setForeground(Color.WHITE);

		credit2 = new JLabel("Shane DeSouza");
		credit2.setAlignmentX(Component.CENTER_ALIGNMENT);
		credit2.setForeground(Color.WHITE);
		
		credit3 = new JLabel("Abdullah Muhammad");
		credit3.setAlignmentX(Component.CENTER_ALIGNMENT);
		credit3.setForeground(Color.WHITE);

		Box vert = Box.createVerticalBox();

		vert.add(title);
		vert.add(subtitle);
		vert.add(about);
		vert.add(aboutExplanation);
		vert.add(howToUse);
		vert.add(howToUseExplanation);
		vert.add(credits);
		vert.add(credit1);
		vert.add(credit2);
		vert.add(credit3);

		((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		subtitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		about.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		howToUse.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		credits.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(vert, BorderLayout.CENTER);	
		
		frame.pack();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setSize(400, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
