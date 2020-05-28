/*
 * NAME: Jerric Jiang
 * PID: A16018311
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * LineCounter Class
 * 
 * @author Jerric Jiang
 * @since 5/26/2020
 */
public class LineCounter {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;

    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;
        ArrayList<String> list = new ArrayList<String>();

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable

        for (int i = 0; i < numArgs; i++) {

            try{
                File file = new File(args[i]);
                Scanner scanner = new Scanner(file);
                tableList[i] = new HashTable(MIN_INIT_CAPACITY);
                while (scanner.hasNextLine()){
                    String data = scanner.nextLine();
                    //list.add(scanner.nextLine());
                    tableList[i].insert(data);
                }
                System.out.println(tableList[i]);
                scanner.close();
            } catch (FileNotFoundException e){
                System.out.println("File not found");
            }

        }

        // Find similarities across files

        for (int i = 0; i < numArgs; i++) {

            for (int j = 0; j < numArgs && j!=i; j++){
                    int count=0;
                    int total=0;
                try{
                    File file = new File(args[i]);
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()){
                        String data = scanner.nextLine();
                        System.out.println(data);
                        if (tableList[j].lookup(data))
                            count++;
                        total++;
                    }
                    //System.out.println(tableList[j]);
                    System.out.println(args[i] + ":\n" + count + "% of lines are also in " + args[j] + "\n");
                    scanner.close();
                } catch (FileNotFoundException e){
                    System.out.println("File not found");
                }


            }

        }
    }

}