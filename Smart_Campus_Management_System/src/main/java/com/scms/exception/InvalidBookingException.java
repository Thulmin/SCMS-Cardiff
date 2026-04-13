// Declare that this file belongs to the com.scms.exception package.
package com.scms.exception;

// Declare the invalid booking exception class.
public class InvalidBookingException extends Exception {
    // Construct a new invalid booking exception instance.
    public InvalidBookingException(String message) {
        // Super.
        super(message);
    }

    // Construct a new invalid booking exception instance.
    public InvalidBookingException(String message, Throwable cause) {
        // Super.
        super(message, cause);
    }
}

