package ch.dataspace.window;

import ch.dataspace.objects.Region;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Region> regions = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            regions.add(new Region(i, 20));
        }
    }
}
