// src/types.ts
export type ActionType = "create" | "update" | "view" | "delete";

export type EntityType =
  "period" |
  "group" |
  "seedbed" |
  "role"|
  "user_integra" | "external_user" |
  "functionary_profile" |
  "student_profile" |
  "group_profile"
;
export const EntityTypes: EntityType[] = [
  "period",
  "group",
  "seedbed",
  "role",
  "user_integra",
  "external_user",
  "functionary_profile",
  "student_profile",
  "group_profile"
];

