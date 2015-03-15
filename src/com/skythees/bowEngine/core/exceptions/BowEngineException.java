package com.skythees.bowEngine.core.exceptions;

/**
 * Created on 06.03.15.
 */
public class BowEngineException extends Exception {
    public BowEngineException() {
        super();
    }

    public BowEngineException(String message) {
        super(message);
    }

    public BowEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public BowEngineException(Throwable cause) {
        super(cause);
    }

    protected BowEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
