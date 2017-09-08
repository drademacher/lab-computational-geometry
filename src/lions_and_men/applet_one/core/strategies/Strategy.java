package lions_and_men.applet_one.core.strategies;

import lions_and_men.applet_one.core.graph.Vertex;

public interface Strategy {

    Vertex getNextPosition();

    boolean vertexIsValidStep(Vertex vertex);

}
