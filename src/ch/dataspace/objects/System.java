package ch.dataspace.objects;

import ch.dataspace.framework.StarType;

import java.util.ArrayList;

public class System {

    private static int count = 0;

    private Region region;
    private ArrayList<Star> stars = new ArrayList<>();

    private int id;
    private double xPos, yPos, zPos;
    private int starCount;
    private double systemAge;

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
        if (stars.get(0).getBasicType() == StarType.BROWN_DWARF) {

        } else {
            switch (stars.get(0).getLumClass()) {
                case Ia:
                case Ib:
                case II:
                case III:

            }
        }
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
        String out = " - SYSTEM #" + id + " (" + Util.round(xPos,2) + " pc, " + Util.round(yPos,2) + " pc, " + Util.round(zPos,2) + " pc) (starCount: " + starCount + ")";
        for (Star star : stars) {
            out += "\n";
            out += star.toString();
        }
        return out;
    }

    public void setSystemAge(double systemAge) {
        this.systemAge = systemAge;
    }
}
