package lions_in_plane.core.plane;


import lions_in_plane.core.strategies.Paper;
import lions_in_plane.core.strategies.StrategyEnum;
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
        setManStrategy(pos, StrategyEnum.Paper);
        men.get(0).getStrategy().getPath(men.get(0), lions);
    }

    public void removeMan(Point coordinates) {
        men.removeIf(man -> man.getPosition() == coordinates);
    }

    public void addLion(Point pos, double speed, double range) {
        lions.add(new Lion(pos, speed, range));
    }

    public void removeLion(Point coordinates) {
        lions.removeIf(lion -> lion.getPosition() == coordinates);
    }

    public void setManStrategy(Point coordinates, StrategyEnum strategyEnum){
        Man man = getManByCoordinate(coordinates);
        if(man == null){
            return;
        }
        man.setStrategy(strategyEnum.getStrategy());
    }

    public Man getManByCoordinate(Point coordinates) {
        // TODO: IMPLEMENT THIS
        if (coordinates == null) {
            return null;
        }
        for (Man man : men) {
            if (man.getPosition().equals(coordinates)) {
                return man;
            }
        }
        return null;
    }

    public Lion getLionByCoordinate(Point coordinates){
        // TODO: IMPLEMENT THIS
        return null;
    }


}
