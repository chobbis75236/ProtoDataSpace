package ch.dataspace.objects;

public class Region {
    private static final double STELLAR_DENSITY = 0.14; // per cubic parsec

    private int regionID;
    private double width; // parsecs
    private int starCount;

    public Region(int regionID, double width) {
        this.regionID = regionID;
        this.width = width;
        starCount = (int) (STELLAR_DENSITY * Math.pow(width,3) * Util.randBetween(0.8, 1.2));
    }

    public int getRegionID() {
        return regionID;
    }

    public double getWidth() {
        return width;
    }

    public int getStarCount() {
        return starCount;
    }

    @Override
    public String toString() {
        return "REGION ID: " + regionID + "; STAR COUNT: " + starCount;
    }
}
