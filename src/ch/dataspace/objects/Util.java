package ch.dataspace.objects;

import java.math.BigDecimal;
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
}
