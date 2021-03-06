package ch.dataspace.objects;

import ch.dataspace.framework.LumClass;
import ch.dataspace.framework.SpecType;
import ch.dataspace.framework.StarType;

class Star {

    private static int count = 0;

    private int id;
    private Object parent;

    private StarType basicType;
    private SpecType specType;
    private LumClass lumClass;
    private double subclass;

    private double mass; // sol
    private double temp; // K
    private double lum; // sol
    private double radius; // sol

    private double age; // Gy

    // Used for a completely random star.
    Star(Object parent) {
        this.id = count;
        count++;
        this.parent = parent;

        genBasicType();
        genClass();
        genBasicStats();
    }

    // Used for a binary star.
    Star(Object parent, Star primary) {
        this.id = count;
        count++;
        this.parent = parent;

        double tempRand = Util.randDouble(0, 1); // Determines whether star is identical or not.
        if (tempRand < 0.2) {
            // Identical spectral type and size class to primary.
            basicType = primary.getBasicType();
            specType = primary.getSpecType();
            lumClass = primary.getLumClass();
            subclass = Math.max(Util.randDouble(0,10), primary.getSubclass());
        } else {
            // Random type, but Giant and higher type than primary are treated as Brown Dwarf.
            genBasicType();
            if (basicType == StarType.GIANT || basicType.getRank() < primary.getBasicType().getRank()) {
                basicType = StarType.BROWN_DWARF;
            }
            genClass();
        }
        genBasicStats();
    }

    // CLASS AND TYPE

    private void genClass() {
        genSpecType();
        genSubclass();
        genLumClass();
    }

    private void genBasicType() {
        // Probabilities determined through Trisen.
        double tempRand = Util.randDouble(0, 1); // Determines basic type.
        if(tempRand < 0.01) basicType = StarType.GIANT;            // 0.01
        else if(tempRand < 0.02) basicType = StarType.CLASS_A;     // 0.01
        else if(tempRand < 0.05) basicType = StarType.CLASS_F;     // 0.03
        else if(tempRand < 0.13) basicType = StarType.CLASS_G;     // 0.08
        else if(tempRand < 0.27) basicType = StarType.CLASS_K;     // 0.14
        else if(tempRand < 0.76) basicType = StarType.CLASS_M;     // 0.49
        else if(tempRand < 0.86) basicType = StarType.WHITE_DWARF; // 0.10
        else basicType = StarType.BROWN_DWARF;                     // 0.14
        // Note: StarType.SPECIAL should be added in a future version.
    }

    // Requires basicType.
    private void genSpecType() {
        switch (basicType) {
            case GIANT:
                double tempRand = Util.randDouble(0,1); // Determines spectral type.
                if(tempRand < 0.1) specType = SpecType.F;
                else if(tempRand < 0.2) specType = SpecType.G;
                else specType = SpecType.K;
                break;
            case CLASS_A:
                specType = SpecType.A;
                break;
            case CLASS_F:
                specType = SpecType.F;
                break;
            case CLASS_G:
                specType = SpecType.G;
                break;
            case CLASS_K:
                specType = SpecType.K;
                break;
            case CLASS_M:
                specType = SpecType.M;
                break;
        }
    }

    // Requires basicType and specType.
    private void genLumClass() {
        // Not all stars have a luminosity class.
        switch (basicType) {
            case GIANT:
                if(Util.randDouble(0,1) < 0.3) lumClass = LumClass.IV;
                else lumClass = LumClass.III;
                break;
            case CLASS_A:
                if(Util.randDouble(0,1) < 0.4) lumClass = LumClass.IV;
                else lumClass = LumClass.V;
                break;
            case CLASS_F:
                if(Util.randDouble(0,1) < 0.2) lumClass = LumClass.IV;
                else lumClass = LumClass.V;
                break;
            case CLASS_G:
                if(Util.randDouble(0,1) < 0.1) lumClass = LumClass.IV;
                else lumClass = LumClass.V;
                break;
            case CLASS_K: // No break statement, so 'falls through'.
            case CLASS_M:
                lumClass = LumClass.V;
                break;
            case WHITE_DWARF:
                lumClass = LumClass.VII;
                break;
        }
    }

    // Requires specType and lumClass.
    private void genSubclass() {
        if (specType == SpecType.K && lumClass == LumClass.IV) {
            subclass = 0;
        } else if (specType != null) {
            subclass = Util.randDouble(0, 10);
        }
    }

    // BASIC STATS

