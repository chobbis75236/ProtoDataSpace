package ch.dataspace.objects;

import java.util.ArrayList;

public class System {

    private static int count = 0;

    private Region region;
    private ArrayList<Star> stars = new ArrayList<>();

    private int id;
    private double xPos, yPos, zPos;
    private int starCount;

    System(Region region) {
        this.id = count;
        count++;
        this.region = region;
        xPos = Util.randDouble(0, region.getWidth());
        yPos = Util.randDouble(0, region.getWidth());
        zPos = Util.randDouble(0, region.getWidth());

        starCount = 1;
        genStarCount(4);
        genStars();
    }

    private void genStarCount(int max) {
        // Chance to have multiple stars in a system, with each star getting more unlikely.
        if (Util.randDouble(0, 1) < 0.4 && starCount < max) {
            starCount++;
            genStarCount(max);
        }
    }

    private void genStars() {
        stars.add(new Star(this));
        for (int i = 0; i < starCount - 1; i++) {
            stars.add(new Star(this, stars.get(0)));
        }
    }

    @Override
    public String toString() {
        String out = " - SYSTEM #" + id + " (" + xPos + ", " + yPos + ", " + zPos + ") (starCount: " + starCount + ")";
        for (Star star : stars) {
            out += "\n";
            out += star.toString();
        }
        return out;
    }
}
