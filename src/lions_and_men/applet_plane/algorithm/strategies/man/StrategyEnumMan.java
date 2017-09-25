package lions_and_men.applet_plane.algorithm.strategies.man;

/**
 * All provided man strategies
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
