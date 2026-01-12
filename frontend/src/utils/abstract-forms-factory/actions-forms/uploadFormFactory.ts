// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { EntityTypes } from '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUploadSchemas.json';

//const CreatePeriodo = defineAsyncComponent(() => import("@/components/forms/create/formCreateGeneral.vue"));
const UploadComponent = defineAsyncComponent(() => import("@components/forms/upload/formUploadGeneral.vue"));

/**
 * Factory for creating 'upload' form components for different entities.
 * Primarily used for bulk-import features (e.g., uploading members via an Excel file).
 *
 * @class UploadFormFactory
 * @extends AbstractFormFactory
 * @example
 * // Get upload component config for seedbed members
 * const config = new UploadFormFactory().getComponentConfig('seedbed_member', { extra: 'prop' });
 */
export class UploadFormFactory extends AbstractFormFactory {
  /**
   * Returns the component configuration for the given entity type to support file uploads.
   * All extra props provided are merged into the props passed to the component.
   *
   * @param {EntityType} type - The entity type to generate an upload form for
   * @param {Record<string, any>} [extraProps] - Optional additional props to be passed to the component
   * @returns {{ component: any; props: Record<string, any> }} Component configuration
   * @example
   * UploadFormFactory.getComponentConfig('seedbed_member', { fileAccept: '.xlsx' });
   */
  getComponentConfig(type: EntityType, extraProps?: Record<string, any>) {
    const componentMap: Partial<Record<EntityType, any>> = {
      seedbed_member: {
        component: UploadComponent,
        props: {
          type: type,
          label: 'Miembro del semillero',
          fields: schema.seedbed_member,
          ...extraProps
        }
      },
    };
    if (!(type in componentMap) || !EntityTypes.includes(type)) {
      console.log( `Componente no encontrado para el tipo: ${type}`);
      return this.getDefaultComponent();
    }
    return componentMap[type as keyof typeof componentMap]
  }
}
