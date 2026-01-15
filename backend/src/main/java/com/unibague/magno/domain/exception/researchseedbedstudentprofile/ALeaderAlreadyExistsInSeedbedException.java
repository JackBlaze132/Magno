package com.unibague.magno.domain.exception.researchseedbedstudentprofile;

/**
 * Exception thrown when attempting to assign a leader role in a research seedbed that already has a leader.
 */
public class ALeaderAlreadyExistsInSeedbedException extends RuntimeException {
    public ALeaderAlreadyExistsInSeedbedException(String message) {
        super(message);
    }
}
