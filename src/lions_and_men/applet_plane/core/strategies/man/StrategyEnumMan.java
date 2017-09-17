package lions_and_men.applet_plane.core.strategies.man;


/**
 * Created by Jens on 20.07.2017.
 */
public enum StrategyEnumMan {
    Paper;

    public Strategy getStrategy() {
        switch (this) {
            case Paper:
                return new Paper(this);
            default:
                throw new IllegalArgumentException("invalid input: " + this);
        }
    }
}
