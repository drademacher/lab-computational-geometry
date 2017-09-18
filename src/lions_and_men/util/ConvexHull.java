package lions_and_men.util;


import lions_and_men.applet_plane.visualization.Lion;

import java.util.Arrays;
import java.util.List;

public class ConvexHull {
    private Point[] points;
    private Point[] hull = new Point[0];

    public ConvexHull(Point[] points) {
        this.points = points.clone();
        convex_hull();
    }

    public ConvexHull(List<Lion> lions) {
        points = new Point[lions.size()];
        for (int i = 0; i < lions.size(); i++) {
            points[i] = lions.get(i).getPosition();
        }
        convex_hull();
    }

    private double cross(Point O, Point A, Point B) {
        return (A.getX() - O.getX()) * (B.getY() - O.getY()) - (A.getY() - O.getY()) * (B.getX() - O.getX());
    }

    private void convex_hull() {

        if (this.points.length > 1) {
            int n = this.points.length, k = 0;
            this.hull = new Point[2 * n];

            Arrays.sort(this.points);

            // Build lower hull
            for (int i = 0; i < n; ++i) {
                while (k >= 2 && cross(this.hull[k - 2], this.hull[k - 1], this.points[i]) <= 0)
                    k--;
                this.hull[k++] = this.points[i];
            }

            // Build upper hull
            for (int i = n - 2, t = k + 1; i >= 0; i--) {
                while (k >= t && cross(this.hull[k - 2], this.hull[k - 1], this.points[i]) <= 0)
                    k--;
                this.hull[k++] = this.points[i];
            }
            if (k > 1) {
                this.hull = Arrays.copyOfRange(this.hull, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
            }
        } else if (this.points.length <= 1) {
            this.hull = this.points;
        } else {
//            return null;
        }
    }


    public boolean insideHull(Point test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = hull.length - 1; i < hull.length; j = i++) {
            if ((hull[i].getY() > test.getY()) != (hull[j].getY() > test.getY()) &&
                    (test.getX() < (hull[j].getX() - hull[i].getX()) * (test.getY() - hull[i].getY()) / (hull[j].getY()-hull[i].getY()) + hull[i].getX())) {
                result = !result;
            }
        }
        return result;
    }

    public Point[] getPoints() {
        return hull;
    }
}
