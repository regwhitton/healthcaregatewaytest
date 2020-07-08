package regwhitton.healthcaregatewaytest.service.exception;

/**
 * Entity passed in an argument was not found.
 * 
 * @see ServiceException
 */
public class NotFoundException extends ServiceException {

    private static final long serialVersionUID = 7854454704649612674L;

    public NotFoundException() {
        super("Not found");
    }
}
