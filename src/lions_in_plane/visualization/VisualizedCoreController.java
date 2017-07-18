package lions_in_plane.visualization;

import javafx.scene.paint.Color;
import lions_in_plane.core.CoreController;
import util.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class VisualizedCoreController extends CoreController {
    private ArrayList<Man> men;
    private ArrayList<Lion> lions;

    public VisualizedCoreController() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
    }

    private Point[] convexHull() {

        // Sort the points from left to right and bottom to top if same x value
        Collections.sort(lions, (o1, o2) -> {
            if (o1.getPosition().getX() < o2.getPosition().getX() || (o1.getPosition().getX() == o2.getPosition().getX() && o1.getPosition().getY() > o2.getPosition().getY()) ) return -1;
            else return 1;
        });



        Point[] ch = new Point[lions.size() + 1];
        int index = 0;

        // upper part of convex hull
        for (int l = 0; l < lions.size(); l++) {
            ch[index] = lions.get(l).getPosition();
            index++;

            Point q = ch[index-1];
            for (int j = index - 2; j > 0; j--) {
                if (isRightOf(ch[j-1], q, ch[j]) == 1) {
                    ch[j] = q;
                    index--;
                } else {
//                    break;
                }
            }
        }

        // lower part of convex hull
        // point at lions.size() - 1 is already in convex hull (right outermost point!)
        for (int l = lions.size() - 1; l >= 0; l--) {
            ch[index] = lions.get(l).getPosition();
            index++;

            Point q = ch[index-1];
            for (int j = index - 2; j > 0; j--) {
                Point p = ch[j-1];
                Point r = ch[j];
                if (isRightOf(p, q, r) == 1) {
                    ch[j] = q;
                    index--;
                } else {
//                    break;
                }
            }
        }




        int finalIndex = index;
        Point[] finalCh = ch;
        lions.forEach(lion -> {
            for (int j = 0; j < finalIndex; j++) {
                if (finalCh[j].equals(lion.getPosition())) {
                    lion.getShape().setFill(Color.BLACK);
                }
            }
        });

        for (int j = 0; j < finalIndex; j++) {
//            System.out.println(ch[j]);
        }
//        lions.get(0).getShape().setFill(Color.AQUA);

        Point[] result = Arrays.copyOfRange(ch, 0, index-1);
//        System.out.println(Arrays.toString(result));
        return result;
    }

    /**
     * Is r right of line segment pq
     */
    private int isRightOf(Point p, Point q, Point r) {
        double d = (r.getX()- p.getX()) * (q.getY()-p.getY())-(r.getY()-p.getY())*(q.getX()-p.getX());
//        double determinant = p.getX() * q.getY() - p.getX() * r.getY() - p.getY() * q.getX() + p.getY() * r.getX() + q.getX() * r.getY() - q.getY() * r.getX();
//        System.out.println(Math.signum(d));
        return (int) Math.signum(d);
    }

    @Override
    public void setEmptyGraph() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
    }

    @Override
    public void setDefaultGraph1() {
        super.setDefaultGraph1();
        new ConvexHull(convexHull());
    }

    @Override
    public void setDefaultGraph2() {
        super.setDefaultGraph2();
        new ConvexHull(convexHull());
    }

    @Override
    public void setDefaultGraph3() {
        super.setDefaultGraph3();
        new ConvexHull(convexHull());
    }

    public void setRandomConfiguration() {
        super.setRandomConfiguration();
        lions.forEach(lion -> System.out.print(lion.getPosition() + ", "));
        System.out.println();
        new ConvexHull(convexHull());
    }


    @Override
    public void createMan(Point coordinates) {
        super.createMan(coordinates);

        men.add(new Man(coordinates));
    }

    @Override
    public void removeMan(Point coordinates) {
        super.removeMan(coordinates);

        men.stream().filter(man -> man.getPosition() == coordinates).forEach(Man::clear);
        men.removeIf(man -> man.getPosition() == coordinates);
    }

    @Override
    public void createLion(Point coordinates) {
        super.createLion(coordinates);

        lions.add(new Lion(coordinates));

    }

    @Override
    public void removeLion(Point coordinates) {
        super.removeLion(coordinates);

        lions.stream().filter(lion -> lion.getPosition() == coordinates).forEach(Lion::clear);
        lions.removeIf(lion -> lion.getPosition() == coordinates);
    }

    @Override
    public void relocateMan(Point from, Point to) {
        super.relocateMan(from, to);

        men.stream().filter(man -> man.getPosition() == from).forEach(man -> man.setPosition(to));
    }

    @Override
    public void relocateLion(Point from, Point to) {
        super.relocateMan(from, to);

        lions.stream().filter(e -> e.getPosition() == from).forEach(e -> e.setPosition(to));
    }

    @Override
    public void setLionRange(Point coordinates, double range) {
        // TODO: IMPLEMENT THIS
    }
}
