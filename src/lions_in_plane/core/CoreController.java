package lions_in_plane.core;

import lions_in_plane.core.plane.Plane;
import lions_in_plane.core.strategies.man.StrategyEnumMan;
import util.Point;
import util.Random;

import java.io.File;
import java.util.ArrayList;


public class CoreController {
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



    }

    public void setDefaultGraph2() {
        setEmptyGraph();

        // already fixed problems !
        Point[] lions = new Point[]{new Point(385.0, 115.0), new Point(170.0, 90.0), new Point(225.0, 455.0), new Point(295.0, 50.0), new Point(415.0, 290.0), new Point(95.0, 220.0), new Point(155.0, 495.0), new Point(225.0, 360.0), new Point(280.0, 355.0), new Point(470.0, 485.0), new Point(295.0, 50.0), new Point(270.0, 80.0), new Point(185.0, 55.0), new Point(20.0, 265.0), new Point(40.0, 445.0), new Point(210.0, 245.0)};

//        Point[] lions = new Point[]{new Point(405.0, 165.0), new Point(75.0, 175.0), new Point(275.0, 235.0), new Point(435.0, 290.0), new Point(295.0, 150.0), new Point(20.0, 10.0), new Point(175.0, 255.0), new Point(480.0, 410.0), new Point(20.0, 425.0), new Point(190.0, 65.0), new Point(315.0, 0.0), new Point(235.0, 255.0), new Point(150.0, 260.0), new Point(440.0, 140.0), new Point(70.0, 170.0), new Point(310.0, 415.0), };

        for (Point p : lions) {
            createLion(new Point(p.getX(), p.getY()));
        }
    }

    public void setDefaultGraph3() {
        setEmptyGraph();

        // already fixed problems !
//        Point[] lions = new Point[]{new Point(385.0, 115.0), new Point(170.0, 90.0), new Point(225.0, 455.0), new Point(295.0, 50.0), new Point(415.0, 290.0), new Point(95.0, 220.0), new Point(155.0, 495.0), new Point(225.0, 360.0), new Point(280.0, 355.0), new Point(470.0, 485.0), new Point(295.0, 50.0), new Point(270.0, 80.0), new Point(185.0, 55.0), new Point(20.0, 265.0), new Point(40.0, 445.0), new Point(210.0, 245.0)};

        Point[] lions = new Point[]{new Point(405.0, 165.0), new Point(75.0, 175.0), new Point(275.0, 235.0), new Point(435.0, 290.0), new Point(295.0, 150.0), new Point(20.0, 10.0), new Point(175.0, 255.0), new Point(480.0, 410.0), new Point(20.0, 425.0), new Point(190.0, 65.0), new Point(315.0, 0.0), new Point(235.0, 255.0), new Point(150.0, 260.0), new Point(440.0, 140.0), new Point(70.0, 170.0), new Point(310.0, 415.0), };

        for (Point p : lions) {
            createLion(new Point(p.getX(), p.getY()));
        }
    }

    public void setRandomConfiguration() {
        setEmptyGraph();
        for (int i = 0; i < 16; i++) {
            createLion(new Point(Random.getRandomInteger(100) * 5, Random.getRandomInteger(100) * 5));
        }
    }

    public void setGraphFromFile(File graphFromFile) {
        // TODO: IMPLEMENT THIS
    }

    public void saveGraphToFile(File selectedFile) {
        // TODO: IMPLEMENT THIS
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
        // TODO: IMPLEMENT THIS
    }

    public void relocateLion(Point from, Point to) {
        // TODO: IMPLEMENT THIS
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

    public void setManStrategy(Point coordinates, StrategyEnumMan strategyEnum){
        // TODO: IMPLEMENT THIS
        plane.setManStrategy(coordinates, strategyEnum);
    }

    public void setLionStrategy(){
        // TODO: IMPLEMENT THIS
    }

    public ArrayList<Point> calcManPath(int index){
        return plane.calcManPath(index);
    }
    public ArrayList<Point> calcLionPath(int index){
        return plane.calcLionPath(index);
    }

    public int getLionsSize(){
        return this.plane.getLionsSize();
    }
}
