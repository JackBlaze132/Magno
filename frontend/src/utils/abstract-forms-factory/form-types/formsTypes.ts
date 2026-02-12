/**
 * Defines available action and entity types used by the Abstract Form Factory.
 * Provides both a typed union (`ActionType`, `EntityType`) derived from constant arrays and
 * arrays that can be used for UI selects or validation.
 *
 * @example
 * import type { ActionType, EntityType } from './form-types/formsTypes';
 * const action: ActionType = 'create';
 */
// src/types.ts

/**
 * List of supported action types for form operations.
 */
export const ActionTypes: string[] = [
  "create",
  "upload",
  "update",
  "view",
  "delete"
];

/**
 * List of supported entity types for form operations.
 * These correspond to the different resources managed by the application.
 */
export const EntityTypes: string[] = [
  "period",
  "group",
  "seedbed",
  "role",
  "user_integra",
  "user_external",
  "user_diri",
  "functionary_profile",
  "student_profile",
  "external_profile",
  "external_seedbed_profile",
  "group_profile",
  "seedbed_profile",
  "seedbed_coordinator",
  "seedbed_tutor",
  "seedbed_member",
  "certificate",
  "report",
  "logs",
  "dashboard"
];

/**
 * Union type for ActionType derived from {@link ActionTypes}.
 * @typedef {typeof ActionTypes[number]} ActionType
 */
export type ActionType = typeof ActionTypes[number];

/**
 * Union type for EntityType derived from {@link EntityTypes}.
 * @typedef {typeof EntityTypes[number]} EntityType
 */
export type EntityType = typeof EntityTypes[number];
