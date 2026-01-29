// src/factories/AbstractComponentFactory.ts
import type { EntityType } from "./form-types/formsTypes";
import { defineComponent, h } from "vue";

/**
 * Abstract base class for form factories.
 * Concrete factories should extend this class and implement {@link getComponentConfig} to
 * return a component and its props for a given entity type and action.
 *
 * This class also provides a helper {@link getDefaultComponent} that returns a placeholder
 * Vue component to render when no matching component is found for a requested type.
 *
 * @abstract
 * @class AbstractFormFactory
 * @example
 * // Example implementation in a concrete factory
 * class MyCreateFactory extends AbstractFormFactory {
 *   getComponentConfig(type) {
 *     if (type === 'period') {
 *       return { component: CreatePeriodComponent, props: { /* ... *\/ } };
 *     }
 *     return this.getDefaultComponent();
 *   }
 * }
 */
export abstract class AbstractFormFactory {
  /**
   * Returns the component configuration (component + props) for the given entity type.
   * Implementations should return a Vue component and the props that will be passed to it.
   *
   * @abstract
   * @param {EntityType} type - The entity type (e.g. 'period', 'seedbed')
   * @param {Record<string, any>} [extraProps] - Optional additional props to merge into the returned props
   * @returns {{ component: any; props: Record<string, any> }} Component config object
   */
  abstract getComponentConfig(type: EntityType, extraProps?: Record<string, any>): { component: any; props: Record<string, any> };

  /**
   * Returns a default placeholder component to render when no specific component is available.
   * The placeholder emits a 'loaded' event immediately so parent overlays can react the same
   * way as when a real component is mounted.
   *
   * @protected
   * @returns {{ component: any; props: Record<string, any> }} Default component config
   */
  protected getDefaultComponent() {
    return {
      component: defineComponent({
        emits: ['loaded'],
        setup(props, { emit }) {

          // Emitir evento para que los componentes padre puedan manejar esto
          emit('loaded');

          return () => h('div', {
            class: 'text-center bg-background pa-4',
            style:
            'border: thin solid rgba(var(--v-border-color), var(--v-border-opacity)); border-radius: 4px; ',
          }, 'Formulario no encontrado');
        }
      }),
      props: {}
    };
  }
}

