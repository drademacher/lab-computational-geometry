package lions_in_plane.visualization;

import lions_in_plane.core.CoreController;
import util.Point;

import java.util.ArrayList;


public class VisualizedCoreController extends CoreController {
    private ArrayList<Man> men;
    private ArrayList<Lion> lions;

    public VisualizedCoreController() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
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
        super.createMan(coordinates);

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
