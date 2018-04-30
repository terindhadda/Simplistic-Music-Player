package algorithms;

import smp.Song;

/**
 * Performs a Merge Sort on an array of Songs.
 */
public class MergeSort {
	
	private static Song[] aux;
	
	/**
	 * Perform the sort.
	 * 
	 * @param x - array of songs to search
	 */
	public static void sort (Song[] x) {
		// Do lg N passes of pairwise merges.
		int N = x.length;
		aux = new Song[N];
		for (int sz = 1; sz < N; sz = sz+sz) 			// sz: subarray size
			for (int lo = 0; lo < N-sz; lo += sz+sz) 	// lo: subarray index
					merge(x, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
	}
	

	/**
	 * Stably merge a[lo .. mid] with a[mid+1 .. hi] using aux[lo .. hi]
	 * 
	 * @param a - subarray of songs
	 * @param lo - low index
	 * @param mid - mid index
	 * @param hi - high index 
	 */
	private static void merge(Song[] a, int lo, int mid, int hi) {
		// Merge a[lo..mid] with a[mid+1..hi].
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) // Copy a[lo..hi] to aux[lo..hi].
			aux[k] = a[k];
		for (int k = lo; k <= hi; k++) // Merge back to a[lo..hi].
			if (i > mid) a[k] = aux[j++];
			else if (j > hi ) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
	}

    /**
     * Compare two songs are return the corresponding result.
     * 
     * @param v - one song to compare
     * @param w - another song to compare
     * @return boolean - result of v < w
     */
	private static boolean less(Song v, Song w) {
		return v.compareTo(w) < 0;
	}
}
