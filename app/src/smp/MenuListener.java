package smp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listens for a user click on one of the file menu options and does 
 * the respective task.
 */
public class MenuListener implements ActionListener {

	/**
	 * On a user click, perform the task according to the file menu item.
	 * 
	 * @param e - action event for when the user clicks a file menu option
	 */
	public void actionPerformed(ActionEvent e) {
		
		// open the about screen
		if (e.getActionCommand().equals("About")) {
			AboutScreen as = new AboutScreen();
			as.createAboutScreen();
		}
		
		// open the file explorer, close current display, open a new one
		if (e.getActionCommand().equals("Import New Library")) {
			WelcomeScreen ws = new WelcomeScreen();	
			String newDirectory = ws.importLibrary();
			Display.close();
			try { 
				Display d = new Display(newDirectory);
				d.createDisplay(); 
			}
			catch (Exception exception) {}
		}
		
		// exit the application
		if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}
}
