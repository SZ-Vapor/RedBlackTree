package edu.frostburg.COSC310.Hogenson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Steven
 */
public class Main {

    static ArrayList<String> original = new ArrayList<String>();
    static ArrayList<Integer> id = new ArrayList<Integer>();
    static ArrayList<String> firstN = new ArrayList<String>();
    static ArrayList<String> lastN = new ArrayList<String>();
    static ArrayList<Integer> age = new ArrayList<Integer>();

    public static void main(String args[]) throws UnsupportedEncodingException, FileNotFoundException {

        Scanner usIn = new Scanner(new File("names.txt"));
        Scanner userIn = new Scanner(System.in);

        while (usIn.hasNext()) {
            String sz = usIn.nextLine();

            if (!sz.equals("")) {
                if (sz.charAt(0) != '#') {
                    if (!sz.contains("#")) {
                        String szS[] = sz.split(" ");
                        if (szS.length != 4) {
                            System.err.println("INPUT FILE DOES NOT CONTAIN PROPER PARAMETERS FOR THIS PROGRAM");
                            System.exit(0);
                        }
                        original.add(sz);
                    }
                }
            }
        }
        usIn.close();
        addInfoToLists();

        RedBlackTree rbt = new RedBlackTree();

        for (int i = 0; i < id.size(); i++) {
            rbt.insert(id.get(i), firstN.get(i), lastN.get(i), age.get(i));
        }

        while (true) {
            System.out.print("Input: ");
            String s = userIn.nextLine();
            if (s.equals("")) {
                break;
            }
            String sSplit[] = s.split(" ");

            if (s.charAt(s.length() - 1) == ('!')) {

                long start = System.nanoTime();
                System.out.println(rbt.searchComp2(sSplit[0] + " " + sSplit[1]));
                long end = System.nanoTime();
                System.out.println("Time taken to perform this operation (in milliseconds): " + (end - start) / 1000000.0 + "\n");

            } else if (s.charAt(s.length() - 1) == ('?')) {

                long start = System.nanoTime();
                System.out.println(rbt.printLastNamesOfLetter(sSplit[0].toUpperCase()));
                long end = System.nanoTime();
                if (sSplit[0].length() == 1) {
                    System.out.println("Time taken to perform this operation (in milliseconds): " + (end - start) / 1000000.0 + "\n");

                }

            } else if (sSplit.length == 1) {
                long start = System.nanoTime();
                System.out.println(rbt.printLastNamesOfName(sSplit[0]));
                long end = System.nanoTime();
                System.out.println("Time taken to perform this operation (in milliseconds): " + (end - start) / 1000000.0 + "\n");
            } else {
                long start = System.nanoTime();
                System.out.println(rbt.search2(s));
                long end = System.nanoTime();
                System.out.println("Time taken to perform this operation (in milliseconds): " + (end - start) / 1000000.0 + "\n");

            }
        }
    }

    static void addInfoToLists() {
        ArrayList<String> elements = new ArrayList<String>();
        for (int i = 0; i < original.size(); i++) {
            String split[] = original.get(i).split("\\s+");
            for (String line : split) {
                elements.add(line);
            }
        }
        for (int i = 0; i < elements.size(); i += 4) {
            id.add(Integer.parseInt(elements.get(i)));
        }

        for (int i = 1; i < elements.size(); i += 4) {
            firstN.add(elements.get(i));
        }

        for (int i = 2; i < elements.size(); i += 4) {
            lastN.add(elements.get(i));
        }

        for (int i = 3; i < elements.size(); i += 4) {
            age.add(Integer.parseInt(elements.get(i)));
        }

    }
}
