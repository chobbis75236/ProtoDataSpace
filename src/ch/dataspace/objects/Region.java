package ch.dataspace.objects;

import java.util.ArrayList;

public class Region {
    private static final double STELLAR_DENSITY = 0.14; // per cubic parsec
    private static int count = 0;

    private ArrayList<System> systems = new ArrayList<>();

    private int id;
    private double width; // parsecs
    private int systemCount;

    public Region(double width) {
        id = count;
        count++;
        this.width = width;
        // density * volume * randomness factor, then converting star count to system count.
        systemCount = (int) (STELLAR_DENSITY * Math.pow(width,3) * Util.randDouble(0.8, 1.2) / 1.5216);
        genSystems();
    }

    private void genSystems() {
        for (int i = 0; i < systemCount; i++) {
            systems.add(new System(this));
        }
    }

    double getWidth() {
        return width;
    }

    @Override
    public String toString() {
        // StringBuilder is used as it is more efficient for repeated appending.
        String out = "- REGION #" + id + " (width: " + width + " systemCount: " + systemCount + ")";
        for (System system : systems) {
            out += "\n";
            out += system.toString();
        }
        return out;
    }
}
