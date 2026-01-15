package com.unibague.magno.domain.exception.integra;

/**
 * Exception thrown when an invalid type is encountered while processing data from the Integra external system.
 */
public class IntegraInvalidTypeException extends RuntimeException {
    public IntegraInvalidTypeException(String message) {
        super(message);
    }
  public IntegraInvalidTypeException() {
  }
}
