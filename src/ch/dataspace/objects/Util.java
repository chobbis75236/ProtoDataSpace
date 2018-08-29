package ch.dataspace.objects;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

final class Util {
    static double randDouble(double min, double max) {
        return (Math.random()*(max-min)+min);
    }

    static int randInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max-min + 1) + min;
    }

    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    static double sf(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        /*
        if (Double.isInfinite(value)) return 88988;
        if (Double.isNaN(value)) return 99899;
        */

        BigDecimal bd = new BigDecimal(value);
        bd = bd.round(new MathContext(places));
        return bd.doubleValue();
    }

    static double bound(double value, double min, double max) {
        return Math.min(max, Math.max(value, min));
    }
}
