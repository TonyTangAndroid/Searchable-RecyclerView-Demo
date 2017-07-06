package com.example;

import net.greypanther.natsort.SimpleNaturalComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NaturalSortOrder {

    public static void main(String[] args) {
        String[] strings = new String[]{"HQ 10th", "HQ 1th", "HQ p1", "HQ p2", "HQ P2", "HQ P0", "HQ p0",};

        List orig = Arrays.asList(strings);

        System.out.println("Original: " + orig);

        List scrambled = Arrays.asList(strings);
        Collections.shuffle(scrambled);

        System.out.println("Scrambled: " + scrambled);

        Collections.sort(scrambled, SimpleNaturalComparator.getInstance());

        System.out.println("Sorted: " + scrambled);
    }
}
