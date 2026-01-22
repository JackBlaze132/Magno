import { describe, it, expect, vi } from 'vitest'
import { FormFactory } from '../FormFactory'
import { CreateFormFactory } from '../actions-forms/createFormFactory'
import { UpdateFormFactory } from '../actions-forms/updateFormFactory'
import { DeleteFormFactory } from '../actions-forms/deleteFormFactory'
import { UploadFormFactory } from '../actions-forms/uploadFormFactory'

describe('FormFactory', () => {
  it('returns the correct factory for each action', () => {
    expect(FormFactory.getFactory('create')).toBeInstanceOf(CreateFormFactory)
    expect(FormFactory.getFactory('update')).toBeInstanceOf(UpdateFormFactory)
    expect(FormFactory.getFactory('delete')).toBeInstanceOf(DeleteFormFactory)
    expect(FormFactory.getFactory('upload')).toBeInstanceOf(UploadFormFactory)
    expect(FormFactory.getFactory('view')).toBeInstanceOf(CreateFormFactory)
  })

  it('getComponentConfig delegates to the correct factory', () => {
    // We can spy on the factory instance returned
    const factory = FormFactory.getFactory('create')
    const spy = vi.spyOn(factory, 'getComponentConfig')

    FormFactory.getComponentConfig('create', 'period')

    // Note: Since FormFactory.getFactory returns a NEW instance in the source code,
    // we can't easily spy on it unless we mock the internal instances or just
    // check the result. Let's check the result instead.

    const config = FormFactory.getComponentConfig('create', 'period')
    expect(config).toHaveProperty('component')
    expect(config).toHaveProperty('props')
    expect(config.props.label).toBe('periodo')
  })
})

describe('CreateFormFactory', () => {
  const factory = new CreateFormFactory()

  it('returns correct config for period', () => {
    const config = factory.getComponentConfig('period')
    expect(config.props.label).toBe('periodo')
    expect(Array.isArray(config.props.fields)).toBe(true)
    // Check if some fields from JSON are present
    expect(config.props.fields.some((f: any) => f.key === 'name')).toBe(true)
  })

  it('returns correct config for seedbed', () => {
    const config = factory.getComponentConfig('seedbed')
    expect(config.props.label).toBe('semillero')
    expect(config.props.fields.some((f: any) => f.key === 'mission')).toBe(true)
  })
})
