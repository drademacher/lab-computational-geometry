package lions_in_plane.core;

import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import lions_in_plane.core.plane.Plane;
import lions_in_plane.core.strategies.man.StrategyEnumMan;
import util.Point;
import util.Random;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CoreController {
    private static final String API_VERSION = "v0.2";
    private boolean editMode = true;

    private double defaultMenSpeed = 1.1;
    private double defaultLionsSpeed = 1;
    private double defaultLionsRange = 0;

    private Plane plane = new Plane();

    public void setEmptyGraph() {
        plane = new Plane();
    }

    public void setDefaultGraph1() {
        setEmptyGraph();

        createLion(new Point(50, 20));
        createLion(new Point(190, 20));
        createLion(new Point(220, 140));
        createLion(new Point(120, 220));
        createLion(new Point(20, 140));

        createLion(new Point(120, 40));
        createLion(new Point(160, 50));
        createLion(new Point(190, 90));
        createLion(new Point(190, 130));
        createLion(new Point(160, 170));
        createLion(new Point(120, 180));
        createLion(new Point(80, 170));
        createLion(new Point(50, 130));
        createLion(new Point(50, 90));
        createLion(new Point(80, 50));

        createLion(new Point(120, 70));
        createLion(new Point(150, 100));
        createLion(new Point(140, 140));
        createLion(new Point(100, 140));


        createMan(new Point(161, 113));


    }

    public void setDefaultGraph2() {
        setEmptyGraph();

        Point[] lions = new Point[]{new Point(385.0, 115.0), new Point(170.0, 90.0), new Point(225.0, 455.0), new Point(295.0, 50.0), new Point(415.0, 290.0), new Point(95.0, 220.0), new Point(155.0, 495.0), new Point(225.0, 360.0), new Point(280.0, 355.0), new Point(470.0, 485.0), new Point(295.0, 50.0), new Point(270.0, 80.0), new Point(185.0, 55.0), new Point(20.0, 265.0), new Point(40.0, 445.0), new Point(210.0, 245.0)};

        for (Point p : lions) {
            createLion(new Point(p.getX(), p.getY()));
        }

        createMan(new Point(171, 337));
    }

    public void setDefaultGraph3() {
        setEmptyGraph();

        Point[] lions = new Point[]{new Point(405.0, 165.0), new Point(75.0, 175.0), new Point(275.0, 235.0), new Point(435.0, 290.0), new Point(295.0, 150.0), new Point(20.0, 10.0), new Point(175.0, 255.0), new Point(480.0, 410.0), new Point(20.0, 425.0), new Point(190.0, 65.0), new Point(315.0, 0.0), new Point(235.0, 255.0), new Point(150.0, 260.0), new Point(440.0, 140.0), new Point(70.0, 170.0), new Point(310.0, 415.0),};

        for (Point p : lions) {
            createLion(new Point(p.getX(), p.getY()));
        }

        createMan(new Point(193, 101));
    }

    public void setRandomConfiguration() {
        setEmptyGraph();
        for (int i = 0; i < 16; i++) {
            createLion(new Point(Random.getRandomInteger(100) * 5, Random.getRandomInteger(100) * 5));
        }
    }

    public void setGraphFromFile(File graphFromFile) {
        setEmptyGraph();
        try {
            BufferedReader br = new BufferedReader(new FileReader(graphFromFile));

            String currentLine;

            //config line
            currentLine = br.readLine();
            if (currentLine == null) {
                throw new Error("wrong file input version: " + CoreController.API_VERSION + " expected");
            }
            String[] lineElements = currentLine.split("##");
            if (!lineElements[2].equals(CoreController.API_VERSION)) {
                throw new Error("wrong file  input version: " + CoreController.API_VERSION + " expected");
            }

            // valid version, read file
            for (int y = 0; (currentLine = br.readLine()) != null; y++) { //Read in MapRow
//                System.out.println(currentLine);

                lineElements = currentLine.split("##");

                Point pos = new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2]));

                switch (lineElements[0]) {
                    case "M":
                        createMan(pos);
//                        setManStrategy(pos, lineElements[3]);
                        setManSpeed(pos, Double.parseDouble(lineElements[4]));

                        System.out.println("" + new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])) + " ## " + Double.parseDouble(lineElements[4]));
                        break;
                    case "L":
                        createLion(pos);
