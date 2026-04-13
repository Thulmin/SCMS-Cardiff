// Declare that this file belongs to the com.scms.exception package.
package com.scms.exception;

// Declare the unauthorized action exception class.
public class UnauthorizedActionException extends Exception {
    // Construct a new unauthorized action exception instance.
    public UnauthorizedActionException(String message) {
        // Super.
        super(message);
    }

    // Construct a new unauthorized action exception instance.
    public UnauthorizedActionException(String message, Throwable cause) {
        // Super.
        super(message, cause);
    }
}

