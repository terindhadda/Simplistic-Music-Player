package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import smp.*;
import algorithms.*;

/**
 * This class is provided to test the algorithmic content used in SMP, specifically, it
 * breaks down the test cases by:
 * 		- Merge Sort
 * 		- Binary Search
 * 		- KMP
 * 
 * This White-Box testing technique tests an array of five generic songs and covers
 * the minimum (i.e. canonical sequence) cases.
 */
public class Testing {
	
	private Song[] songs;	// test array of generic songs
	
	/**
	 * Create the test array of five generic songs.
	 */
	@Before
	public void setUp() {
		songs = new Song[5];
		songs[0] = new Song("path1", "Name1", "Artist1", "Album1", 100);
		songs[1] = new Song("path2", "Name2", "Artist2", "Album2", 200);
		songs[2] = new Song("path3", "Name3", "Artist3", "Album3", 300);
		songs[3] = new Song("path4", "Name4", "Artist4", "Album4", 400);
		songs[4] = new Song("path5", "Name5", "Artist5", "Album5", 500);
	}
	
	/**
	 * Do "manual" garbage collection, then call the garbage collector. 
	 */
	@After
	public void tearDown() {
		songs = null;
		System.gc();
	}
	
	/**
	 * Test the Merge Sort algorithm. First sort the test array of songs, 
	 * then iterate through the test array and  check that each song is 
	 * greater than the previous song.
	 */
	@Test
	public void testMergeSort() {
		MergeSort.sort(songs);
		for (int i = 0; i < songs.length-1; i++) {
			assertTrue(songs[i+1].compareTo(songs[i]) == 0 || songs[i+1].compareTo(songs[i]) == 1);
		}
	}
	
	/**
	 * Test the Binary Search algorithm. Traverse each song in the test array
	 * and search for it (the test array will already be sorted since testMerseSort
	 * runs before this test case).
	 */
	@Test 
	public void testBinarySearch() {
		int index = 0;
		for (int i = 0; i < songs.length; i++) {
			assertTrue(BinarySearch.search(songs[i], songs) == index);
			index++;
		}
	}
	
	/**
	 * Test the KMP algorithm. Traverse each song in the test array
	 * and search for the substrings "Name", "Artist", and "Album", which 
	 * all of our generic songs in the test array contain (by inspection).
	 */
	@Test
	public void testKMP() {
		KMP kmp1 = new KMP("Name");
		KMP kmp2 = new KMP("Artist");
		KMP kmp3 = new KMP("Album");

		for (int i = 0; i < songs.length; i++) {
			assertTrue(kmp1.search(songs[i].name()) != -1);
			assertTrue(kmp2.search(songs[i].artist()) != -1);
			assertTrue(kmp3.search(songs[i].album()) != -1);
		}
	}
}
