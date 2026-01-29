/**
 * @fileoverview Permission management system for role-based access control (RBAC).
 * Defines permissions for different user roles and provides utilities to check access rights.
 * @module permissions
 */

import type { ActionType, EntityType } from './abstract-forms-factory/form-types/formsTypes';

/**
 * Permission Map based on the API Endpoints CSV.
 * Maps roles to their allowed actions on each entity.
 *
 * @constant {Record<string, Partial<Record<EntityType, ActionType[]>>>} PERMISSIONS
 * @description Comprehensive permission matrix defining what actions each role can perform on entities.
 *
 * **Roles:**
 * - `DIRI`: Full administrative access to all entities
 * - `COORDINADOR_DE_GRUPO_DE_INVESTIGACION`: Investigation group coordinator
 * - `COORDINADOR_DE_SEMILLERO`: Research seedbed coordinator
 * - `TUTOR_DE_SEMILLERO`: Research seedbed tutor
 * - `ESTUDIANTE_LIDER`: Student leader
 * - `ESTUDIANTE`: Regular student
 * - `USUARIO_SIN_ROL`: User without assigned role
 *
 * **Actions:** create, update, delete, view, upload
 *
 * **Entities:** period, report, logs, group, seedbed, role, user_integra, user_external, etc.
 *
 * @example
 * // Access DIRI permissions for periods
 * const diriPeriodPerms = PERMISSIONS['DIRI']['period']; // ['create', 'update', 'delete', 'view']
 *
 * @example
 * // Check if student can create seedbed profiles
 * const canCreate = PERMISSIONS['ESTUDIANTE']['seedbed_profile']?.includes('create'); // false
 */
export const PERMISSIONS: Record<string, Partial<Record<EntityType, ActionType[]>>> = {
  'DIRI': {
    'dashboard': ['view'],
    'period': ['create', 'update', 'delete', 'view'],
    'report': ['view'],
    'logs': ['view'],
    'group': ['create', 'update', 'delete', 'view'],
    'seedbed': ['create', 'update', 'delete', 'view'],
    'role': ['view'],
    'user_integra': ['create', 'view'],
    'user_external': ['create', 'update', 'delete', 'view'],
    'user_diri': ['create', 'delete', 'view'],
    'functionary_profile': ['create', 'update', 'delete', 'view'],
    'student_profile': ['create', 'update', 'delete', 'view'],
    'external_profile': ['create', 'update', 'delete', 'view'],
    'external_seedbed_profile': ['create', 'update', 'delete', 'view'],
    'group_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_coordinator': ['update', 'view'],
    'seedbed_tutor': ['view', 'update'],
    'seedbed_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_member': ['create', 'update', 'delete', 'view', 'upload'],
  },
  'COORDINADOR_DE_GRUPO_DE_INVESTIGACION': {
    'period': ['view'],
    'certificate': ['view'],
    'group': ['view'],
    'seedbed': ['view'],
    'group_profile': ['view', 'create'],
    'seedbed_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_member': ['create', 'update', 'delete', 'view', 'upload'],
    'external_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_coordinator': ['update', 'view'],
    'seedbed_tutor': ['update', 'view'],
  },
  'COORDINADOR_DE_SEMILLERO': {
    'period': ['view'],
    'certificate': ['view'],
    'group': ['view'],
    'seedbed': ['view'],
    'group_profile': ['view', 'create'],
    'seedbed_profile': ['view', 'create'],
    'seedbed_member': ['create', 'update', 'delete', 'view', 'upload'],
    'external_seedbed_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_coordinator': ['view'],
    'seedbed_tutor': ['view', 'create', 'update', 'delete'],
  },
  'TUTOR_DE_SEMILLERO': {
    'period': ['view'],
    'group_profile': ['view', 'create'],
    'certificate': ['view'],
    'seedbed_profile': ['view', 'create'],
    'seedbed_member': ['view', 'create', 'update', 'delete', 'upload'],
  },
  'ESTUDIANTE_LIDER': {
    'period': ['view'],
    'group_profile': ['view'],
    'seedbed_profile': ['view'],
    'seedbed_member': ['view'],
    'certificate': ['view'],
  },
  'ESTUDIANTE': {
    'period': ['view'],
    'group_profile': ['view'],
    'seedbed_profile': ['view'],
    'seedbed_member': ['view'],
    'certificate': ['view'],
  },
  'USUARIO_SIN_ROL': {
    'period': ['view'],
    'group_profile': ['view',  'create'],
    'seedbed_profile': ['view', 'create'],
    'seedbed_member': ['view'],
  },
};

/**
 * Checks if a role has permission to perform a specific action on an entity.
 * Normalizes the role name by converting to uppercase, replacing spaces with underscores,
 * and removing the 'ROLE_' prefix if present.
 *
 * @function checkPermission
 * @param {string | null} role - The user's role (can include 'ROLE_' prefix or spaces)
 * @param {ActionType} action - The action to check ('create', 'update', 'delete', 'view', 'upload')
 * @param {EntityType} entity - The entity to check permissions for
 * @returns {boolean} True if the role has permission for the action on the entity, false otherwise
 *
 * @example
 * // Check if DIRI can create periods
 * checkPermission('DIRI', 'create', 'period'); // Returns true
 *
 * @example
 * // Check with ROLE_ prefix and spaces
 * checkPermission('ROLE_COORDINADOR DE GRUPO DE INVESTIGACION', 'delete', 'group'); // Returns false
 *
 * @example
 * // Check student permissions
 * checkPermission('ESTUDIANTE', 'view', 'certificate'); // Returns true
 * checkPermission('ESTUDIANTE', 'create', 'period'); // Returns false
 *
 * @example
 * // Handle null role
 * checkPermission(null, 'view', 'period'); // Returns false
 */
export function checkPermission(role: string | null, action: ActionType, entity: EntityType): boolean {
  if (!role) return false;

  const normalizedRole = role.toUpperCase().replace(/\s+/g, '_').replace('ROLE_', '');
  const rolePermissions = PERMISSIONS[normalizedRole];

  if (!rolePermissions) return false;

  const entityPermissions = rolePermissions[entity];
  if (!entityPermissions) return false;

  return entityPermissions.includes(action);
}
