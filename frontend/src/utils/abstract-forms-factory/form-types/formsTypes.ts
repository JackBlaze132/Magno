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
  "external_user",
  "functionary_profile",
  "student_profile",
  "group_profile",
  "seedbed_profile"
];
export type ActionType = typeof ActionTypes[number];
export type EntityType = typeof EntityTypes[number];
