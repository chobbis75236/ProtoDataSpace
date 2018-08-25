package ch.dataspace.window;

import ch.dataspace.objects.Region;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Region> regions = new ArrayList<>();

        for (int i = 0; i < 80; i++) {
            regions.add(new Region(5));
        }

        for (Region region : regions) {
            System.out.println(region.toString());
        }
    }
}
