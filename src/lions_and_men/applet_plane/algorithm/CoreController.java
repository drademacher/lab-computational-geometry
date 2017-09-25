package lions_and_men.applet_plane.algorithm;

import javafx.scene.control.Alert;
import lions_and_men.applet_plane.algorithm.plane.*;
import lions_and_men.applet_plane.algorithm.strategies.lion.StrategyEnumLion;
import lions_and_men.applet_plane.algorithm.strategies.man.StrategyEnumMan;
import lions_and_men.exceptions.WrongConfigurationException;
import lions_and_men.util.ConvexHull;
import lions_and_men.util.Global;
import lions_and_men.util.Point;
import lions_and_men.util.Random;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CoreController {

    protected Plane plane = new Plane();
    protected double maxX = 0;
    protected double maxY = 0;
    protected double minX = 0;
    protected double minY = 0;
    private boolean editMode = true;

    private int stepsToGoAfterEscape = 100;

    private double defaultMenEpsilon = 0.1;
    private ArrayList<AllPaths> allPathsList = new ArrayList<>();
    private int maxInductionsStep = 0;

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

        Point[] lions = new Point[]{new Point(470.0, 485.0), new Point(385.0, 115.0), new Point(170.0, 90.0), new Point(225.0, 455.0), new Point(295.0, 50.0), new Point(415.0, 290.0), new Point(95.0, 220.0), new Point(155.0, 495.0), new Point(225.0, 360.0), new Point(280.0, 355.0), new Point(270.0, 80.0), new Point(185.0, 55.0), new Point(20.0, 265.0), new Point(40.0, 445.0), new Point(210.0, 245.0)};

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

    public void setDefaultGraph4() {
        setEmptyGraph();

        createLion(new Point(-170, -100));
        createLion(new Point(-12, -50));
        createLion(new Point(-45, -35));
        createLion(new Point(-90, 10));

        createMan(new Point(-125, -80));
    }

    public void setDefaultGraph5() {
        setEmptyGraph();

        createMan(new Point(110, 110));
        createLion(new Point(50, 50));
        createLion(new Point(200, 50));
        createLion(new Point(110, 200));
    }

    public void setRandomConfiguration() {
        setEmptyGraph();
        int NUMBER_OF_LIONS = 16;

        Point[] lionPoints = new Point[NUMBER_OF_LIONS];
        Point manPoint = new Point(Random.getRandomInteger(100) * 5, Random.getRandomInteger(100) * 5);
        createMan(manPoint);
        for (int i = 0; i < NUMBER_OF_LIONS; i++) {
            Point lionPoint = new Point(Random.getRandomInteger(100) * 5, Random.getRandomInteger(100) * 5);
            createLion(lionPoint);
            lionPoints[i] = lionPoint;
        }

        //after random generation the man is not in the ch -> generate 4 more lions to make sure the man is in the ch
        if (!new ConvexHull(lionPoints).insideHull(manPoint)) {
            Point point1 = new Point(0, Random.getRandomInteger(100 - (int) (manPoint.getY() + 1) / 5) * 5 + 5);
            createLion(manPoint.add(point1));

            Point point2 = new Point(0, Random.getRandomInteger((int) (manPoint.getY() + 1) / 5) * 5 + 5);
            createLion(manPoint.sub(point2));

            Point point3 = new Point(Random.getRandomInteger(100 - (int) (manPoint.getX() + 1) / 5) * 5 + 5, 0);
            createLion(manPoint.add(point3));

            Point point4 = new Point(Random.getRandomInteger((int) (manPoint.getX() + 1) / 5) * 5 + 5, 0);
            createLion(manPoint.sub(point4));
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
                throw new Error("wrong file input version: " + Global.API_VERSION + " expected");
            }
            String[] lineElements = currentLine.split("##");
            if (!lineElements[2].equals(Global.API_VERSION)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Wrong file version.");
                alert.showAndWait();
                throw new Error("wrong file  input version: " + Global.API_VERSION + " expected");
            }

            // valid version, read file
            while ((currentLine = br.readLine()) != null) { //Read in MapRow

                lineElements = currentLine.split("##");

                Point pos = new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2]));

                switch (lineElements[0]) {
                    case "M":
                        createMan(pos);
                        setManStrategy(StrategyEnumMan.valueOf(lineElements[3]));
                        setManEpsilon(Double.parseDouble(lineElements[4]));

                        break;
                    case "L":
                        createLion(pos);
                        setLionStrategy(pos, StrategyEnumLion.valueOf(lineElements[3]));
                        break;
                    default:
                        throw new IllegalArgumentException("invalid input: " + lineElements[0]);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not read file.");
            alert.showAndWait();
            e.printStackTrace();
            e.fillInStackTrace();
        }
    }

    public void saveGraphToFile(File selectedFile) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

            bufferedWriter.write("C##>>>>>Configuration for LionsInPlane Applet<<<<<##" + Global.API_VERSION);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Man man = plane.getMan();
            bufferedWriter.write("M##" + man.getPosition().getX() + "##" + man.getPosition().getY() + "##" + man.getStrategy().getName() + "##" + man.getEpsilon());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            for (Lion lion : plane.getLions()) {
                bufferedWriter.write("L##" + lion.getPosition().getX() + "##" + lion.getPosition().getY() + "##" + lion.getStrategy().getName());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getDefaultMenEpsilon() {
        return defaultMenEpsilon;
    }

    public void setDefaultMenEpsilon(double defaultMenEpsilon) {
        this.defaultMenEpsilon = defaultMenEpsilon;
        setManEpsilon(defaultMenEpsilon);
    }


    private void resetInductionsStep() {
        maxInductionsStep = 0;
        calcAllPaths();
    }

    public AllPaths simulateStep() {

        if (maxInductionsStep >= allPathsList.size() || maxInductionsStep < 0) {
            maxInductionsStep = 0;
        }

        AllPaths curAllPathObject = allPathsList.get(maxInductionsStep);
        maxInductionsStep++;
        return curAllPathObject;

    }

    private void calcAllPaths() {

        if (this.plane.getMan() == null || this.plane.getLions() == null || this.plane.getLions().size() < 1) {
            return;
        }

        allPathsList.clear();
        this.plane.getMan().resetPath();
        this.plane.getLions().forEach(Entity::resetPath);

        ArrayList<Point> resultPath = new ArrayList<>();
        ArrayList<Point> inductionPath;
        Map<Integer, ArrayList<Point>> lionPaths = new HashMap<>();

        for (int k = 0; k < this.plane.getLionsSize(); k++) {

            // calculate the ch from the active lions
            ArrayList<Lion> allLions = this.plane.getLions();
            Point[] lionPoints = new Point[k + 1];
            for (int i = 0; i <= k; i++) {
                lionPoints[i] = allLions.get(i).getPosition();
            }
            ConvexHull allLionsHull = new ConvexHull(lionPoints);

            inductionPath = resultPath;
            resultPath = new ArrayList<>();
            lionPaths.clear();
            this.plane.resetManPath();

            int stepsToGo = stepsToGoAfterEscape;
            while (stepsToGo > 0) {

                resultPath = this.plane.calcManPath(k, inductionPath);

                for (int j = 0; j <= k; j++) {
                    lionPaths.put(j, this.plane.calcLionPath(j, lionPaths.get(j), resultPath));
                }


                //check if the lion escaped the ch
                if (resultPath.size() > 10 && !allLionsHull.insideHull(resultPath.get(resultPath.size() - 1))) {
                    stepsToGo--;

                    //we escaped the origin ch and did the extra steps (stepsToGo)
                    if (stepsToGo == 0) {

                        //calculate a new ch from the current positions of all active lions
                        lionPoints = new Point[k + 1];
                        for (int i = 0; i <= k; i++) {
                            lionPoints[i] = lionPaths.get(i).get(lionPaths.get(i).size() - 1);
                        }
                        allLionsHull = new ConvexHull(lionPoints);

                        //check if we escaped this new ch, if not -> do more steps (reset the stepsToGo counter)
                        if (allLionsHull.insideHull(resultPath.get(resultPath.size() - 1))) {
                            stepsToGo = stepsToGoAfterEscape;
                        }
                    }
                }
            }

            ArrayList<ArrayList<Point>> lionPathList = new ArrayList<>();
            lionPaths.forEach((key, value) -> lionPathList.add(value));
            AllPaths allPaths = new AllPaths(resultPath, lionPathList, (k == plane.getLionsSize() - 1));
            allPathsList.add(allPaths);
        }


        //calc bounding box greedy
        maxX = Integer.MIN_VALUE;
        minX = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        for (AllPaths allPaths : allPathsList) {
            for (Point point : allPaths.manPath) {
                maxX = Math.max(maxX, point.getX());
                minX = Math.min(minX, point.getX());
                maxY = Math.max(maxY, point.getY());
                minY = Math.min(minY, point.getY());
            }
            for (ArrayList<Point> lionPath : allPaths.lionPaths) {
                for (Point point : lionPath) {
                    maxX = Math.max(maxX, point.getX());
                    minX = Math.min(minX, point.getX());
                    maxY = Math.max(maxY, point.getY());
                    minY = Math.min(minY, point.getY());
                }
            }
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) throws WrongConfigurationException {
        if (!editMode && (this.plane.getMan() == null || this.plane.getLions() == null || this.plane.getLions().size() < 1)) {
            throw new WrongConfigurationException();
        }
        if (!editMode) {
            resetInductionsStep();
        }
        this.editMode = editMode;
    }

    public void createMan(Point coordinates) {
        if (this.plane.getMan() != null) {
            return;
        }
        plane.addMan(coordinates, defaultMenEpsilon);
    }

    public void createLion(Point coordinates) {
        plane.addLion(coordinates);
    }

    public void removeMan(Point coordinates) {
        plane.removeMan();
    }

    public void removeLion(Point coordinates) {
        plane.removeLion(coordinates);
    }

    public void relocateMan(Point to) {
        plane.getMan().setPosition(to);
    }

    public void relocateLion(Point from, Point to) {
        plane.getLionByCoordinate(from).setPosition(to);
    }

    private void setManEpsilon(double epsilon) {
        if (plane.getMan() != null) {
            plane.getMan().setEpsilon(epsilon);
        }
    }

    public void shuffleLionOrder() {
        plane.shuffleLionOrder();
    }

    private void setManStrategy(StrategyEnumMan strategyEnum) {
        plane.setManStrategy(strategyEnum);
    }

    public void setAllManStrategy(StrategyEnumMan strategyEnum) {
        plane.setManStrategy(strategyEnum);

    }

    private void setLionStrategy(Point coordinates, StrategyEnumLion strategyEnum) {
        plane.setLionStrategy(coordinates, strategyEnum);
    }

    public void setAllLionStrategy(StrategyEnumLion strategyEnum) {
        for (Lion lion : plane.getLions()) {
            plane.setLionStrategy(lion.getPosition(), strategyEnum);
        }
    }

    public int getStepsToGoAfterEscape() {
        return stepsToGoAfterEscape;
    }

    public void setStepsToGoAfterEscape(int stepsToGoAfterEscape) {
        if (stepsToGoAfterEscape > 0) {
            this.stepsToGoAfterEscape = stepsToGoAfterEscape;
        }
    }
}
