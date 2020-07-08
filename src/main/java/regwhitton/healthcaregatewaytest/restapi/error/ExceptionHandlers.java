package regwhitton.healthcaregatewaytest.restapi.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import regwhitton.healthcaregatewaytest.service.exception.NotFoundException;
import regwhitton.healthcaregatewaytest.service.exception.ServiceException;

/**
 * Presents exception messages within HTTP responses.
 */
@RestControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlers.class);

    /**
     * Handle "not found" business logic exceptions.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleException(NotFoundException nfe) {
        return error(NOT_FOUND, nfe.getMessage());
    }

    /**
     * Handle other business logic exceptions.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleException(ServiceException se) {
        return error(UNPROCESSABLE_ENTITY, se.getMessage());
    }

    /**
     * Handle exceptions from bad incoming JSON.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageConversionException hmce) {
        return error(BAD_REQUEST, "Bad incoming request - please check your JSON");
    }

    /**
     * Handle exceptions from bad parameters.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException matme) {
        return error(BAD_REQUEST, "Cannot convert '" + matme.getName() + "' parameter from value '" +
            matme.getValue() + "'");
    }

    /**
     * All {@link RuntimeException}s are unexpected in terms of business logic, so
     * these are logged and a 500 Internal Server Error response returned.
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Throwable ex) {
        LOG.error("Unexpected exception", ex);
        return error(INTERNAL_SERVER_ERROR, "Internal server error");
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus httpStatus, String message) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(message), httpStatus);
    }
}
