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

    private GraphHelper() {

    }

    public static GraphHelper createGraphHelper() {
        if (graphHelper == null) {
            graphHelper = new GraphHelper();
        }
        return graphHelper;
    }

    public int bestBFS(Position startPosition) {
        return bestBFS(startPosition, false);
    }

    public int bestBFS(Position startPosition, boolean reversed) {
        return bestBFS(startPosition, reversed, (startPosition.getAllEntities().get(0) instanceof Lion ? Man.class : Lion.class), null);
    }

    public int bestBFS(Position startPosition, boolean reversed, Class<?> targetClass, Class<?> doNotMoveClass) {
        ArrayList<Integer> best = new ArrayList<>();
        for (Position position : startPosition.getAllNeighborPositions()) {
            best.add(BFS(startPosition, position, targetClass, doNotMoveClass));
        }

        if (reversed) {
            best.sort(Comparator.comparingInt(Integer::intValue).reversed());
        } else {
            best.sort(Comparator.comparingInt(Integer::intValue));
        }

        for (int i = 0; i < best.size(); i++) {
            return best.get(i);
        }
        return Integer.MAX_VALUE;
    }

    /**
     * use BFS algorithm, give the starting point and a neighborPoint, in which direction BFS should go
     */
    public int BFS(Position startPosition, Position directionPosition) {
        return BFS(startPosition, directionPosition, (startPosition.getAllEntities().get(0) instanceof Lion ? Man.class : Lion.class), null);
    }

    public int BFS(Position startPosition, Position directionPosition, Class<?> targetClass, Class<?> doNotMoveClass) {

        Set<Position> set = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        Position current = null;

        directionPosition.counter = 1;

        set.add(startPosition);
        set.add(directionPosition);
        queue.add(directionPosition);

        while (!queue.isEmpty()) {
            current = queue.poll();
            for (Entity entity : current.getAllEntities()) {
                if (targetClass.isInstance(entity)) {
                    return current.counter;
                }
            }
            for (Position position : current.getAllNeighborPositions()) {
                if (!set.contains(position)) {
                    position.counter = current.counter + 1;
                    set.add(position);

                    boolean addToQue = true;
                    if (doNotMoveClass != null) {
                        for (Entity entity : position.getAllEntities()) {
                            if (doNotMoveClass.isInstance(entity)) {
                                addToQue = false;
                            }
                        }
                    }

                    if (addToQue) {
                        queue.add(position);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}
