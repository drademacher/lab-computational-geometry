package lions_in_plane.core;

import util.Point;

import java.io.File;
import java.util.ArrayList;


public class CoreController {
    private boolean editMode = true;

    private double defaultMenSpeed = 1.1;
    private double defaultLionsSpeed = 1;
    private double defaultLionsRange = 0;


    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setEmptyGraph() {
        // TODO: IMPLEMENT THIS
    }

    public void setDefaultGraph1() {
        // TODO: IMPLEMENT THIS
    }

    public void setDefaultGraph2() {
        // TODO: IMPLEMENT THIS
    }

    public void setDefaultGraph3() {
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
        // TODO: IMPLEMENT THIS
    }

    public void createLion(Point coordinates) {
        // TODO: IMPLEMENT THIS
    }

    public void removeMan(Point coordinates) {
        // TODO: IMPLEMENT THIS
    }

    public void removeLion(Point coordinates) {
        // TODO: IMPLEMENT THIS
    }

    public void relocateMan(Point coordinates) {
        // TODO: IMPLEMENT THIS
    }

    public void relocateLion(Point coordinates) {
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