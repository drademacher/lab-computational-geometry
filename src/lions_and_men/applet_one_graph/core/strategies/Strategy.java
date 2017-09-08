package lions_and_men.applet_one_graph.core.strategies;

import lions_and_men.applet_one_graph.core.graph.Vertex;

public interface Strategy {

    Vertex getNextPosition();

    boolean vertexIsValidStep(Vertex vertex);

}
