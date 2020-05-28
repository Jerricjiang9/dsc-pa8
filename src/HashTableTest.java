import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {
    HashTable table = new HashTable();
    HashTable table1 = new HashTable(15);

    @Test
    public void insert() {
        assertEquals(true, table.insert("Hello"));
        assertEquals(true, table.insert("Hi"));
        assertEquals(false, table.insert("Hi"));
    }

    @Test
    public void delete() {
        table.insert("test");
        table.insert("hello");
        assertEquals(true, table.delete("test"));
        assertEquals(true, table.delete("hello"));
        assertEquals(false, table.delete("test123"));
    }

    @Test
    public void lookup() {
        table.insert("hello");
        assertEquals(true, table.lookup("hello"));
        table.insert("test123");
        assertEquals(false, table.lookup("test"));
        assertEquals(true, table.lookup("test123"));
    }

    @Test
    public void size() {
        assertEquals(0, table.size());
        table.insert("test");
        assertEquals(1, table.size());
        table.delete("test");
        assertEquals(0, table.size());
    }

    @Test
    public void capacity() {
        assertEquals(20, table.capacity());
        assertEquals(15, table1.capacity());
    }

    @Test
    public void testToString() {
        table.insert("hello");
        table.insert("hi");
        table.insert("hello");
        System.out.println(table.getStatsLog());


        System.out.println(table.toString());
    }

    @Test
    public void getStatsLog() {
    }
}