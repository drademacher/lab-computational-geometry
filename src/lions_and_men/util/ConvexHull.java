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


    /**
     * Is r right of line segment pq
     */
    private int isRightOf(Point p, Point q, Point r) {
        double d = (r.getX() - p.getX()) * (q.getY() - p.getY()) - (r.getY() - p.getY()) * (q.getX() - p.getX());
//        double determinant = p.getX() * q.getY() - p.getX() * r.getY() - p.getY() * q.getX() + p.getY() * r.getX() + q.getX() * r.getY() - q.getY() * r.getX();
//        System.out.println(Math.signum(d));
        return (int) Math.signum(d);
    }


    public boolean insideHull(Point p) {
        if (hull.length < 3) {
//            System.out.println("hull to small");
            return false;
        }

        for (int i = 0; i < hull.length - 1; i++) {
            if (isRightOf(hull[i], hull[i + 1], p) < 0) {
                return false;
            }
        }
        if (isRightOf(hull[hull.length - 1], hull[0], p) < 0) {
            return false;
        }

        return true;
    }

    public Point[] getPoints() {
        return hull;
    }
}
