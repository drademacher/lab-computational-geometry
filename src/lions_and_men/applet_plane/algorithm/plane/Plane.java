package lions_and_men.applet_plane.algorithm.plane;


import lions_and_men.applet_plane.algorithm.strategies.lion.Strategy;
import lions_and_men.applet_plane.algorithm.strategies.lion.StrategyEnumLion;
import lions_and_men.applet_plane.algorithm.strategies.man.StrategyEnumMan;
import lions_and_men.util.Point;
import lions_and_men.util.Random;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Plane Class
 * Controls / manages all entities on plane (man, lions)
 */
public class Plane {
    private Man man;
    private ArrayList<Lion> lions;


    public Plane() {
        this.lions = new ArrayList<>();
    }

    public void addMan(Point pos, double epsilon) {
        man = new Man(pos, epsilon);
        setManStrategy(StrategyEnumMan.Paper);
    }

    public void removeMan() {
        man = null;
    }

    public void addLion(Point pos) {
        Lion lion = new Lion(pos);
        lions.add(lion);
        setLionStrategy(lion, StrategyEnumLion.Greedy);
    }

    public void removeLion(Point coordinates) {
        lions.removeIf(lion -> lion.getPosition().equals(coordinates));
    }

    public void setManStrategy(StrategyEnumMan strategyEnum) {
        if (man == null) {
            return;
        }
        man.setStrategy(strategyEnum.getStrategy());
    }

    public void setLionStrategy(Point coordinates, StrategyEnumLion strategyEnum) {

        Lion lion = getLionByCoordinate(coordinates);
        setLionStrategy(lion, strategyEnum);
    }

    private void setLionStrategy(Lion lion, StrategyEnumLion strategyEnum) {
        if (lion == null) {
            return;
        }
        if (strategyEnum == null) {
            return;
        }
        Strategy strat = strategyEnum.getStrategy();
        lion.setStrategy(strat);
    }

    public void shuffleLionOrder() {
        Collections.shuffle(lions, Random.getRANDOM());
    }

    public Lion getLionByCoordinate(Point coordinates) {
        if (coordinates == null) {
            return null;
        }
        for (Lion lion : lions) {
            if (lion.getPosition().equals(coordinates)) {
                return lion;
            }
        }
        return null;
    }

    public ArrayList<Point> calcManPath(int index, ArrayList<Point> inductionPath) {
        if (man != null) {
            ArrayList<Point> resultPath = man.getStrategy().getPath(man, lions, index, inductionPath);
            man.setCalculatedPath(resultPath);
            return resultPath;
        } else {
            return new ArrayList<>();
        }
    }

    public void resetManPath() {
        if (man == null) {
            return;
        }
        man.resetPath();
    }

    public ArrayList<Point> calcLionPath(int index, ArrayList<Point> resultPath, ArrayList<Point> manPath) {
        if (lions.get(index).getStrategy() == null) {
            System.out.println("*ERROR*   -   lion without strategy! SHOULD NOT HAPPEN");
            //TODO should not be possible.... but some lions does not have a strategy
            if (resultPath == null) {
                resultPath = new ArrayList<>();
            }
            resultPath.add(lions.get(index).getPosition());
            lions.get(index).setCalculatedPath(resultPath);
            return resultPath;
        }
        if (man != null) {
            resultPath = lions.get(index).getStrategy().getPath(lions.get(index), man, manPath, resultPath);
            lions.get(index).setCalculatedPath(resultPath);
        } else {
            resultPath = new ArrayList<Point>() {{
                add(lions.get(index).getPosition());
            }};
        }
        return resultPath;
    }

    public int getLionsSize() {
        return lions.size();
    }

    public Man getMan() {
        return man;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }
}