    private void genBasicStats() {
        if (specType != null) {

            // First digit is specType rank, second digit is subclass.
            double x = specType.getRank() * 10 + subclass;

            // The following are polynomial approximations of Trisen tables.
            switch (lumClass) {
                case Ia:
                    mass = 0.000012475 * Math.pow(x, 4) - 0.0023062 * Math.pow(x, 3) + 0.17110 * Math.pow(x, 2) - 5.6572 * x + 80.372;
                    if (x < 20) {
                        temp = -0.33516 * Math.pow(x, 4) + 17.098 * Math.pow(x, 3) - 238.35 * Math.pow(x, 2) - 1101 * x + 43503;
                    } else {
                        temp = 0.00089849 * Math.pow(x, 4) - 0.17438 * Math.pow(x, 3) + 13.234 * Math.pow(x, 2) - 598.52 * x + 17004;
                    }
                    if (x < 20) {
                        lum = 70.949 * Math.pow(x, 4) - 4069.7 * Math.pow(x, 3) + 88200 * Math.pow(x, 2) - 901710 * x + 4071100;
                    } else {
                        lum = 0.107 * Math.pow(x, 4) - 22.823 * Math.pow(x, 3) + 1792 * Math.pow(x, 2) - 58769 * x + 739724;
                    }
                    break;
                case Ib:
                    mass = 0.000018055 * Math.pow(x, 4) - 0.0032087 * Math.pow(x, 3) + 0.21771 * Math.pow(x, 2) - 6.4877 * x + 81.512;
                    if (x < 20) {
                        temp = -0.14306 * Math.pow(x, 4) + 9.3751 * Math.pow(x, 3) - 147.57 * Math.pow(x, 2) - 1417.2 * x + 44675;
                    } else {
                        temp = 0.0011439 * Math.pow(x, 4) - 0.21971 * Math.pow(x, 3) + 16.094 * Math.pow(x, 2) - 668.22 * x + 17662;
                    }
                    if (x < 15) {
                        lum = 89.918 * Math.pow(x, 4) - 4328.4 * Math.pow(x, 3) + 85878 * Math.pow(x, 2) - 856320 * x + 3640600;
                    } else {
                        lum = 0.081176 * Math.pow(x, 4) - 12.096 * Math.pow(x, 3) + 710.33 * Math.pow(x, 2) - 19274 * x + 205980;
                    }
                    break;
                case II:
                    mass = 0.000018621 * Math.pow(x, 4) - 0.0034503 * Math.pow(x, 3) + 0.23701 * Math.pow(x, 2) - 6.9642 * x + 82.344;
                    if (x < 20) {
                        temp = -0.091135 * Math.pow(x, 4) + 7.6864 * Math.pow(x, 3) - 135.75 * Math.pow(x, 2) - 1430.8 * x + 45664;
                    } else {
                        temp = 0.0029573 * Math.pow(x, 4) - 0.55328 * Math.pow(x, 3) + 37.9 * Math.pow(x, 2) - 1262.2 * x + 23474;
                    }
                    if (x < 15) {
                        lum = 100.11 * Math.pow(x, 4) - 4836.4 * Math.pow(x, 3) + 93237 * Math.pow(x, 2) - 876530 * x + 3453000;
                    } else if (x < 20) {
                        lum = 14.583 * Math.pow(x, 4) - 1068.1 * Math.pow(x, 3) + 29565 * Math.pow(x, 2) - 369110 * x + 1769600;
                    } else if (x < 60) {
                        lum = 0.013752 * Math.pow(x, 4) - 2.0811 * Math.pow(x, 3) + 119.55 * Math.pow(x, 2) - 3078.6 * x + 30396;
                    } else {
                        lum = 14.88 * Math.pow(x, 3) - 3082.2 * Math.pow(x, 2) + 212890 * x - 4887300;
                    }
                    break;
                case III:
                    mass = 0.000024241 * Math.pow(x, 4) - 0.0040822 * Math.pow(x, 3) + 0.25394 * Math.pow(x, 2) - 7.0001 * x + 77.633;
                    if (x < 20) {
                        temp = 0.065764 * Math.pow(x, 4) + 1.9956 * Math.pow(x, 3) - 77.714 * Math.pow(x, 2) - 1610.3 * x + 46751;
                    } else {
                        temp = 0.0013351 * Math.pow(x, 4) - 0.29368 * Math.pow(x, 3) + 23.548 * Math.pow(x, 2) - 938.69 * x + 21036;
                    }
                    if (x < 5) {
                        lum = 14583 * Math.pow(x, 4) - 179170 * Math.pow(x, 3) + 797920 * Math.pow(x, 2) - 1811900 * x + 2996400;
                    } else if (x < 20) {
                        lum = 33.853 * Math.pow(x, 4) - 2167.2 * Math.pow(x, 3) + 52420 * Math.pow(x, 2) - 571540 * x + 2386900;
                    } else if (x < 60) {
                        lum = 0.0014347 * Math.pow(x, 4) - 0.2252 * Math.pow(x, 3) + 13.484 * Math.pow(x, 2) - 362.05 * x + 3701.4;
                    }
                    break;
                case IV:
                    mass = 0.000053837 * Math.pow(x, 4) - 0.0075392 * Math.pow(x, 3) + 0.38829 * Math.pow(x, 2) - 8.8525 * x + 78.898;
                    if (x < 20) {
                        temp = 0.14756 * Math.pow(x, 4) - 0.74281 * Math.pow(x, 3) - 54.993 * Math.pow(x, 2) - 1659.5 * x + 47759;
                    } else {
                        temp = 0.0064059 * Math.pow(x, 4) - 0.98206 * Math.pow(x, 3) + 57.372 * Math.pow(x, 2) - 1655.6 * x + 26772;
                    }
                    if (x < 5) {
                        lum = -3333.3 * Math.pow(x, 4) + 9259.2 * Math.pow(x, 3) + 188890 * Math.pow(x, 2) - 1191000 * x + 2499400;
                    } else if (x < 20) {
                        lum = 17.658 * Math.pow(x, 4) - 1152 * Math.pow(x, 3) + 28441 * Math.pow(x, 2) - 316410 * x + 1344000;
                    } else if (x < 40) {
                        lum = 0.0015793 * Math.pow(x, 4) - 0.23154 * Math.pow(x, 3) + 12.761 * Math.pow(x, 2) - 314.35 * x + 2937.7;
                    } else {
                        lum = -0.2 * x + 14;
                    }
                    break;
                case V:
                    mass = 0.000000010044 * Math.pow(x, 6) - 0.0000027701 * Math.pow(x, 5) + 0.00031297 * Math.pow(x, 4) - 0.018575 * Math.pow(x, 3) + 0.61243 * Math.pow(x, 2) - 10.704 * x + 79.497;
                    if (x < 20) {
                        temp = 0.13864 * Math.pow(x, 4) + 0.76578 * Math.pow(x, 3) - 90.776 * Math.pow(x, 2) - 1554.2 * x + 49712;
                    } else {
                        temp = 0.0015024 * Math.pow(x, 4) - 0.3436 * Math.pow(x, 3) + 28.082 * Math.pow(x, 2) - 1105.5 * x + 23524;
                    }
                    // Mass-luminosity relationship:
                    if (mass < 0.43) {
                        lum = 0.23 * Math.pow(mass, 2.3);
                    } else if (mass < 2) {
                        lum = Math.pow(mass, 4);
                    } else if (mass < 55) {
                        lum = 1.4 * Math.pow(mass, 3.5);
                    } else {
                        lum = 32000 * mass;
                    }
                    break;
            }

            // Non-main-sequence stars vary significantly in size, luminosity, and radius, so 'randomise':
            double tempRand = Util.randDouble(0,1);
            switch (lumClass) { // Switch statement used to increase readability.
                case Ia:
                case Ib:
                case II:
                case III:
                    if (tempRand < 0.8) {
                        mass *= tempRand + 0.25;
                        lum *= tempRand + 0.25;
                    } else {
                        mass *= tempRand * 2.5 - 0.875;
                        lum *= tempRand * 5 - 2.75;
                    }
                    break;
                case IV:
                    if (tempRand < 0.45) {
                        mass *= tempRand + 0.55;
                        lum *= 2 * tempRand + 0.1;
                    } else if (tempRand > 0.55) {
                        mass *= tempRand + 0.45;
                        lum *= 2 * tempRand - 0.1;
                    }
                    break;
            }

            radius = Math.sqrt(lum) * Math.pow(5778/temp, 2);

        } else if (basicType == StarType.WHITE_DWARF) {

            double tempRand = Util.randDouble(0, 1);
            // MASS
            if (tempRand < (13.0/30)) {
                mass = -2 * tempRand + 1.4;
            } else {
                mass = -0.5 * tempRand + 0.825;
            }
            // RADIUS
            if (tempRand < 0.15) {
                radius = 0.03 * tempRand + 0.0025;
            } else if (tempRand < 0.25) {
                radius = 0.02 * tempRand + 0.004;
            } else {
                radius = 0.01 * tempRand + 0.0065;
            }

        } else if (basicType == StarType.BROWN_DWARF) {

            double tempRand = Util.randDouble(0, 1);
            // MASS
            if (tempRand < 0.65) {
                mass = -0.06 * tempRand + 0.073;
            } else if (tempRand < 0.75) {
                mass = -0.08 * tempRand + 0.086;
            } else {
                mass = -0.06 * tempRand + 0.071;
            }
            // RADIUS
            if (tempRand < 0.5) {
                radius = 0.1 * tempRand + 0.065;
            } else {
                radius = 0.01 * tempRand + 0.11;
            }
        }
    }

