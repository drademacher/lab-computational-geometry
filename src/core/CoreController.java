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
    private Strategy manStrategy;
    private Strategy lionStrategy;

    public Graph setEmptyGraph() {
        this.graph = new Graph();
        return this.graph;
    }



    public Graph setDefaultGraph1() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(9, 9));
        this.graph.registerVertex(new Point(1, 4));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
        return this.graph;
    }


    public Graph setDefaultGraph2() {
        this.graph = new Graph();
        this.graph.registerVertex(new Point(5, 5));
        this.graph.registerVertex(new Point(9, 9));
        this.graph.registerVertex(new Point(1, 4));
        this.graph.registerEdge(this.graph.getVertices().get(1), this.graph.getVertices().get(2));
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

    public void saveGraphFromFile(Graph graph) {
        this.graph = new Graph();
        // TODO: implement file saving
    }

}
