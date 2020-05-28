/*
 * NAME: TODO
 * PID: TODO
 */

/**
 * An implementation of a bloom filter prototype.
 */
public class BloomFilterJunior {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 50;
    private static final int HASH_THREE_LEFT_SHIFT = 8;

    /* Instance variables */
    private boolean[] table;

    public BloomFilterJunior(int capacity) {
        /* TODO */
    }

    public void insert(String value) {
        /* TODO */
    }

    public boolean lookup(String value) {
        /* TODO */
        return false;
    }

    /**
     * Hash function 1.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashOne(String value) {
        /* TODO: Copy and paste from your HashTable */
        return -1;
    }

    /**
     * Hash function 2.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashTwo(String value) {
        /* TODO: Copy and paste from your HashTable */
        return -1;
    }

    /**
     * Hash function 3.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashThree(String value) {
        // Base-256 String
        int hash = 0;
        char[] chars = value.toCharArray();
        for (char c : chars) {
            hash = ((hash << HASH_THREE_LEFT_SHIFT) + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }
}
