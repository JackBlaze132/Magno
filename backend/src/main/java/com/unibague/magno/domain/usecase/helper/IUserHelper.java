package com.unibague.magno.domain.usecase.helper;

/**
 * Helper interface for user-related operations.
 * <p>
 * <strong>Why this helper exists:</strong> This helper is used to encapsulate the specific
 * logic of managing DIRI (administrative) users and their associated functionary profiles.
 * DIRI users require special handling because they exist in a dedicated academic period
 * for administrative purposes, separate from regular academic periods.
 * </p>
 * <p>
 * <strong>Responsibilities:</strong>
 * <ul>
 *   <li>Create functionary profiles for DIRI users in the administrative academic period</li>
 *   <li>Assign the DIRI role and appropriate dependency</li>
 *   <li>Clean up DIRI functionary profiles when users are removed from the DIRI role</li>
 * </ul>
 * </p>
 */
public interface IUserHelper {

    /**
     * Adds a user as a DIRI (administrative) user.
     * <p>
     * Creates a functionary profile for the user in the administrative academic period
     * with the DIRI role and the DIRI dependency.
     * </p>
     *
     * @param diriIdentification the identification number of the user
     * @param diriUserId         the ID of the user to add as DIRI
     */
    void addDiriUser(String diriIdentification, Long diriUserId);

    /**
     * Removes a user from the DIRI (administrative) role.
     * <p>
     * Deletes all functionary profiles with the DIRI role for the specified user.
     * </p>
     *
     * @param diriIdentification the identification number of the user
     * @param diriUserId         the ID of the user to remove from DIRI
     */
    void deleteDiriUser(String diriIdentification, Long diriUserId);
}
