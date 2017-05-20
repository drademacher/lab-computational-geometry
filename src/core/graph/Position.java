package core.graph;

import java.util.ArrayList;

/**
 * Created by Danny on 13.05.2017.
 */
public class Position {

    private ArrayList<Position> neighborPositions = new ArrayList<>();
    private GraphEntity graphEntity;

    public Position(GraphEntity graphEntity){
        this.graphEntity = graphEntity;
    }

    public boolean regiterNeighborPosition(Position position){
        return neighborPositions.add(position);
    }

    public GraphEntity getGraphEntity() {
        return graphEntity;
    }

    @Override
    public String toString() {
        String neighborString = "";
        for(Position position : neighborPositions){
            neighborString += position.getGraphEntity().getEntityInfo() + "  ";
        }
        return "Position on "+graphEntity.getEntityInfo()  + "   ##   " + neighborString ;
    }
}
