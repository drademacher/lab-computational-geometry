package lions_and_men.exceptions;

public class WrongConfigurationException extends Exception {
    public WrongConfigurationException() {
        super("Missing entities in this Configuration");
    }
}
