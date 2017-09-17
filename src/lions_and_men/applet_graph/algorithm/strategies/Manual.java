package lions_and_men.applet_graph.algorithm.strategies;

import lions_and_men.applet_graph.algorithm.CoreController;
import lions_and_men.applet_graph.algorithm.entities.Entity;
import lions_and_men.applet_graph.algorithm.graph.Vertex;

import java.util.ArrayList;

public class Manual<T extends Entity> extends Strategy<T> {

    public Manual(CoreController coreController) {
        super(coreController);
    }

    @Override
    public ArrayList<Vertex> calculatePossibleSteps() {
        ArrayList<Vertex> result = new ArrayList<>();
        result.add(entity.getCurrentPosition());
        return result;
    }
}
