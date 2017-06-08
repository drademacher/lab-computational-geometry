package core;

import core.entities.Lion;
import core.entities.Man;

import java.util.ArrayList;


public class State {
    private ArrayList<Man> men;
    private ArrayList<Lion> lions;

    public State() {
        this.men = new ArrayList<>();
        this.lions = new ArrayList<>();
    }

    public State(ArrayList<Man> men, ArrayList<Lion> lions) {
        this.men = men;
        this.lions = lions;
    }

    public ArrayList<Man> getMen() {
        return men;
    }

    public ArrayList<Lion> getLions() {
        return lions;
    }
}
