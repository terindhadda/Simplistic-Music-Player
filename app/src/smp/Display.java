package smp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import algorithms.MergeSort;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class Display {

	private static final String[] headers = {"Song", "Artist", "Album", "Length"};
   
	private static JFrame frame;
	
	private JMenuBar menuBar;
    private JMenu fileMenu;
    
	private JMenuItem aboutItem;
	private JMenuItem importItem;
	private JMenuItem exitItem;
	
	private JLabel title;
	private JLabel subtitle;
	
	private JButton backButton;	
	private static JButton playPauseButton;
	private JButton nextButton;
	
	private JLabel song;	private static JLabel currentSong;
	private JLabel artist;	private static JLabel currentArtist;
	private JLabel album;	private static JLabel currentAlbum;
	
	private static JLabel search;
	private static JTextField searchBox;
	private JButton searchButton;
	
	private static ArrayList<Song> temp = new ArrayList<Song>();
	private static Song[] library;
	private static String[][] songList;
	
	private static JTable table;
	private JScrollPane scrollPane;
	
	private static String directory;

	/**
	 * Constructor.
	 * 
	 * @param dir - directory of the user's song library
	 */
	public Display(String dir) {
		frame = new JFrame("SMP");
		directory = dir;
	}
	
	/**
	 * Creates and displays components of the main application screen.
	 */
	public void createDisplay() {
		
    	menuBar = new JMenuBar();                                      
    	fileMenu = new JMenu("File");
    	menuBar.add(fileMenu);
    	
    	aboutItem = new JMenuItem("About");
    	importItem = new JMenuItem("Import New Library");
    	exitItem = new JMenuItem("Exit");
    	
    	aboutItem.addActionListener(new MenuListener());
    	importItem.addActionListener(new MenuListener());
    	exitItem.addActionListener(new MenuListener());
    	
    	fileMenu.add(aboutItem);
    	fileMenu.addSeparator();
    	fileMenu.add(importItem);
    	fileMenu.addSeparator();
    	fileMenu.add(exitItem);
		
		title = new JLabel();
		title.setIcon(new ImageIcon(getClass().getResource("/images/logo.png")));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		subtitle = new JLabel("The simplest music player in the world.");
		subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
		subtitle.setForeground(Color.WHITE);
		subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", 0, 0));
		backButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		backButton.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton.setIcon(new ImageIcon(getClass().getResource("/images/back.png")));
		backButton.addActionListener(new ButtonListener());

		playPauseButton = new JButton("Play/Pause");
		playPauseButton.setFont(new Font("Arial", 0, 0));
		playPauseButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		playPauseButton.setHorizontalTextPosition(SwingConstants.CENTER);
		playPauseButton.setIcon(new ImageIcon(getClass().getResource("/images/play.png")));
		playPauseButton.addActionListener(new ButtonListener());

		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Arial", 0, 0));
		nextButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		nextButton.setHorizontalTextPosition(SwingConstants.CENTER);
		nextButton.setIcon(new ImageIcon(getClass().getResource("/images/next.png")));
		nextButton.addActionListener(new ButtonListener());
			
		song = new JLabel("Song: ");		currentSong = new JLabel(" ");
		artist = new JLabel("Artist: ");	currentArtist = new JLabel(" ");
		album = new JLabel("Album: ");		currentAlbum = new JLabel(" ");
		
		song.setForeground(Color.WHITE);	currentSong.setForeground(Color.WHITE);
		artist.setForeground(Color.WHITE);	currentArtist.setForeground(Color.WHITE);
		album.setForeground(Color.WHITE);	currentAlbum.setForeground(Color.WHITE);
	
		currentSong.setFont(new Font("Arial", Font.ITALIC, 12));
		currentArtist.setFont(new Font("Arial", Font.ITALIC, 12));
		currentAlbum.setFont(new Font("Arial", Font.ITALIC, 12));
		
		search = new JLabel(" ");
		search.setForeground(Color.WHITE);
		search.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchBox = new JTextField("Search");
		searchBox.setFont(new Font("Arial", Font.ITALIC, 12));
		clearSearchBoxOnClick();
		searchBox.setHorizontalAlignment(0);
		searchButton = new JButton("Search");
		searchButton.setFont(new Font("Arial", 0, 0));
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchButton.setIcon(new ImageIcon(getClass().getResource("/images/search.png")));
		searchButton.addActionListener(new ButtonListener());

		try { getSongs(new File(directory)); }
		catch (Exception e) {System.out.println("error"); }
		songList = new String[library.length][4];
		for (int i = 0; i  < library.length; i++) {
			songList[i][0] = library[i].name();
			songList[i][1] = library[i].artist();
			songList[i][2] = library[i].album();
			songList[i][3] = library[i].formattedLength();
		}
		
		table = new JTable(songList, headers);
		table.setShowGrid(false);		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		makeCellsUneditable(table);
		removeEnterKeyBinding(table);

		scrollPane = new JScrollPane(table);

		Box vert = Box.createVerticalBox();
		Box titles = Box.createVerticalBox();
		Box vertLabels = Box.createVerticalBox();
		Box vertVolume = Box.createVerticalBox();
		Box vertSearch = Box.createVerticalBox();
		Box horzButtons = Box.createHorizontalBox();
		Box horzSong = Box.createHorizontalBox();
		Box horzArtist = Box.createHorizontalBox();
		Box horzAlbum = Box.createHorizontalBox();
		Box songInfo = Box.createHorizontalBox();
		Box coverBox = Box.createHorizontalBox();
		Box horzSearch = Box.createHorizontalBox();
		
		titles.add(title);
		titles.add(subtitle);
		
		Box horzTop = Box.createHorizontalBox();
		
		horzSong.setAlignmentX(Box.LEFT_ALIGNMENT);
		horzArtist.setAlignmentX(Box.LEFT_ALIGNMENT);
		horzAlbum.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		horzSong.add(song); 	horzSong.add(currentSong);		vertLabels.add(horzSong);
		horzArtist.add(artist); horzArtist.add(currentArtist);	vertLabels.add(horzArtist);
		horzAlbum.add(album); 	horzAlbum.add(currentAlbum);	vertLabels.add(horzAlbum);
		
		songInfo.add(vertLabels);
		
		horzTop.add(titles);
		horzTop.add(songInfo);
		
		horzButtons.add(backButton); horzButtons.add(playPauseButton); horzButtons.add(nextButton);	
		
		
		horzSearch.add(searchBox); horzSearch.add(searchButton);
		vertSearch.add(search); vertSearch.add(horzSearch);

		vert.add(horzTop);
		vert.add(horzButtons);
		vert.add(vertSearch);
		vert.add(scrollPane);
		
		subtitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		horzButtons.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		horzTop.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		songInfo.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		coverBox.setBorder(BorderFactory.createEmptyBorder(0,0,0,30));
		vertVolume.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		vertSearch.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		((JComponent) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(vert, BorderLayout.CENTER);	
		
		frame.pack();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setSize(600, 700);
		frame.setJMenuBar(menuBar);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	// Getters
	public static JTable getTable() { return table; }
	public static Song[] getLibrary() { return library; }
	public static String getSearch() { return searchBox.getText(); }
	
	// Updates display according 
	public static void updateCurrentSong(String s) { currentSong.setText(s); }
	public static void updateCurrentArtist(String s) { currentArtist.setText(s); }
	public static void updateCurrentAlbum(String s) { currentAlbum.setText(s); }
	public static void updateSearchFound() { search.setText("Found!"); }
	public static void updateSearchNotFound() { search.setText("Not Found!"); }
	public static void updateSearchReset() { search.setText(" "); }
	public static void updatePlayPauseButton(String s) { playPauseButton.setIcon(new ImageIcon(Display.class.getResource(s))); }
	
	/**
	 * Closes the display.
	 */
	public static void close() {
		frame.dispose();
	}
	
	/**
	 * When a user clicks the search box, clear anything currently written in it.
	 * 
	 * @source www.stackoverflow.com
	 */
	private void clearSearchBoxOnClick() {
		searchBox.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
        		updateSearchReset();
                searchBox.setText("");
        		searchBox.setFont(new Font("Arial", 1, 12));
            }
        });
	}
	
	/**
	 * Makes the cells of the user's song library (table) uneditable.
	 * 
	 * @param table - the user's song library to make uneditable
	 * @source www.stackoverflow.com
	 */
	private void makeCellsUneditable(JTable table) {
		for (int c = 0; c < table.getColumnCount(); c++)
		{
		    Class<?> col_class = table.getColumnClass(c);
		    table.setDefaultEditor(col_class, null);        // remove editor
		}
	}
	
	/**
	 * Remove the "ENTER" key binding on a jtable.
	 * 
	 * @param table - jtable to remove key binding from.
	 * @source www.stackoverflow.com
	 */
	private void removeEnterKeyBinding(JTable table) {
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		    table.getActionMap().put("Enter", new AbstractAction() {
				private static final long serialVersionUID = 1L;

		        public void actionPerformed(ActionEvent ae) {}
		    });
	}
	
	/**
	 * Get the songs from the user's song library and add them to an array
	 * of Song types. Then, perform a Merge Sort algorithm on this array
	 * to order it (to use for binary search later).
	 * 
	 * @param folder - the user's song library
	 */
	public static void getSongs(File folder) {

		try {
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            getSongs(fileEntry);
		        } else {
		            String path = directory + fileEntry.getName();
		            Mp3File song = null;
					try { song = new Mp3File(path); }
					catch (Exception e) {}
					if (song.hasId3v2Tag()){
					    ID3v2 tag = song.getId3v2Tag();
					    Song s = new Song(path, tag.getTitle(), tag.getArtist(), tag.getAlbum(), song.getLengthInSeconds());
						temp.add(s);
					}
		        }
		    }
		} 
		catch (Exception e) {}

	    library = new Song[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
        	library[i] = temp.get(i);
        }
        
        MergeSort.sort(library);        
	}
}


