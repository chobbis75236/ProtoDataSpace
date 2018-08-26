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
    private int subclass;

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
            subclass = Math.max(Util.randInt(0,9), primary.getSubclass());
        } else {
            // Random type, but Giant and higher type than primary are treated as Brown Dwarf.
            genBasicType();
            if (basicType == StarType.GIANT || basicType.getRank() < primary.getBasicType().getRank()) {
                basicType = StarType.BROWN_DWARF;
            }
            genClass();
        }
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
            subclass = Util.randInt(0, 9);
        }
    }

    // BASIC STATS

    private void genBasicStats() {
        genMass();
        genTemp();
        calcLum();
        calcRad();
    }

    private void genMass() {
        double[][] massTable = {
                {  80,   70,   60,   50,   45,   40,   35,   30,   25,   20},
                {17.5, 15.1, 13.0, 11.1,  9.5,  8.2,    7,    6,    5,    4},
                {   3,  2.8,  2.6,  2.5,  2.3,  2.2,    2,  1.9,  1.8,  1.7},
                { 1.6, 1.53, 1.47, 1.42, 1.36, 1.31, 1.26, 1.21, 1.17, 1.12},
                {1.08, 1.05, 1.02, 0.99, 0.96, 0.94, 0.92, 0.89, 0.87, 0.85},
                {0.82, 0.79, 0.75, 0.72, 0.69, 0.66, 0.63, 0.61, 0.56, 0.49},
                {0.46, 0.38, 0.32, 0.26, 0.21, 0.18, 0.15, 0.12, 0.10, 0.08}};
        if (specType != null) {
            mass = massTable[specType.getRank()][subclass];
        }
    }

    private void genTemp() {

    }

    private void calcLum() {

    }

    private void calcRad() {

    }

    @Override
    public String toString() {
        String out = "  - STAR #" + id + " (" + basicType.name() + ")";
        if (specType != null || lumClass != null) {
            out += " [";
            if (specType != null) {
                out += specType.name() + subclass;
            }
            if (lumClass != null) {
                out += lumClass.name();
            }
            out += "]";
        }
        out += "(mass: " + mass + ")";
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

    public int getSubclass() {
        return subclass;
    }
}
