package com.cypherfund.bbn.exception;

/**
 *
 * @author ngaielizabeth
 */
public class DuplicateAssignmentException extends RuntimeException {

    public DuplicateAssignmentException() {
    }

    public DuplicateAssignmentException(String message) {
        super(message);
    }

    public DuplicateAssignmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAssignmentException(Throwable cause) {
        super(cause);
    }
}
