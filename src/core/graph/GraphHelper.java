package core.graph;

import core.entities.Entity;
import core.entities.Lion;
import core.entities.Man;

import java.util.*;

/**
 * Created by Jens on 20.05.2017.
 */
public class GraphHelper {

    private static GraphHelper graphHelper;

    public static GraphHelper createGraphHelper(){
        if(graphHelper == null){
            graphHelper = new GraphHelper();
        }
        return graphHelper;
    }

    private GraphHelper(){

    }


    public int BFS(Position startPosition){
        return BFS(startPosition, false);
    }
    public int BFS(Position startPosition, boolean reversed){
        ArrayList<Integer> best = new ArrayList<>();
        for(Position position : startPosition.getAllNeighborPositions()){
            best.add(BFS(startPosition, position));
        }
//        best.sort(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o1 - o2;
//            }
//        });
        if(reversed){
            best.sort(Comparator.comparingInt(Integer::intValue).reversed());
        }else {
            best.sort(Comparator.comparingInt(Integer::intValue));
        }

        for(int i = 0; i < best.size(); i++){
            if(best.get(i) > 0){
                return best.get(i);
            }
        }
        return -1;
    }
    /**
     * use BFS algorithm, give the starting point and a neighborPoint, in which direction BFS should go
     *
     * */
    public int BFS(Position startPosition, Position directionPosition){
        Set<Position> set = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        Position current = null;

        //TODO goal
        boolean lookForMan = false;
        if(startPosition.getAllEntities().get(0) instanceof Man){
            lookForMan=false;
        }
        if(startPosition.getAllEntities().get(0) instanceof Lion){
            lookForMan=true;
        }

        directionPosition.counter = 1;

        set.add(startPosition);
        set.add(directionPosition);
        queue.add(directionPosition);

        while (!queue.isEmpty()){
            current = queue.poll();
            //TODO goal
            for(Entity entity : current.getAllEntities()){
                if(lookForMan){
                    if(entity instanceof Man){
                        return current.counter;
                    }
                }else {
                    if(entity instanceof Lion){
                        return current.counter;
                    }
                }
            }
//            if(current.getAllEntities().size() > 0){
//                return current.counter;
//            }

            for(Position position : current.getAllNeighborPositions()){
                if(!set.contains(position)){
                    position.counter = current.counter+1;
                    set.add(position);
                    queue.add(position);
                }
            }
        }

        return -1;
    }
}
