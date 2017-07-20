package lions_in_plane.core.strategies;


/**
 * Created by Jens on 20.07.2017.
 */
public enum StrategyEnum {
    Wait, Paper;

    public Strategy getStrategy() {
        switch (this) {
            case Wait:
                return new Wait();
            case Paper:
                return new Paper();
            default:
                throw new IllegalArgumentException("invalid input: " + this);
        }
    }
}
