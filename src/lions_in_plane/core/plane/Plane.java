package lions_in_plane.core.plane;


import util.Point;

import java.util.ArrayList;

public class Plane {
    private ArrayList<Man> men;
    private ArrayList<Lion> lions;

    public Plane() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
    }

    public void addMan(Point pos, double speed) {
        men.add(new Man(pos, speed));
    }

    public void removeMan(Point coordinates) {
        men.removeIf(man -> man.getPosition() == coordinates);
    }
}
