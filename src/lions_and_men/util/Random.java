package lions_and_men.util;

public class Random {

    private static java.util.Random RANDOM;

    /**
     * Provides an uniform distributed random double value between 0.0 and 1.0
     *
     * @return an uniform distributed random double value between 0.0 and 1.0
     */
    public static double getRandomDouble() {
        if (RANDOM == null) RANDOM = new java.util.Random();
        return RANDOM.nextDouble();
    }

    /**
     * Provides an uniform distributed random int value between 0 and the input parameter d.
     *
     * @param d upper bound for interval the random int value is chosen from.
     * @return an uniform distributed random int value between 0 and d.
     */
    public static int getRandomInteger(int d) {
        if (RANDOM == null) RANDOM = new java.util.Random();
        return RANDOM.nextInt(d);
    }

    /**
     * Provides a gaussian distributed random double value between 0.0 and 1.0.
     *
     * @return a gaussian distributed random double value between 0.0 and 1.0.
     */
    public static double getRandomGaussian() {
        if (RANDOM == null) RANDOM = new java.util.Random();
        return RANDOM.nextGaussian();
    }

    /**
     * Provides the Random instance.
     *
     * @return the Random instance.
     */
    public static java.util.Random getRANDOM() {
        if (RANDOM == null) RANDOM = new java.util.Random();
        return RANDOM;
    }
}
