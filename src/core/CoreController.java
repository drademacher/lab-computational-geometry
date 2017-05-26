package core;

import core.entities.Lion;
import core.entities.Man;
import core.graph.Graph;
import core.strategy.Strategy;
import core.util.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Danny on 17.05.2017.
 */
public class CoreController {
    private Graph graph;
    private ArrayList<Man> men;
    private ArrayList<Lion> lions;

    public Graph setEmptyGraph() {
        this.graph = new Graph();
        return this.graph;
    }



    public Graph setDefaultGraph1() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(19, 9));
        this.graph.registerVertex(new Point(9, 4));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        return this.graph;
    }


    public Graph setDefaultGraph2() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 2));
        this.graph.registerVertex(new Point(19, 2));
        this.graph.registerVertex(new Point(22, 15));
        this.graph.registerVertex(new Point(12, 22));
        this.graph.registerVertex(new Point(2, 15));

        this.graph.registerVertex(new Point(12, 4));
        this.graph.registerVertex(new Point(16, 6));
        this.graph.registerVertex(new Point(19, 10));
        this.graph.registerVertex(new Point(19, 14));
        this.graph.registerVertex(new Point(16, 18));
        this.graph.registerVertex(new Point(12, 19));
        this.graph.registerVertex(new Point(8, 18));
        this.graph.registerVertex(new Point(5, 14));
        this.graph.registerVertex(new Point(5, 10));
        this.graph.registerVertex(new Point(8, 6));

        this.graph.registerVertex(new Point(12, 8));
        this.graph.registerVertex(new Point(15, 11));
        this.graph.registerVertex(new Point(14, 15));
        this.graph.registerVertex(new Point(10, 15));
        this.graph.registerVertex(new Point(9, 11));

        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(1));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        this.graph.registerEdge(this.graph.getVertices().get(2), this.graph.getVertices().get(3));
        this.graph.registerEdge(this.graph.getVertices().get(3), this.graph.getVertices().get(4));
        this.graph.registerEdge(this.graph.getVertices().get(4), this.graph.getVertices().get(0));

        this.graph.registerEdge(this.graph.getVertices().get(0), this.graph.getVertices().get(14));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(6));
        this.graph.registerEdge(this.graph.getVertices().get(2), this.graph.getVertices().get(8));
        this.graph.registerEdge(this.graph.getVertices().get(3), this.graph.getVertices().get(10));
        this.graph.registerEdge(this.graph.getVertices().get(4), this.graph.getVertices().get(12));

        this.graph.registerEdge(this.graph.getVertices().get(5), this.graph.getVertices().get(6));
        this.graph.registerEdge(this.graph.getVertices().get(6), this.graph.getVertices().get(7));
        this.graph.registerEdge(this.graph.getVertices().get(7), this.graph.getVertices().get(8));
        this.graph.registerEdge(this.graph.getVertices().get(8), this.graph.getVertices().get(9));
        this.graph.registerEdge(this.graph.getVertices().get(9), this.graph.getVertices().get(10));
        this.graph.registerEdge(this.graph.getVertices().get(10), this.graph.getVertices().get(11));
        this.graph.registerEdge(this.graph.getVertices().get(11), this.graph.getVertices().get(12));
        this.graph.registerEdge(this.graph.getVertices().get(12), this.graph.getVertices().get(13));
        this.graph.registerEdge(this.graph.getVertices().get(13), this.graph.getVertices().get(14));
        this.graph.registerEdge(this.graph.getVertices().get(14), this.graph.getVertices().get(5));

        this.graph.registerEdge(this.graph.getVertices().get(5), this.graph.getVertices().get(15));
        this.graph.registerEdge(this.graph.getVertices().get(7), this.graph.getVertices().get(16));
        this.graph.registerEdge(this.graph.getVertices().get(9), this.graph.getVertices().get(17));
        this.graph.registerEdge(this.graph.getVertices().get(11), this.graph.getVertices().get(18));
        this.graph.registerEdge(this.graph.getVertices().get(13), this.graph.getVertices().get(19));

        this.graph.registerEdge(this.graph.getVertices().get(15), this.graph.getVertices().get(16));
        this.graph.registerEdge(this.graph.getVertices().get(16), this.graph.getVertices().get(17));
        this.graph.registerEdge(this.graph.getVertices().get(17), this.graph.getVertices().get(18));
        this.graph.registerEdge(this.graph.getVertices().get(18), this.graph.getVertices().get(19));
        this.graph.registerEdge(this.graph.getVertices().get(19), this.graph.getVertices().get(15));
        return this.graph;
    }

    public Graph setDefaultGraph3() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(9, 9));
        this.graph.registerVertex(new Point(1, 4));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        return this.graph;
    }

    public Graph setDefaultGraph4() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(9, 9));
        this.graph.registerVertex(new Point(1, 4));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        return this.graph;
    }

    public Graph setDefaultGraph5() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(9, 9));
        this.graph.registerVertex(new Point(1, 4));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        return this.graph;
    }


    public Graph setRandomGraph() {
        this.graph = new Graph();
        // TODO: implement random graph algorithm
        return this.graph;
    }


    public Graph setGraphFromFile(File file) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();  //Skip type
            int yDim = Integer.valueOf(br.readLine().substring(7));  //Read height
            int xDim = Integer.valueOf(br.readLine().substring(6));  //Read width
            this.graph = new Graph(); //init Map without passable fields
            br.readLine();  //Skip map
            String currentLine;
            for (int y = 0; (currentLine = br.readLine()) != null; y++) { //Read in MapRow
                for (int x = 0; x < currentLine.length(); x++) {
//                    if (currentLine.charAt(x) == '.' || currentLine.charAt(x) == 'G' || currentLine.charAt(x) == 'S') {
//                        map.switchPassable(new Vector(x, y));    //Mark passable fields
//                    }

                    // TODO: do something nice with the file input
                }
            }
            return graph;
        } catch (Exception e) {
            // TODO: search up the right exception type
            throw new Exception("test");
        }
    }

    public void saveGraphToFile(File file) {
        this.graph = new Graph();
        // TODO: implement file saving
    }

}
