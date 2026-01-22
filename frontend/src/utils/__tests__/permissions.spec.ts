import { describe, it, expect } from 'vitest'
import { checkPermission } from '../permissions'

describe('checkPermission', () => {
  it('returns false if role is null or undefined', () => {
    expect(checkPermission(null, 'view', 'period')).toBe(false)
    expect(checkPermission(undefined as any, 'view', 'period')).toBe(false)
  })

  it('correctly identifies DIRI permissions', () => {
    expect(checkPermission('DIRI', 'create', 'period')).toBe(true)
    expect(checkPermission('DIRI', 'delete', 'group')).toBe(true)
    expect(checkPermission('DIRI', 'view', 'logs')).toBe(true)
  })

  it('normalizes role names (case, spaces, prefix)', () => {
    // Should handle 'ROLE_' prefix
    expect(checkPermission('ROLE_DIRI', 'create', 'period')).toBe(true)
    // Should handle lowercase and spaces
    expect(checkPermission('coordinador de semillero', 'view', 'period')).toBe(true)
    // Should handle combination
    expect(checkPermission('ROLE_COORDINADOR DE SEMILLERO', 'view', 'period')).toBe(true)
  })

  it('restricts permissions for regular students', () => {
    expect(checkPermission('ESTUDIANTE', 'view', 'certificate')).toBe(true)
    expect(checkPermission('ESTUDIANTE', 'create', 'period')).toBe(false)
    expect(checkPermission('ESTUDIANTE', 'delete', 'group')).toBe(false)
  })

  it('returns false for unknown roles', () => {
    expect(checkPermission('NON_EXISTENT_ROLE', 'view', 'period')).toBe(false)
  })

  it('returns false for unknown entities or actions', () => {
    expect(checkPermission('DIRI', 'invalid_action' as any, 'period')).toBe(false)
    expect(checkPermission('DIRI', 'view', 'invalid_entity' as any)).toBe(false)
  })
})
