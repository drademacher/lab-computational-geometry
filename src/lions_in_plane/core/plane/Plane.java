package lions_in_plane.core.plane;


import lions_in_plane.core.strategies.lion.StrategyEnumLion;
import lions_in_plane.core.strategies.man.StrategyEnumMan;
import util.Point;

import java.util.ArrayList;

public class Plane {
    private ArrayList<Man> men;
    private ArrayList<Lion> lions;


    public Plane() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
    }

    public void addMan(Point pos, double speed, double epsilon) {
        men.add(new Man(pos, speed, epsilon));
        setManStrategy(pos, StrategyEnumMan.Paper);
        System.out.println("man position @ " + pos);
    }

    public void removeMan(Point coordinates) {
        men.removeIf(man -> man.getPosition() == coordinates);
    }

    public void addLion(Point pos, double speed, double range) {
        lions.add(new Lion(pos, speed, range));
        setLionStrategy(pos, StrategyEnumLion.Greedy);
    }

    public void removeLion(Point coordinates) {
        lions.removeIf(lion -> lion.getPosition() == coordinates);
    }

    public void setManStrategy(Point coordinates, StrategyEnumMan strategyEnum) {
        System.out.println("set man strategy to "+strategyEnum.toString());
        Man man = getManByCoordinate(coordinates);
        if (man == null) {
            return;
        }
        man.setStrategy(strategyEnum.getStrategy());
    }

    public void setLionStrategy(Point coordinates, StrategyEnumLion strategyEnum) {
        Lion lion = getLionByCoordinate(coordinates);
        if (lion == null) {
            System.out.println("no lion, no strategy");
            return;
        }
        if(strategyEnum == null){
            System.out.println("no strategy!!");
        }
        lion.setStrategy(strategyEnum.getStrategy());
    }

    public Man getManByCoordinate(Point coordinates) {
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
        if (men.size() > 0) {
            ArrayList<Point> resultPath = men.get(0).getStrategy().getPath(men.get(0), lions, index, inductionPath);
            men.get(0).setCalculatedPath(resultPath);
            return resultPath;
        }else{
            return new ArrayList<>();
        }
    }

    public void resetManPath(){
        men.get(0).resetPath();
    }

    public ArrayList<Point> calcLionPath(int index, ArrayList<Point> resultPath, ArrayList<Point> manPath) {
        if (lions.get(index).getStrategy() == null) {
            //TODO should not be possible.... but some lions does not have a strategy
            if (resultPath == null) {
                resultPath = new ArrayList<>();
            }
            resultPath.add(lions.get(index).getPosition());
            lions.get(index).setCalculatedPath(resultPath);
            return resultPath;
        }
        if (men.size() > 0) {
            resultPath = lions.get(index).getStrategy().getPath(lions.get(index), men.get(0), manPath, resultPath);
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

//    public void setCalculatedLionPath(ArrayList<Point> calculatedLionPath, int index) {
//        lions.get(index).setCalculatedPath(calculatedLionPath);
//    }


    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }
}