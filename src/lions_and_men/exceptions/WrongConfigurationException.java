package lions_and_men.exceptions;

/**
 * customized exception to display an user alert. (the configuration is not ok)
 */
public class WrongConfigurationException extends Exception {
    public WrongConfigurationException() {
        super("Missing entities in this Configuration");
    }
}