//                        setLionStrategy(pos, lineElements[3]);
                        setLionSpeed(pos, Double.parseDouble(lineElements[4]));
                        setLionRange(pos, Double.parseDouble(lineElements[5]));
                        break;
                    default:
                        throw new IllegalArgumentException("invalid input: " + lineElements[0]);
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void saveGraphToFile(File selectedFile) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

            bufferedWriter.write("C##>>>>>Configuration for LionsInPlane Applet<<<<<##" + API_VERSION);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            for (Man man : plane.getMen()) {
//                System.out.println("M##" + man.getPosition().getX() + "##" + man.getPosition().getY() + "##" + man.getStrategy().toString() + "##" + man.getSpeed());
                bufferedWriter.write("M##" + man.getPosition().getX() + "##" + man.getPosition().getY() + "##" + man.getStrategy().toString() + "##" + man.getSpeed());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            for (Lion lion : plane.getLions()) {
                bufferedWriter.write("L##" + lion.getPosition().getX() + "##" + lion.getPosition().getY() + "##" + lion.getStrategy().toString() + "##" + lion.getSpeed() + "##" + lion.getRange());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getDefaultLionsRange() {
        // TODO: IMPLEMENT THIS
        return defaultLionsRange;
    }

    public void setDefaultLionsRange(double defaultLionsRange) {
        // TODO: IMPLEMENT THIS
        this.defaultLionsRange = defaultLionsRange;
    }

    public double getDefaultMenSpeed() {
        return defaultMenSpeed;
    }

    public void setDefaultMenSpeed(double defaultMenSpeed) {
        // TODO: IMPLEMENT THIS
        // non-negative check e.g.
        this.defaultMenSpeed = defaultMenSpeed;
    }

    public double getDefaultLionsSpeed() {
        return defaultLionsSpeed;
    }

    public void setDefaultLionsSpeed(double defaultLionsSpeed) {
        // TODO: IMPLEMENT THIS
        this.defaultLionsSpeed = defaultLionsSpeed;
    }

    public boolean simulateStep() {
        // TODO: IMPLEMENT THIS
        return false;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public ArrayList<Point> getMenWithManualInput() {
        // TODO: IMPLEMENT THIS
        return new ArrayList<>();
    }

    public ArrayList<Point> getLionsWithManualInput() {
        // TODO: IMPLEMENT THIS
        return new ArrayList<>();
    }

    public void createMan(Point coordinates) {
        plane.addMan(coordinates, defaultMenSpeed);
    }

    public void createLion(Point coordinates) {
        plane.addLion(coordinates, defaultLionsSpeed, defaultLionsRange);
    }

    public void removeMan(Point coordinates) {
        plane.removeMan(coordinates);
    }

    public void removeLion(Point coordinates) {
        plane.removeLion(coordinates);
    }

    public void relocateMan(Point from, Point to) {
        plane.getManByCoordinate(from).setPosition(to);
    }

    public void relocateLion(Point from, Point to) {
        plane.getLionByCoordinate(from).setPosition(to);
    }

    public void setLionRange(Point coordinates, double range) {
        // TODO: IMPLEMENT THIS
    }

    public void setManSpeed(Point coordinates, double speed) {
        // TODO: IMPLEMENT THIS
    }

    public void setLionSpeed(Point coordinates, double speed) {
        // TODO: IMPLEMENT THIS
    }

    public void setManStrategy(Point coordinates, StrategyEnumMan strategyEnum) {
        // TODO: IMPLEMENT THIS
        plane.setManStrategy(coordinates, strategyEnum);
    }

    public void setLionStrategy() {
        // TODO: IMPLEMENT THIS
    }


    public ArrayList<ArrayList<Point>> calcAllPaths() {

        ArrayList<Point> resultPath = new ArrayList<>();
        ArrayList<Point> inductionPath;
        Map<Integer, ArrayList<Point>> lionPaths = new HashMap<>();

        for (int k = 0; k < this.plane.getLionsSize(); k++) {

            inductionPath = resultPath;
            resultPath = new ArrayList<>();
            lionPaths.clear();

            int steps = 0;
            //TODO calc the path until the man escaped OR is caught
            //TODO if (last) lion is to close to end of line
//            while ((resultPath.size() == 0 || insideHull(resultPath.get(resultPath.size() - 1))) && steps < 500) {
            for (int i = 0; i < 200; i++) {
                steps++;

                resultPath = this.plane.calcManPath(k, inductionPath, resultPath);

                for (int j = 0; j <= k; j++) {
                    lionPaths.put(j, this.plane.calcLionPath(j, lionPaths.get(j), resultPath));
                    this.plane.setCalculatedLionPath(lionPaths.get(j), j);
                }
            }
        }

        ArrayList<ArrayList<Point>> allPaths = new ArrayList<>();
        allPaths.add(resultPath);

        lionPaths.forEach((k, v) -> {
            allPaths.add(v);
        });

        return allPaths;

    }
}
