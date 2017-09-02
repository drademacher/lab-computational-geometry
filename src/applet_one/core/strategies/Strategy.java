package applet_one.core.strategies;

import applet_one.core.graph.Vertex;

public interface Strategy {

    Vertex getNextPosition();

    boolean vertexIsValidStep(Vertex vertex);

}
