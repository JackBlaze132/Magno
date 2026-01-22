import { describe, it, expect } from 'vitest'
import formatter from '../formatter'

describe('Formatter singleton', () => {

  describe('externalFormatter', () => {
    it('returns "externo" when true', () => {
      expect(formatter.externalFormatter(true)).toBe('externo')
    })
    it('returns "interno" when false', () => {
      expect(formatter.externalFormatter(false)).toBe('interno')
    })
    it('returns null when input is null or undefined', () => {
      expect(formatter.externalFormatter(null as any)).toBeNull()
      expect(formatter.externalFormatter(undefined as any)).toBeNull()
    })
  })

  describe('periodActivityFormatter', () => {
    it('returns "Activo" when true', () => {
      expect(formatter.periodActivityFormatter(true)).toBe('Activo')
    })
    it('returns "inactivo" when false', () => {
      expect(formatter.periodActivityFormatter(false)).toBe('inactivo')
    })
  })

  describe('dateFormatter', () => {
    it('adjusts timezone by adding T05:00:00.000Z', () => {
      const dateStr = '2024-01-15'
      const formatted = formatter.dateFormatter(dateStr)
      expect(formatted instanceof Date).toBe(true)
      expect(formatted.toISOString()).toContain('2024-01-15T05:00:00.000Z')
    })
  })
})
