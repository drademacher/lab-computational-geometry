package lions_and_men.util;

/**
 * Created by Jens on 11.09.2017.
 */
public class WrongConfigurationException extends Exception{
    public WrongConfigurationException() {
        super("Missing entities in this Configuration");
    }
}
