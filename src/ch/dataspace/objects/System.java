package ch.dataspace.objects;

import ch.dataspace.framework.SpecType;
import ch.dataspace.framework.StarType;

import java.util.ArrayList;

public class System {

    private static int count = 0;

    private Region region;
    private ArrayList<Star> stars = new ArrayList<>();

    private int id;
    private double xPos, yPos, zPos;
    private int starCount;
    private double systemAge; // In Gy.
    private double systemAbundance;

    System(Region region) {
        this.id = count;
        count++;
        this.region = region;
        xPos = Util.randDouble(0, region.getWidth());
        yPos = Util.randDouble(0, region.getWidth());
        zPos = Util.randDouble(0, region.getWidth());

        starCount = 1;
        genStarCount(4);
        initStars();

        // DETERMINE SYSTEM AGE

        if (stars.get(0).getBasicType() == StarType.BROWN_DWARF || stars.get(0).getBasicType() == StarType.WHITE_DWARF) {
            systemAge = Util.randDouble(0.5, 10.5);
        } else {
            double mass, lum;
            switch (stars.get(0).getLumClass()) {
                case Ia:
                case Ib:
                case II:
                case III:
                    // Mass-luminosity relationship:
                    mass = stars.get(0).getMass();
                    if (mass < 0.43) {
                        lum = 0.23 * Math.pow(mass, 2.3);
                    } else if (mass < 2) {
                        lum = Math.pow(mass, 4);
                    } else if (mass < 55) {
                        lum = 1.4 * Math.pow(mass, 3.5);
                    } else {
                        lum = 32000 * mass;
                    }
                    systemAge = 12 * mass / lum;
                    break;
                case IV:
                    // Mass-luminosity relationship:
                    mass = stars.get(0).getMass();
                    if (mass < 0.43) {
                        lum = 0.23 * Math.pow(mass, 2.3);
                    } else if (mass < 2) {
                        lum = Math.pow(mass, 4);
                    } else if (mass < 55) {
                        lum = 1.4 * Math.pow(mass, 3.5);
                    } else {
                        lum = 32000 * mass;
                    }
                    systemAge = 11 * mass / lum;
                    break;
                case V:
                    double subclass = stars.get(0).getSubclass();
                    switch (stars.get(0).getSpecType()) {
                        case O:
                            systemAge = Util.randDouble(0.001,0.01);
                            break;
                        case B:
                            systemAge = Util.randDouble(0.5, 10.5) * stars.get(0).getMass() / stars.get(0).getLum();
                            break;
                        case A:
                            if (subclass < 5) {
                                double tempRand = Util.randDouble(0, 1);
                                if (tempRand < 0.8) {
                                    systemAge = 0.5 * tempRand + 0.05;
                                } else {
                                    systemAge = tempRand - 0.35;
                                }
                            } else {
                                double tempRand = Util.randDouble(0, 1);
                                if (tempRand < 0.15) {
                                    systemAge = 2 * tempRand + 0.1;
                                } else {
                                    systemAge = tempRand + 0.25;
                                }
                            }
                            break;
                        case F:
                            if (subclass < 5) {
                                double tempRand = Util.randDouble(0, 1);
                                systemAge = 3.2 * tempRand + 0.17;
                            } else {
                                systemAge = Util.randDouble(0.5,5);
                            }
                            break;
                        default:
                            systemAge = Util.randDouble(0.5,10.5);
                    }
                    break;
            }
        }

        // Adjusting luminosity / temperature based on system age.
        for (Star star : stars) {
            star.setAge(systemAge);
            star.ageAdjust();
        }

        double tempRand = Util.randDouble(0, 10) + Util.randDouble(0, 10) + systemAge;
        if (tempRand < 9) {
            systemAbundance = 2;
        } else if (tempRand < 12) {
            systemAbundance = 1;
        } else if (tempRand < 18) {
            systemAbundance = 0;
        } else if (tempRand < 21) {
            systemAbundance = -1;
        } else {
            systemAbundance = -3;
        }
    }

    private void genStarCount(int max) {
        // Chance to have multiple stars in a system, with each star getting more unlikely.
        if (Util.randDouble(0, 1) < 0.4 && starCount < max) {
            starCount++;
            genStarCount(max);
        }
    }

    private void initStars() {
        stars.add(new Star(this));
        for (int i = 0; i < starCount - 1; i++) {
            stars.add(new Star(this, stars.get(0)));
        }
    }

    @Override
    public String toString() {
        String out = " - SYSTEM #" + id + " (" + Util.round(xPos,2)
                + " pc, " + Util.round(yPos,2) + " pc, " + Util.round(zPos,2)
                + " pc) (starCount: " + starCount + ")";
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
