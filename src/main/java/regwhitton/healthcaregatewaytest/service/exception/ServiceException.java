package regwhitton.healthcaregatewaytest.service.exception;

/**
 * Checked exception throw by services to indicate a business rule has been
 * broken - these can be shown to the user.
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -4428642045570104737L;

    public ServiceException(String message) {
        super(message);
    }
}
