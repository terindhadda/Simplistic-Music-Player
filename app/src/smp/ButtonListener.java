package smp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.LogManager;

import javax.swing.JTable;

import algorithms.BinarySearch;
import algorithms.KMP;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * Listens for a user click on one of the music functionality buttons 
 * (i.e. Back, Play/Pause, Next, Search) and does the respective task.
 */
public class ButtonListener implements ActionListener {

	private static String status = "NOT PLAYING";
	
	private static int currentRow = 0;
	private static int lastRow = 0;
	private static int rowToHighlight = 0;
	
	private static JTable table;
	private static Song[] library;
	private static Song currentSong;
	private static BasicPlayer player;
	
	/**
	 * On a user click, perform the task according to the button.
	 * 
	 * @param e - action event for when the user clicks a music function button
	 */
	public void actionPerformed(ActionEvent e) {
		
		// this line is added to 'hide' the thread messages from the BasicPlayer API
		LogManager.getLogManager().reset();
		
		table = Display.getTable();
		library = Display.getLibrary();
		lastRow = currentRow;
		currentRow = table.getSelectedRow();
		
		// play/pause a song
		if (e.getActionCommand().equals("Play/Pause")) {
			
			// if we are on a new row, play the new song
			if (currentRow != lastRow && currentRow != -1) {
				stopSong();
				currentSong = library[currentRow];
				lastRow = currentRow;
				status = "PLAYING";
				playSong();
			}
			
			else {
			
				// if this is first song being play, play the song
				if (table.getSelectedRow() != -1 && status.equals("NOT PLAYING")) {
					currentSong = library[currentRow];
					status = "PLAYING";
					playSong();
				} 
				
				// pause the song is playing
				else if (status.equals("PLAYING")) {
					status = "PAUSED";
					pauseSong();
				}
				
				// resume the song if paused
				else if (status.equals("PAUSED")) {
					status = "PLAYING";
					resumeSong();
				} 
				
				// play the first song in the library if no songs are selected
				else if (table.getSelectedRow() == -1) {
					table.setRowSelectionInterval(0, 0);
					currentSong = library[0];
					currentRow = 0;
					lastRow = currentRow;
					status = "PLAYING";
					playSong();
				}
			}
		}
		
		// go back one row in the music library 
		if (e.getActionCommand() == "Back")
			if (currentRow-1 >= 0) table.setRowSelectionInterval(currentRow-1, currentRow-1);
		
		// go next one row in the music library
		if (e.getActionCommand() == "Next")
			if (currentRow+1 < table.getRowCount()) table.setRowSelectionInterval(currentRow+1, currentRow+1);
		
		// search for what the user entered into the text field, highlight the corresponding match if there is one
		if (e.getActionCommand() == "Search") {		
			rowToHighlight = 0;
			for (Song s : library) {
				if (search(s.name())) break;
				if (search(s.artist())) break;
				if (search(s.album())) break;
		    	rowToHighlight++;
			}
		}
	}
	
	/**
	 * Search for a match between what the user entered into the text field 
	 * and the user's song library names, artists, and albums. This search
	 * is performed by the famous KMP substring algorithm.
	 * 
	 * @param text - text to search for
	 * @return boolean - whether a match is found or not
	 */
	private static boolean search(String text) {
		String pattern = Display.getSearch();
        	
	    KMP kmp = new KMP(pattern);
        int offset = kmp.search(text);
        	
    	if (offset != -1) {
    		Display.updateSearchFound();
			table.setRowSelectionInterval(rowToHighlight, rowToHighlight);
			return true;
    	}
        Display.updateSearchNotFound();
        return false;
	}
	
	/**
	 * Stop playing the current song.
	 */
	private static void stopSong() {
		try { player.stop(); } 
		catch (BasicPlayerException e) { e.printStackTrace(); }
		catch (NullPointerException e) {}
	}
	
	/**
	 * Play the current song and update the it's information on display.
	 */
	private static void playSong() {
		File file = new File(findSong());
		player = new BasicPlayer();
		try {
			player.open(file);
			player.play();
		} catch (BasicPlayerException e1) { e1.printStackTrace(); }

		Display.updateCurrentSong(library[table.getSelectedRow()].name());
		Display.updateCurrentArtist(library[table.getSelectedRow()].artist());
		Display.updateCurrentAlbum(library[table.getSelectedRow()].album());
		Display.updatePlayPauseButton("/images/pause.png");
	}
	
	/**
	 * Resume the currently paused song.
	 */
	private static void resumeSong() {
		try { player.resume(); } 
		catch (BasicPlayerException e1) { e1.printStackTrace(); }
		Display.updatePlayPauseButton("/images/pause.png");
	}
	
	/**
	 * Pause the currently playing song.
	 */
	private static void pauseSong() {
		try { player.pause(); } 
		catch (BasicPlayerException e1) { e1.printStackTrace(); }
		Display.updatePlayPauseButton("/images/play.png");
	}
	
	/**
	 * Helper method for search(). Using the Binary Search algorithm, find
	 * the currently selected song in the library to get its path.
	 * 
	 * @return String - path for the current song
	 */
	private static String findSong() {
		int index = BinarySearch.search(currentSong, library);
		if (index == -1) return "";
		return library[index].path();	
	}
}
