// Declare that this file belongs to the com.scms.exception package.
package com.scms.exception;

// Declare the duplicate data exception class.
public class DuplicateDataException extends Exception {
    // Construct a new duplicate data exception instance.
    public DuplicateDataException(String message) {
        // Super.
        super(message);
    }

    // Construct a new duplicate data exception instance.
    public DuplicateDataException(String message, Throwable cause) {
        // Super.
        super(message, cause);
    }
}

