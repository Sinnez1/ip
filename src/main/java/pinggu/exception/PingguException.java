package pinggu.exception;

/**
 * Exception class for error messages due to missing user inputs.
 */
public class PingguException extends Exception {
    /**
     * Initializes PingguException object with error message.
     *
     * @param msg The error message.
     */
    public PingguException(String msg) {
        super(msg);
    }
}
