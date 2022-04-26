import java.util.ArrayList;
import java.util.Arrays;

public class SubString {
    private static final int ALPHABET_CARDINALITY = 256;
    private static final int PRIME_NUMBER = 373;

    //Method that searches for substrings using the simplest algorithm
    public static ArrayList<Integer> simplestSearch(String source, String sample) throws Exception {
        if(source == null || sample == null)
            throw new Exception("Your source string or sample is null!");

        ArrayList<Integer> result = new ArrayList<>();

        if(source.length() == 0 || sample.length() == 0 || sample.length() > source.length())
            return result;

        int n = source.length();
        int m = sample.length();

        for(int i = 0; i <= n - m; i++)
            if(sample.equals(source.substring(i, i + m)))
                result.add(i);

        return result;
    }

    //Method that searches for substrings using the Rabin-Karp algorithm
    public static ArrayList<Integer> RabinKarp(String source, String sample) throws Exception {
        if(source == null || sample == null)
            throw new Exception("Your source string or sample is null!");

        ArrayList<Integer> result = new ArrayList<>();

        if(source.length() == 0 || sample.length() == 0 || sample.length() > source.length())
            return result;

        int n = source.length();
        int m = sample.length();

        int D = (int) (Math.pow(ALPHABET_CARDINALITY, m - 1) % PRIME_NUMBER);
        int sampleHash = getHash(sample);
        int sourceHash = getHash(source.substring(0, m));

        for(int i = 0; i <= n - m; i++) {
            if(sampleHash == sourceHash)
                if(sample.equals(source.substring(i, i + m)))
                    result.add(i);
            if(i < n - m) {
                sourceHash = (ALPHABET_CARDINALITY * (sourceHash - D * source.charAt(i)) + source.charAt(i + m)) % PRIME_NUMBER;
                if (sourceHash < 0)
                    sourceHash += PRIME_NUMBER;
            }
        }
        return result;
    }

    //Method that calculates the hash of a substring for the Rabin-Karp algorithm
    private static int getHash(String sample) {
        int hash = 0;
        for(int i = 0; i < sample.length(); i++)
            hash = (ALPHABET_CARDINALITY * hash + sample.charAt(i)) % PRIME_NUMBER;
        return hash;
    }

    //Method that searches for substrings using the Knuth-Morris-Pratt algorithm
    public static ArrayList<Integer> KnuthMorrisPratt(String source, String sample) throws Exception {
        if(source == null || sample == null)
            throw new Exception("Your source string or sample is null!");

        ArrayList<Integer> result = new ArrayList<>();

        if(source.length() == 0 || sample.length() == 0 || sample.length() > source.length())
            return result;

        int n = source.length();
        int m = sample.length();

        int[] prefixTable = prefixFunction(sample);
        int k = 0;
        for(int i = 0; i < n; i++) {
            while(k > 0 && sample.charAt(k) != source.charAt(i))
                k = prefixTable[k - 1];
            if(sample.charAt(k) == source.charAt(i))
                k = k + 1;
            if(k == m) {
                result.add(i - m + 1);
                k = prefixTable[k - 1];
            }
        }
        return result;
    }

    //Method that calculates the prefix table for a Knuth-Morris-Pratt algorithm
    //and for creating a suffix table in the Boyer-Moore algorithm
    private static int[] prefixFunction(String sample) {
        int[] prefixTable = new int[sample.length()];
        prefixTable[0] = 0;
        int k;
        for(int i = 1; i < sample.length(); i++) {
            k = prefixTable[i - 1];
            while(k > 0 && sample.charAt(k) != sample.charAt(i))
                k = prefixTable[k - 1];
            if(sample.charAt(k) == sample.charAt(i))
                k = k + 1;
            prefixTable[i] = k;
        }
        return prefixTable;
    }

    //Method that searches for substrings using the Boyer-Moore algorithm
    public static ArrayList<Integer> BoyerMoore(String source, String sample) throws Exception {
        if(source == null || sample == null)
            throw new Exception("Your source string or sample is null!");

        ArrayList<Integer> result = new ArrayList<>();

        if(source.length() == 0 || sample.length() == 0 || sample.length() > source.length())
            return result;

        int n = source.length();
        int m = sample.length();
        int stopOffset;
        int suffixOffset;
        int[] suffixTable = getSuffixTable(sample);
        int[] stopTable = getStopTable(sample);

        for(int i = 0; i <= n - m;) {
            int j = m - 1;

            while(j >= 0 && sample.charAt(j) == source.charAt(i + j))
                j--;

            if(j == -1) {
                result.add(i);
                stopOffset = 1;
            }
            else
                stopOffset = j - stopTable[source.charAt(i + j) % ALPHABET_CARDINALITY];
            suffixOffset = suffixTable[j + 1];
            i += Math.max(suffixOffset, stopOffset);
        }
        return result;
    }

    //Method that calculates the suffix table for a Boyer-Moore algorithm
    private static int[] getSuffixTable(String sample) {
        int m = sample.length();
        int[] suffixTable = new int[m + 1];
        int[] p1 = prefixFunction(sample);

        StringBuilder sb = new StringBuilder(sample);
        sb.reverse();
        String inverted = sb.toString();
        int[] p2 = prefixFunction(inverted);

        for(int i = 0; i < m + 1; i++)
            suffixTable[i] = m - p1[m - 1];

        for(int i = 0; i < m; i++) {
            int index = m - p2[i];
            int offset = i - p2[i] + 1;
            if(suffixTable[index] > offset)
                suffixTable[index] = offset;
        }

        return suffixTable;
    }

    //Method that calculates the stop table for a Boyer-Moore algorithm
    private static int[] getStopTable(String sample) {
        int[] stopTable = new int[ALPHABET_CARDINALITY];
        Arrays.fill(stopTable, -1);
        for(int i = 0; i < sample.length() - 1; i++)
            if(stopTable[sample.charAt(i) % ALPHABET_CARDINALITY] < i)
                stopTable[sample.charAt(i) % ALPHABET_CARDINALITY] = i;
        return stopTable;
    }

    //Method that searches for substrings using algorithm based on finite state machine
    public static ArrayList<Integer> FiniteStateMachine(String source, String sample) throws Exception {
        if(source == null || sample == null)
            throw new Exception("Your source string or sample is null!");

        ArrayList<Integer> result = new ArrayList<>();

        if(source.length() == 0 || sample.length() == 0 || sample.length() > source.length())
            return result;

        int n = source.length();
        int m = sample.length();
        int[][] table = new int[m + 1][ALPHABET_CARDINALITY];

        for(int i = 0; i < m + 1; i++)
            for(int j = 0; j < ALPHABET_CARDINALITY; j++)
                table[i][j] = suffixFunction(i, (char) j, sample);

        int currentState = 0;
        for(int position = 0; position < n; position++) {
            currentState = table[currentState][source.charAt(position) % ALPHABET_CARDINALITY];
            if(currentState == m) {
                position -= (m - 1);
                result.add(position);
            }
        }

        return result;
    }

    //Method that calculates the following state
    private static int suffixFunction(int state, char currentChar, String sample) {
        if(state < sample.length() && currentChar == sample.charAt(state))
            return ++state;

        for(int currentStatus = state - 1; currentStatus >= 0; currentStatus--)
            if (sample.charAt(currentStatus) == currentChar)
                if(sample.substring(0, currentStatus).equals(sample.substring(state - currentStatus, state)))
                    return currentStatus + 1;

        return 0;
    }
}
