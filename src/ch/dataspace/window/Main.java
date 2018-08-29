package ch.dataspace.window;

import ch.dataspace.objects.Region;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Region> regions = new ArrayList<>();

        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            regions.add(new Region(5));
        }

        long intermediateTime = System.nanoTime();

        for (Region region : regions) {
            System.out.println(region.toString());
        }

        long endTime = System.nanoTime();

        System.out.println("\nGENERATING: " + ((intermediateTime - startTime)/1000000) + " ms");
        System.out.println("\nPRINTING: " + ((endTime - intermediateTime)/1000000) + " ms");
    }
}
