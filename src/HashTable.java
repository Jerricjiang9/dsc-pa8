/*
 * Name: Jerric Jiang
 * PID: A16018311
 */

import java.text.DecimalFormat;

/**
 * HashTable Class
 * 
 * @author Jerric Jiang
 * @since 5/22/2020
 */
public class HashTable implements IHashTable {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int DEFAULT_CAPACITY = 20;
    private static final double MAX_LOAD_FACTOR = 0.5;
    private static final int SUBTABLE_DIVISOR = 2;
    private static final int CHAR_VALUE = 27;
    private static final int LEFT_SHIT = 5;
    private static final int CAPACITY_INCREASE = 2;

    /* Instance variables */
    private String[] table1, table2; // sub-tables
    private int nElems; // size
    private int capacity; // capacity
    private String statsLog; // Statistics log
    private int rehashCount; // number of rehashes
    private int evictionCount; // number of evictions

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        if (capacity < MIN_INIT_CAPACITY)
            throw new IllegalArgumentException();
        this.capacity = capacity;
        table1 = new String[capacity / SUBTABLE_DIVISOR];
        table2 = new String[capacity / SUBTABLE_DIVISOR];
        statsLog = "";
        nElems = 0;
        evictionCount = 0;
        rehashCount = 1;
    }

    @Override
    public boolean insert(String value) {
        int counter = 0;
        if (lookup(value))
            return false;
        if ((double) nElems / capacity > MAX_LOAD_FACTOR)
            rehash();
        while (infiniteLoopTest(value)) {
            rehash();
        }
        String temp1 = value;
        String temp2;
        nElems+=1;
        while (counter < capacity){
            if (table1[hashOne(temp1)] == null) {
                table1[hashOne(temp1)] = temp1;
                return true;
            }
            else {
                temp2 = table1[hashOne(temp1)];
                table1[hashOne(temp1)] = temp1;
                evictionCount++;
            }
            if (table2[hashTwo(temp2)] == null){
                table2[hashTwo(temp2)] = temp2;
                return true;
            }
            else {
                temp1 = table2[hashTwo(temp2)];
                table2[hashTwo(temp2)] = temp2;
                evictionCount++;
            }
            counter++;
        }
        return true;
    }

    @Override
    public boolean delete(String value) {
        if (value == null)
            throw new NullPointerException();
        if (lookup(value) == false)
            return false;
        nElems-=1;
        if (table1[hashOne(value)] == value)
            table1[hashOne(value)] = null;
        else
            table2[hashTwo(value)] = null;
        return true;
    }

    @Override
    public boolean lookup(String value) {
        if (value == null)
            throw new NullPointerException();
        int pos1 = hashOne(value);
        int pos2 = hashTwo(value);
        if (table1[pos1] == value || table2[pos2] == value)
            return true;
        else
            return false;

    }

    @Override
    public int size() {
        return nElems;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * Get the string representation of the hash table.
     *
     * Format Example:
     * | index | table 1 | table 2 |
     * | 0 | Marina | [NULL] |
     * | 1 | [NULL] | DSC30 |
     * | 2 | [NULL] | [NULL] |
     * | 3 | [NULL] | [NULL] |
     * | 4 | [NULL] | [NULL] |
     *
     * @return string representation
     */
    @Override
    public String toString() {
        String table = "| index | table 1 | table 2 | \n";
        for (int i = 0; i < table1.length; i++){
            table = table + "| " + i + " | ";
            if (table1[i] != null)
                table = table + table1[i] + " | ";
            else
                table = table + "[NULL] | ";
            if (table2[i] != null)
                table = table + table2[i] + " |\n";
            else
                table = table + "[NULL] |\n";
        }
        return table;
    }

    /**
     * Get the rehash stats log.
     *
     * Format Example: 
     * Before rehash # 1: load factor 0.80, 3 evictions.
     * Before rehash # 2: load factor 0.75, 5 evictions.
     *
     * @return rehash stats log
     */
    public String getStatsLog() {
        return statsLog;
    }

    private void rehash() {
        double loadFactor = (double) nElems / capacity;
        DecimalFormat df = new DecimalFormat("0.00");
        statsLog = statsLog + "Before rehash # " + rehashCount + ": load factor "
                + df.format(loadFactor) + ", " + evictionCount + " evictions.\n";
        evictionCount = 0;
        rehashCount++;
        capacity = capacity * CAPACITY_INCREASE;
        String[] temp1 = table1;
        String[] temp2 = table2;
        table1 = new String[capacity / SUBTABLE_DIVISOR];
        table2 = new String[capacity / SUBTABLE_DIVISOR];
        nElems = 0;
        for (int i=0; i < temp1.length; i++){
            if (temp1[i] != null)
                insert(temp1[i]);
        }
        for (int i = 0; i < temp2.length; i++){
            if (temp2[i] != null)
                insert(temp2[i]);
        }

    }

    private boolean infiniteLoopTest(String value){
        int count = 0;
        String[] tempTable1 = new String[table1.length];
        String[] tempTable2 = new String[table2.length];
        for (int i = 0; i < table1.length; i++){
            tempTable1[i] = table1[i];
            tempTable2[i] = table2[i];
        }
        String temp1 = value;
        String temp2;
        while (count < capacity){
            int pos1 = hashOne(temp1);
            if (tempTable1[pos1] == null)
                return false;
            else {
                temp2 = tempTable1[pos1];
                tempTable1[pos1] = temp1;
            }
            int pos2 = hashTwo(temp2);
            if (tempTable2[pos2] == null)
                return false;
            else {
                temp1 = tempTable2[pos2];
                tempTable2[pos2] = temp2;
            }
            count++;
        }
        return true;
    }

    private int hashOne(String value) {
        int hashVal = 0;
        for (int i = 0; i < value.length(); i++){
            int letter = value.charAt(i);
            hashVal = (hashVal * CHAR_VALUE + letter) % table1.length;
        }
        return hashVal;
    }

    private int hashTwo(String value) {
        int hashValue = 0;
        for (int i = 0; i < value.length(); i++){
            int leftShiftedValue = hashValue << LEFT_SHIT;
            int rightShiftedValue = hashValue >>> CHAR_VALUE;
            hashValue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        return Math.abs(hashValue % table2.length);
    }
}
