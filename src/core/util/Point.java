package core.util;


public class Point {

    private int x;
    private int y;

    /**
     * Init Vector instance with the input params.
     *
     * @param x init x value
     * @param y init y value
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Provides current x value of the vector.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Provides current y value of the vector.
     *
     * @return y
     * @since 1.0
     */
    public int getY() {
        return y;
    }


    public Point add(Point other) {
        return new Point(x + other.getX(), y + other.getY());
    }


    public Point sub(Point other) {
        return new Point(x - other.getX(), y - other.getY());
    }

    /**
     * Provides vector multiplication with a single integer value.
     *
     * @param factor multiplication factor.
     * @return A new Vector instance representing the product of the input factor and this vector.
     */
    public Point mul(int factor) {
        return new Point(x * factor, y * factor);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point that = (Point) o;

        if (x != that.x) return false;
        return y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
