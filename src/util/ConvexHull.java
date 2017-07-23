package util;


import lions_in_plane.visualization.Lion;

import java.util.Arrays;
import java.util.List;

public class ConvexHull {
    private Point[] points;
    private Point[] hull = new Point[0];

    public ConvexHull(Point[] points) {
        this.points = points.clone();
        computeConvexHull();
        trimConvexHull();
    }

    public ConvexHull(List<Lion> lions) {
        points = new Point[lions.size()];
        for (int i = 0; i < lions.size(); i++) {
            points[i] = lions.get(i).getPosition();
        }
        computeConvexHull();
        trimConvexHull();
    }

    private void computeConvexHull() {
        hull = new Point[points.length + 4]; // overflow where hull has temporarily more than n+2 points

        int index = 0;

        if (points.length < 3) {
            return;
        }

        // Sort the points from left to right and bottom to top if same x value
        Arrays.sort(points, (o1, o2) -> {
            if (o1.getX() < o2.getX() || (o1.getX() == o2.getX() && o1.getY() > o2.getY()))
                return -1;
            else return 1;
        });


        // upper part of convex hull
        for (int l = 0; l < points.length; l++) {
            hull[index] = points[l];
            index++;

            Point q = hull[index - 1];
            for (int j = index - 2; j > 0; j--) {
                if (isRightOf(hull[j - 1], q, hull[j]) == 1) {
                    hull[j] = q;
                    index--;
                } else {
//                    break;
                }
            }
        }

        // lower part of convex hull
        // point at lions.size() - 1 is already in convex hull (right outermost point!)
        for (int l = points.length - 1; l >= 0; l--) {
            hull[index] = points[l];
            index++;

            Point q = hull[index - 1];
            for (int j = index - 2; j > 0; j--) {
                Point p = hull[j - 1];
                Point r = hull[j];
                if (isRightOf(p, q, r) == 1) {
                    hull[j] = q;
                    index--;
                } else {
//                    break;
                }
            }
        }
    }

    private void trimConvexHull() {
        if (hull[0] == null) {
            hull = new Point[0];
            return;
        }

        for (int i = 0; i < hull.length; i++) {
            if (hull[i] == null) {
                hull = Arrays.copyOfRange(hull, 0, i - 1);
                return;
            }
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

        for (int i = 0; i < hull.length-1; i++) {
            if (isRightOf(hull[i], hull[i + 1], p) < 1) {
                return false;
            }
        }

        return true;
    }

    public Point[] getPoints() {
        return hull;
    }
}
