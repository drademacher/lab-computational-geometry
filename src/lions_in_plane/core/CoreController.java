package lions_in_plane.core;

import lions_in_plane.core.plane.AllPaths;
import lions_in_plane.core.plane.Lion;
import lions_in_plane.core.plane.Man;
import lions_in_plane.core.plane.Plane;
import lions_in_plane.core.strategies.lion.StrategyEnumLion;
import lions_in_plane.core.strategies.man.StrategyEnumMan;
import util.Constants;
import util.Point;
import util.Random;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CoreController {
    
    private boolean editMode = true;

    private double defaultMenEpsilon = 0.1;
    private double defaultLionsSpeed = 1;
    private double defaultLionsRange = 5;
    private double maxLionSpeed = defaultLionsSpeed;

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

        Point[] lions = new Point[]{ new Point(470.0, 485.0),new Point(385.0, 115.0), new Point(170.0, 90.0), new Point(225.0, 455.0), new Point(295.0, 50.0), new Point(415.0, 290.0), new Point(95.0, 220.0), new Point(155.0, 495.0), new Point(225.0, 360.0), new Point(280.0, 355.0), new Point(295.0, 50.0), new Point(270.0, 80.0), new Point(185.0, 55.0), new Point(20.0, 265.0), new Point(40.0, 445.0), new Point(210.0, 245.0)};

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
                throw new Error("wrong file input version: " + Constants.API_VERSION + " expected");
            }
            String[] lineElements = currentLine.split("##");
            if (!lineElements[2].equals(Constants.API_VERSION)) {
                throw new Error("wrong file  input version: " + Constants.API_VERSION + " expected");
            }

            // valid version, read file
            while ((currentLine = br.readLine()) != null) { //Read in MapRow
//                System.out.println(currentLine);

                lineElements = currentLine.split("##");

                Point pos = new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2]));

                switch (lineElements[0]) {
                    case "M":
                        createMan(pos);
                        setManStrategy(pos, StrategyEnumMan.valueOf(lineElements[3]));
                        setManEpsilon(pos, Double.parseDouble(lineElements[4]));

                        // System.out.println("" + new Point(Double.parseDouble(lineElements[1]), Double.parseDouble(lineElements[2])) + " ## " + Double.parseDouble(lineElements[4]));
                        break;
                    case "L":
                        createLion(pos);
                        setLionStrategy(pos, StrategyEnumLion.valueOf(lineElements[3]));
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
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile));

            bufferedWriter.write("C##>>>>>Configuration for LionsInPlane Applet<<<<<##" + Constants.API_VERSION);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            for (Man man : plane.getMen()) {
//                System.out.println("M##" + man.getPosition().getX() + "##" + man.getPosition().getY() + "##" + man.getStrategy().toString() + "##" + man.getSpeed());
                bufferedWriter.write("M##" + man.getPosition().getX() + "##" + man.getPosition().getY() + "##" + man.getStrategy().toString() + "##" + man.getEpsilon());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

            for (Lion lion : plane.getLions()) {
                System.out.println(lion.getStrategy());
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
        return defaultLionsRange;
    }

    public void setDefaultLionsRange(double defaultLionsRange) {
        this.defaultLionsRange = defaultLionsRange;
    }

    public double getDefaultMenEpsilon() {
        return defaultMenEpsilon;
    }

    public void setDefaultMenEpsilon(double defaultMenEpsilon) {
        this.defaultMenEpsilon = defaultMenEpsilon;
    }

    public double getDefaultLionsSpeed() {
        return defaultLionsSpeed;
    }

    public void setDefaultLionsSpeed(double defaultLionsSpeed) {
        this.defaultLionsSpeed = defaultLionsSpeed;
    }

    private void calcMaxLionSpeed(){
        maxLionSpeed = 0;
        for(Lion lion : plane.getLions()){
            setMaxLionSpeed(lion.getSpeed());
        }
    }
    private void setMaxLionSpeed(double speed){
        if(speed > maxLionSpeed){
            maxLionSpeed = speed;
            for(Man man : plane.getMen()){
                man.setSpeed(maxLionSpeed);
            }
        }
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
        plane.addMan(coordinates, maxLionSpeed, defaultMenEpsilon);
    }

    public void createLion(Point coordinates) {
        plane.addLion(coordinates, defaultLionsSpeed, defaultLionsRange);
        setMaxLionSpeed(defaultLionsSpeed);
    }

    public void removeMan(Point coordinates) {
        plane.removeMan(coordinates);
    }

    public void removeLion(Point coordinates) {
        plane.removeLion(coordinates);
        calcMaxLionSpeed();
    }

    public void relocateMan(Point from, Point to) {
        plane.getManByCoordinate(from).setPosition(to);
    }

    public void relocateLion(Point from, Point to) {
        plane.getLionByCoordinate(from).setPosition(to);
    }

    public void setLionRange(Point coordinates, double range) {
        plane.getLionByCoordinate(coordinates).setRange(range);
    }

    public void setManEpsilon(Point coordinates, double epsilon) {
        plane.getManByCoordinate(coordinates).setEpsilon(epsilon);
    }

    public void setLionSpeed(Point coordinates, double speed) {
        plane.getLionByCoordinate(coordinates).setSpeed(speed);
        setMaxLionSpeed(speed);
    }

    public void setManStrategy(Point coordinates, StrategyEnumMan strategyEnum) {
        plane.setManStrategy(coordinates, strategyEnum);
    }

    public void setAllManStrategy(StrategyEnumMan strategyEnum){
        for(Man man : plane.getMen()){
            plane.setManStrategy(man.getPosition(), strategyEnum);
        }
    }

    public void setLionStrategy(Point coordinates, StrategyEnumLion strategyEnum) {
        plane.setLionStrategy(coordinates, strategyEnum);
    }

    public void setAllLionStrategy(StrategyEnumLion strategyEnum){
        for(Lion lion : plane.getLions()){
            plane.setLionStrategy(lion.getPosition(), strategyEnum);
        }
    }


    protected AllPaths calcAllPaths(int maxInductionsStep) {

        ArrayList<Point> resultPath = new ArrayList<>();
        ArrayList<Point> inductionPath;
        Map<Integer, ArrayList<Point>> lionPaths = new HashMap<>();

        for (int k = 0; k < Math.min(this.plane.getLionsSize(), maxInductionsStep); k++) {

            inductionPath = resultPath;
            resultPath = new ArrayList<>();
            lionPaths.clear();
            this.plane.resetManPath();

            int steps = 0;
            //TODO calc the path until the man escaped OR is caught
            //TODO if (last) lion is to close to end of line
//            while ((resultPath.size() == 0 || insideHull(resultPath.get(resultPath.size() - 1))) && steps < 500) {

            Point[] lionPoints = new Point[k+1];
            for (int j = 0; j <= k; j++) {
                lionPoints[j]=this.plane.getLions().get(j).getCalculatedLastPosition();
            }


            for (int i = 0; i < 200; i++) {
//            while(resultPath.size() < 20 || new ConvexHull(lionPoints).insideHull(this.plane.getMen().get(0).getPosition())){
                steps++;

                resultPath = this.plane.calcManPath(k, inductionPath);

                for (int j = 0; j <= k; j++) {
                    lionPaths.put(j, this.plane.calcLionPath(j, lionPaths.get(j), resultPath));
//                    this.plane.setCalculatedLionPath(lionPaths.get(j), j);
                }
            }
        }

        ArrayList<ArrayList<Point>> lionPathList = new ArrayList<>();
        lionPaths.forEach((k, v) -> {
            lionPathList.add(v);
        });
        AllPaths allPaths = new AllPaths(resultPath, lionPathList);

        return allPaths;

    }
}