    public void ageAdjust() {
        if (basicType == StarType.BROWN_DWARF) {

            double tempModifier;
            if (age < 5) {
                tempModifier = -0.05 * age;
            } else {
                tempModifier = -0.1 * age + 0.25;
            }

            // tempModifier is non-negative, so an upper bound is needed, which is randomised slightly.
            double tempRand = Math.min(Util.randDouble(0, 1)+tempModifier, Util.randDouble(0.97, 1.03));
            // TEMPERATURE
            if (tempRand < 0.35) {
                temp = 1000 * tempRand + 650;
            } else {
                temp = 2000 * tempRand + 300;
            }

            // LUMINOSITY
            lum = Math.pow(radius, 2) * Math.pow(temp, 4) / Math.pow(5778, 4);
        } else if (basicType == StarType.WHITE_DWARF) {

            double tempModifier = -0.5 * age + 4.5;

            // An upper and lower bound are needed, which is randomised slightly.
            double tempRand = Util.bound(Util.randDouble(0, 1)+tempModifier, Util.randDouble(-0.03,0.03), Util.randDouble(0.97, 1.03));
            // TEMPERATURE
            if (tempRand < 0.65) {
                temp = 20000 * tempRand + 3000;
            } else if (tempRand < 0.75) {
                temp = 40000 * tempRand - 10000;
            } else {
                temp = 50000 * tempRand - 17500;
            }

            // LUMINOSITY
            lum = Math.pow(radius, 2) * Math.pow(temp, 4) / Math.pow(5778, 4);
        } else {

            switch (specType) {
                case A:
                    if (subclass < 5) {
                        lum *= age + 0.7;
                    } else {
                        if (age < 0.4) {
                            lum *= 0.25 * age + 0.725;
                        } else {
                            lum *= 0.5 * age + 0.625;
                        }
                    }
                    break;
                case F:
                    if (subclass < 5) {
                        lum *= 0.3 * age + 0.5;
                    } else {
                        lum *= 0.2 * age + 0.5;
                    }
                    break;
                case G:
                    if (subclass < 5) {
                        lum *= 0.1 * age + 0.5;
                    } else {
                        if (age < 5) {
                            lum *= 0.1 * age + 0.5;
                        } else if (age > 7) {
                            lum *= 0.1 * age + 0.3;
                        }
                    }
                    break;
                case K:
                    if (subclass < 5) {
                        if (age < 5) {
                            lum *= 0.05 * age + 0.75;
                        } else if (age > 9) {
                            lum *= 0.05 * age + 0.55;
                        }
                    } else {
                        if (age < 3) {
                            lum *= 0.05 * age + 0.85;
                        }
                    }
                    break;
                case M:
                    if (age < 2) {
                        lum *= 0.1 * age + 0.8;
                    }
                    break;
            }

        }
    }

    @Override
    public String toString() {
        String out = "  - STAR #" + id + " (" + basicType.name() + ")";
        if (specType != null || lumClass != null) {
            out += " [";
            if (specType != null) {
                out += specType.name() + Util.round(subclass,1);
            }
            if (lumClass != null) {
                out += lumClass.name();
            }
            out += "]";
        }
        out += "\n(L: " + Util.sf(lum,5) + " sol)";
        out += "\n(M: " + Util.sf(mass,5) + " sol)";
        out += "\n(T: " + Util.sf(temp,5) + " K)";
        out += "\n(R: " + Util.sf(radius,5) + " sol)";
        out += "\n(A: " + Util.sf(age, 5) + " Gy)";
        return out;
    }

    public StarType getBasicType() {
        return basicType;
    }

    public SpecType getSpecType() {
        return specType;
    }

    public LumClass getLumClass() {
        return lumClass;
    }

    public double getSubclass() {
        return subclass;
    }

    public double getMass() {
        return mass;
    }

    public double getTemp() {
        return temp;
    }

    public double getLum() {
        return lum;
    }

    public double getRadius() {
        return radius;
    }

    public void setAge(double age) {
        this.age = age;
    }
}
