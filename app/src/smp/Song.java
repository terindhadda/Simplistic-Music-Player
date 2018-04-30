package smp;

/**
 * A song is defined by a path to the song in the user's system, a name, an
 * artist, and album, and a length.
 *
 */
public class Song {

	private String path;
	private String name;
	private String artist;
	private String album;
	private long length;
	
	/**
	 * Constructor.
	 * 
	 * @param pa - path to song in user's system
	 * @param na - name of song
	 * @param ar - artist of song
	 * @param al - album of song
	 * @param le - length of song in seconds
	 */
	public Song(String pa, String na, String ar, String al, long le) {
		this.path = pa;
		this.name = na;
		this.artist = ar;
		this.album = al;
		this.length = le;
	}
	
	// Getters
	public String path() 	{ return path; }
	public String name() 	{ return name; }
	public String artist()	{ return artist; }
	public String album() 	{ return album; }
	public long length() 	{ return length; }
	
	public String formattedLength()	{
		String s = length/60 + ":";
		if (length%60 < 10) s += "0" + length%60;
		else s += length%60;
		return s;
	}
	
	/**
	 * Songs are compared by name using the String's compareTo method. This
	 * must be implemented to use for merge sort.
	 * 
	 * @param that - Song to compare to
	 * @return int - integer representation of the result
	 * 				 1 means this > that
	 * 				 0 means this = that
	 * 				 -1 means this < that
	 */
	public int compareTo(Song that) {
		return this.name.compareTo(that.name());
	}
}
