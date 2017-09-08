package lions_and_men.applet_two_plane.core.strategies.lion;

/**
 * Created by Jens on 21.07.2017.
 */
public enum StrategyEnumLion {
    Greedy, Wait;

    public Strategy getStrategy() {
        switch (this) {
            case Greedy:
                return new Greedy(this);
            case Wait:
                return new Wait(this);
            default:
                throw new IllegalArgumentException("invalid input: " + this);
        }
    }
}