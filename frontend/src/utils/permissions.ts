import type { ActionType, EntityType } from './abstract-forms-factory/form-types/formsTypes';

/**
 * Permission Map based on the API Endpoints CSV.
 * Maps roles to their allowed actions on each entity.
 */
export const PERMISSIONS: Record<string, Partial<Record<EntityType, ActionType[]>>> = {
  'DIRI': {
    'period': ['create', 'update', 'delete', 'view'],
    'group': ['create', 'update', 'delete', 'view'],
    'seedbed': ['create', 'update', 'delete', 'view'],
    'role': ['create', 'update', 'delete', 'view'],
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
    'seedbed_member': ['create', 'update', 'delete', 'view'],
  },
  'COORDINADOR_DE_GRUPO_DE_INVESTIGACION': {
    'period': ['view'],
    'group_profile': ['view', 'create'],
    'seedbed_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_member': ['create', 'update', 'delete', 'view'],
    'external_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_coordinator': ['update', 'view'],
    'seedbed_tutor': ['update', 'view'],
  },
  'COORDINADOR_DE_SEMILLERO': {
    'period': ['view'],
    'group_profile': ['view', 'create'],
    'seedbed_profile': ['view', 'create'],
    'seedbed_member': ['create', 'update', 'delete', 'view'],
    'external_seedbed_profile': ['create', 'update', 'delete', 'view'],
    'seedbed_coordinator': ['view'],
    'seedbed_tutor': ['view', 'create', 'update', 'delete'],
  },
  'TUTOR_DE_SEMILLERO': {
    'period': ['view'],
    'group_profile': ['view'],
    'seedbed': ['view'],
    'seedbed_profile': ['view'],
    'seedbed_member': ['view'],
    'external_profile': ['view'],
  },
  'ESTUDIANTE_LIDER': {
    'period': ['view'],
    'group_profile': ['view'],
    'seedbed': ['view'],
    'seedbed_profile': ['view'],
    'seedbed_member': ['view'],
  },
  'ESTUDIANTE': {
    'period': ['view'],
    'group_profile': ['view'],
    'seedbed': ['view'],
    'seedbed_profile': ['view'],
    'seedbed_member': ['view'],
  }
};

/**
 * Helper to check if a role has permission for an action on an entity.
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
