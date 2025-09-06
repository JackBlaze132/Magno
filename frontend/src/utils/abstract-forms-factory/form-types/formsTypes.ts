// src/types.ts

export const ActionTypes: string[] = [
  "create",
  "update",
  "view",
  "delete"
];

export const EntityTypes: string[] = [
  "period",
  "group",
  "seedbed",
  "role",
  "user_integra",
  "user_external",
  "functionary_profile",
  "student_profile",
  "external_profile",
  "external_seedbed_profile",
  "group_profile",
  "seedbed_profile",
  "seedbed_coordinator",
  "seedbed_tutor",
  "seedbed_member"
];
export type ActionType = typeof ActionTypes[number];
export type EntityType = typeof EntityTypes[number];
