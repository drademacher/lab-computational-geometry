package lions_in_plane.core;

import lions_in_plane.core.plane.Plane;
import util.Point;

import java.io.File;
import java.util.ArrayList;


public class CoreController {
    private boolean editMode = true;

    private double defaultMenSpeed = 1.1;
    private double defaultLionsSpeed = 1;
    private double defaultLionsRange = 0;

    private Plane plane = new Plane();

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setEmptyGraph() {
         plane = new Plane();
    }

    public void setDefaultGraph1() {
        plane = new Plane();
        // TODO: IMPLEMENT THIS
    }

    public void setDefaultGraph2() {
        plane = new Plane();
        // TODO: IMPLEMENT THIS
    }

    public void setDefaultGraph3() {
        plane = new Plane();
        // TODO: IMPLEMENT THIS
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
        // TODO: IMPLEMENT THIS
    }

    public void removeMan(Point coordinates) {
        plane.removeMan(coordinates);
    }

    public void removeLion(Point coordinates) {
        // TODO: IMPLEMENT THIS
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
}
