package algorithms;

/**
 * The famous KMP substring search algorithm.
 * 
 * @source www.algs4.cs.princeton.edu
 *
 */
public class KMP {
    private String pattern;
    private int[] next;

    /**
     * Create Knuth-Morris-Pratt NFA from pattern.
     * 
     * @param pattern - pattern to create the NFA for.
     */
    public KMP(String pattern) {
        this.pattern = pattern;
        int M = pattern.length();
        next = new int[M];
        int j = -1;
        for (int i = 0; i < M; i++) {
            if (i == 0)                                      next[i] = -1;
            else if (pattern.charAt(i) != pattern.charAt(j)) next[i] = j;
            else                                             next[i] = next[j];
            while (j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
        }
    }

    /**
     * Return offset of first occurrence of text in pattern (or N if no match),
     * simulate the NFA to find match.
     * 
     * @param text - text to search
     * @return int - offset of match, -1 if not found
     */
    public int search(String text) {
        int M = pattern.length();
        int N = text.length();
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j))
                j = next[j];
            j++;
        }
        if (j == M) return i - M;
        return -1;
    }
}