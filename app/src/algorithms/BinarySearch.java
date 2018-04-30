package algorithms;

import smp.Song;

/**
 * Performs a Binary Search on an array of Songs.
 */
public class BinarySearch {
	
    public static int search(Song key, Song[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (key.compareTo(a[mid]) < 0) hi = mid - 1;
            else if (key.compareTo(a[mid]) > 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
}
