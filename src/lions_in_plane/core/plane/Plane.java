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

    public void addMan(Point pos, double speed) {
        men.add(new Man(pos, speed));
        setManStrategy(pos, StrategyEnumMan.Paper);
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

    public void setManStrategy(Point coordinates, StrategyEnumMan strategyEnum){
        Man man = getManByCoordinate(coordinates);
        if(man == null){
            return;
        }
        man.setStrategy(strategyEnum.getStrategy());
    }

    public void setLionStrategy(Point coordinates, StrategyEnumLion strategyEnum){
        Lion lion = getLionByCoordinate(coordinates);
        if(lion == null){
            return;
        }
        lion.setStrategy(strategyEnum.getStrategy());
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
        if (coordinates == null) {
            return null;
        }
        for (Lion lion : lions) {
            if (lion.getPosition().equals(coordinates)) {
                return lion;
            }
        }
        System.out.println("getLionCoord  is NULL");
        return null;
    }

    public ArrayList<Point> calcManPath(int index){
        if(men.size()>0) {
            return men.get(index).getStrategy().getPath(men.get(index), lions, null);
        }
        return new ArrayList<>();
    }

    public ArrayList<Point> calcLionPath(int index){
//        System.out.println("l  "+lions.get(index));
//        System.out.println("strategy: "+lions.get(index).getStrategy());
//        System.out.println("path  "+lions.get(index).getStrategy().getPath(lions.get(index), men, null));
        if(lions.get(index).getStrategy() == null){
            return new ArrayList<>();
        }
        return lions.get(index).getStrategy().getPath(lions.get(index), men, null);
    }

    public int getLionsSize(){
        return lions.size();
    }

}
