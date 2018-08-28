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

    private double mass;
    private double temp;
    private double lum;
    private double radius;

    // Used for a completely random star.
    Star(Object parent) {
        this.id = count;
        count++;
        this.parent = parent;

        genBasicType();
        genClass();
        genAll();
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
        genAll();
    }

    // CLASS AND TYPE

    private void genClass() {
        genSpecType();
        genSubclass();
        genLumClass();
    }

    private void genBasicType() {
        double tempRand = Util.randDouble(0, 1); // Determines basic type.
        if(tempRand < 0.01) basicType = StarType.GIANT;            // 0.01
        else if(tempRand < 0.02) basicType = StarType.CLASS_A;     // 0.01
        else if(tempRand < 0.05) basicType = StarType.CLASS_F;     // 0.03
        else if(tempRand < 0.13) basicType = StarType.CLASS_G;     // 0.08
        else if(tempRand < 0.27) basicType = StarType.CLASS_K;     // 0.14
        else if(tempRand < 0.76) basicType = StarType.CLASS_M;     // 0.49
        else if(tempRand < 0.86) basicType = StarType.WHITE_DWARF; // 0.10
        else if(tempRand < 0.99) basicType = StarType.BROWN_DWARF; // 0.13
        else basicType = StarType.SPECIAL;                         // 0.01
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

    private void genAll() {
        genBasicStats();
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
                    break;
                case Ib:
                    mass = 0.000018055 * Math.pow(x, 4) - 0.0032087 * Math.pow(x, 3) + 0.21771 * Math.pow(x, 2) - 6.4877 * x + 81.512;
                    if (x < 20) {
                        temp = -0.14306 * Math.pow(x, 4) + 9.3751 * Math.pow(x, 3) - 147.57 * Math.pow(x, 2) - 1417.2 * x + 44675;
                    } else {
                        temp = 0.0011439 * Math.pow(x, 4) - 0.21971 * Math.pow(x, 3) + 16.094 * Math.pow(x, 2) - 668.22 * x + 17662;
                    }
                    break;
                case II:
                    mass = 0.000018621 * Math.pow(x, 4) - 0.0034503 * Math.pow(x, 3) + 0.23701 * Math.pow(x, 2) - 6.9642 * x + 82.344;
                    if (x < 20) {
                        temp = -0.091135 * Math.pow(x, 4) + 7.6864 * Math.pow(x, 3) - 135.75 * Math.pow(x, 2) - 1430.8 * x + 45664;
                    } else {
                        temp = 0.0029573 * Math.pow(x, 4) - 0.55328 * Math.pow(x, 3) + 37.9 * Math.pow(x, 2) - 1262.2 * x + 23474;
                    }
                    break;
                case III:
                    mass = 0.000024241 * Math.pow(x, 4) - 0.0040822 * Math.pow(x, 3) + 0.25394 * Math.pow(x, 2) - 7.0001 * x + 77.633;
                    if (x < 20) {
                        temp = 0.065764 * Math.pow(x, 4) + 1.9956 * Math.pow(x, 3) - 77.714 * Math.pow(x, 2) - 1610.3 * x + 46751;
                    } else {
                        temp = 0.0013351 * Math.pow(x, 4) - 0.29368 * Math.pow(x, 3) + 23.548 * Math.pow(x, 2) - 938.69 * x + 21036;
                    }
                    break;
                case IV:
                    mass = 0.000053837 * Math.pow(x, 4) - 0.0075392 * Math.pow(x, 3) + 0.38829 * Math.pow(x, 2) - 8.8525 * x + 78.898;
                    if (x < 20) {
                        temp = 0.14756 * Math.pow(x, 4) - 0.74281 * Math.pow(x, 3) - 54.993 * Math.pow(x, 2) - 1659.5 * x + 47759;
                    } else {
                        temp = 0.0064059 * Math.pow(x, 4) - 0.98206 * Math.pow(x, 3) + 57.372 * Math.pow(x, 2) - 1655.6 * x + 26772;
                    }
                    break;
                case V:
                    mass = 0.000000010044 * Math.pow(x, 6) - 0.0000027701 * Math.pow(x, 5) + 0.00031297 * Math.pow(x, 4) - 0.018575 * Math.pow(x, 3) + 0.61243 * Math.pow(x, 2) - 10.704 * x + 79.497;
                    if (x < 20) {
                        temp = 0.13864 * Math.pow(x, 4) + 0.76578 * Math.pow(x, 3) - 90.776 * Math.pow(x, 2) - 1554.2 * x + 49712;
                    } else {
                        temp = 0.0015024 * Math.pow(x, 4) - 0.3436 * Math.pow(x, 3) + 28.082 * Math.pow(x, 2) - 1105.5 * x + 23524;
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
                        mass *= tempRand + 0.2;
                    } else {
                        mass *= tempRand * 2.5 - 1;
                    }
                    break;
                case IV:
                    if (tempRand < 0.5) {
                        mass *= tempRand + 0.5;
                    } else if (tempRand > 0.6) {
                        mass *= tempRand + 0.4;
                    }
                    break;
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

            radius = Math.sqrt(lum) * Math.pow(5800/temp, 2);

        } else if (basicType == StarType.WHITE_DWARF) {

        } else if (basicType == StarType.BROWN_DWARF) {

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
        out += "\n(L: " + Util.round(lum,3) + " sol)";
        out += "\n(M: " + Util.round(mass,3) + " sol)";
        out += "\n(T: " + Util.round(temp,3) + " K)";
        out += "\n(R: " + Util.round(radius,3) + " sol)";
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
}
