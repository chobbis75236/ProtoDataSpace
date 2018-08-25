package ch.dataspace.objects;

import java.util.Random;

final class Util {
    static double randDouble(double min, double max) {
        return (Math.random()*(max-min)+min);
    }

    static double randInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max-min + 1) + min;
    }
}
