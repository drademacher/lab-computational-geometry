package core.graph;

import core.entities.Entity;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Position {

    private ArrayList<Position> neighborPositions = new ArrayList<>();
    private GraphEntity graphEntity;
    private ArrayList<Entity> entities = new ArrayList<>();
    private boolean visited = false;

    public Position(GraphEntity graphEntity){
        this.graphEntity = graphEntity;
    }

    public boolean registerNeighborPosition(Position position){
        return neighborPositions.add(position);
    }

    public boolean unregisterNeighborPosition(Position position){
        return neighborPositions.remove(position);
    }

    public boolean registerEntity(Entity entity){
        return entities.add(entity);
    }

    public boolean unregisterEntity(Entity entity){
        return entities.remove(entity);
    }


    /*
     *
     * DEBUG 
     *
     *
     */



    public GraphEntity getGraphEntity() {
        return graphEntity;
    }

    public ArrayList<Position> getAllNeighborPositions(){
        return neighborPositions;
    }

    public ArrayList<Position> startAllPositionsRecursiv(){
        setAllVisitedFalse();
        return getAllPositionsRecursive();
    }
    public void setAllVisitedFalse(){
        if(!visited){
            return;
        }
        visited = false;
        for(Position pos : neighborPositions){
            pos.setAllVisitedFalse();
        }
    }
    public ArrayList<Position> getAllPositionsRecursive(){
        ArrayList<Position> recursiveList = new ArrayList<>();
        if(visited){
            return recursiveList;
        }
        visited = true;
        recursiveList.add(this);
        for(Position pos : neighborPositions){
            recursiveList.addAll(pos.getAllPositionsRecursive());
        }

        return recursiveList;
    }

    @Override
    public String toString() {
        String neighborString = "";
        for(Position position : neighborPositions){
            neighborString += "\n## "+position.getGraphEntity().getGraphEntityInfo();
        }
        return "Position on "+graphEntity.getGraphEntityInfo()  + " Entities:" + entities + "" + neighborString ;
    }
}
