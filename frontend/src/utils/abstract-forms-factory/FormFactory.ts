/**
 * Factory pattern implementation for dynamically creating form components.
 * Provides a centralized way to get the appropriate form factory based on action type.
 * @module FormFactory
 */

// src/factories/ComponentFactory.ts
import type { ActionType, EntityType } from "./form-types/formsTypes";
import { AbstractFormFactory } from "./abstractFormsFactory";
import { CreateFormFactory } from "./actions-forms/createFormFactory";
import { UpdateFormFactory } from "./actions-forms/updateFormFactory";
import { DeleteFormFactory } from "./actions-forms/deleteFormFactory";
import { UploadFormFactory } from "./actions-forms/uploadFormFactory";
// Importar otras fábricas (Edit, View, delete) cuando estén listas

/**
 * Factory class for creating and configuring form components based on action types.
 * Implements the Factory pattern to provide appropriate form factories for different CRUD operations.
 *
 * This class serves as the main entry point for obtaining form component configurations.
 * It maps action types to their corresponding factory implementations and provides methods
 * to retrieve both factories and component configurations.
 *
 * @class FormFactory
 *
 * @example
 * // Get a component configuration for creating a period
 * const config = FormFactory.getComponentConfig('create', 'period');
 *
 * @example
 * // Get a component configuration with extra props
 * const config = FormFactory.getComponentConfig('update', 'user', {
 *   initialData: { name: 'John', email: 'john@example.com' }
 * });
 */
export class FormFactory {
  /**
   * Returns the appropriate form factory instance based on the action type.
   *
   * Maps action types to their factory implementations:
   * - 'create' → CreateFormFactory
   * - 'upload' → UploadFormFactory
   * - 'update' → UpdateFormFactory
   * - 'view' → CreateFormFactory (currently uses create factory)
   * - 'delete' → DeleteFormFactory
   *
   * Returns a new FormFactory instance as fallback if action is not found.
   *
   * @param {ActionType} action - The action type ('create', 'update', 'delete', 'view', 'upload')
   * @returns {AbstractFormFactory} The corresponding form factory instance
   *
   * @example
   * const createFactory = FormFactory.getFactory('create');
   * const deleteFactory = FormFactory.getFactory('delete');
   */
  static getFactory(action: ActionType): AbstractFormFactory {
    const factoryMap: { [key in ActionType]: AbstractFormFactory } = {
      create: new CreateFormFactory(),
      upload: new UploadFormFactory(),
      update: new UpdateFormFactory(),
      view: new CreateFormFactory(),
      delete: new DeleteFormFactory()
    };

    return factoryMap[action] || new FormFactory(); // Fallback a "create"
  }

  /**
   * Retrieves the component configuration for a specific action and entity type.
   * This is the primary method used to get form component configurations throughout the application.
   *
   * @param {ActionType} action - The action to perform ('create', 'update', 'delete', 'view', 'upload')
   * @param {EntityType} type - The entity type (e.g., 'period', 'group', 'seedbed', 'user')
   * @param {Record<string, any>} [extraProps] - Optional additional props to pass to the component
   * @returns {Object} Component configuration object with component reference and props
   * @returns {Component} returns.component - The Vue component to render
   * @returns {Object} returns.props - Props to pass to the component
   *
   * @example
   * // Get create form for academic period
   * const config = FormFactory.getComponentConfig('create', 'period');
   * // Returns: { component: FormCreatePeriod, props: {...} }
   *
   * @example
   * // Get update form with initial data
   * const config = FormFactory.getComponentConfig('update', 'seedbed', {
   *   index: 123,
   *   name: 'Research Seedbed',
   *   initialData: { name: 'My Seedbed', mission: 'Our mission' }
   * });
   *
   * @example
   * // Get delete form
   * const config = FormFactory.getComponentConfig('delete', 'user', {
   *   index: 456,
   *   name: 'John Doe'
   * });
   */
  static getComponentConfig(action: ActionType, type: EntityType , extraProps?: Record<string, any>) {
    return this.getFactory(action).getComponentConfig(type, extraProps);
  }
}
