package com.unibague.magno.domain.exception.functionaryprofile;

/**
 * Exception thrown when attempting to create a functionary profile that already exists for a specific user and academic period.
 */
public class FunctionaryProfileAlreadyExistsException extends RuntimeException {
  public FunctionaryProfileAlreadyExistsException(String message) {
    super(message);
  }
    public FunctionaryProfileAlreadyExistsException() {
    }
}
