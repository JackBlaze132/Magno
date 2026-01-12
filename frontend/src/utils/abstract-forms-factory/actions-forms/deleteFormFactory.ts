// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { EntityTypes } from '@/utils/abstract-forms-factory/form-types/formsTypes';

const DeleteComponent = defineAsyncComponent(() => import("@/components/forms/delete/formDeleteGeneral.vue"));

/**
 * Factory for creating 'delete' confirmation components for different entities.
 * Returns a generic delete component configured with the name and index of the resource to remove.
 *
 * @class DeleteFormFactory
 * @extends AbstractFormFactory
 * @example
 * // Get delete dialog config for a seedbed
 * const config = new DeleteFormFactory().getComponentConfig('seedbed', { index: 10, name: 'Test Seedbed' });
 */
export class DeleteFormFactory extends AbstractFormFactory {
  /**
   * Returns the component configuration for the given entity type to perform deletion.
   * The `extraProps` parameter is used to supply the resource `name` and `index` to the
   * generic delete component so it can render proper confirmation text.
   *
   * @param {EntityType} type - The entity type to generate a delete confirmation for
   * @param {Record<string, any>} [extraProps] - Optional additional props (e.g., `name`, `index`)
   * @returns {{ component: any; props: Record<string, any> }} Component configuration
   * @example
   * DeleteFormFactory.getComponentConfig('user_diri', { index: 42, name: 'Admin User' });
   */
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
    const componentMap: Partial<Record<EntityType, any>> = {
      period: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          type: type,
          label: "periodo",
          index: extraProps?.index,
           } },
      group: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          type: type,
          label: "grupo",
          index: extraProps?.index,
        }
      },
      seedbed: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          type: type,
          label: "semillero",
          index: extraProps?.index,
        }
      },
      group_profile: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          type: type,
          label: "perfil de grupo",
          index: extraProps?.index,
        }
      },
      seedbed_profile: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          type: type,
          label: "perfil de semillero",
          index: extraProps?.index,
        }
      },
      seedbed_member: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          alt_name: 'miembro',
          type: type,
          label: "miembro de semillero",
          index: extraProps?.index,
        }
      },
      external_seedbed_profile: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          alt_name: 'aliado',
          type: type,
          label: "perfil externo de semillero",
          index: extraProps?.index,
        }
      },
      user_diri: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          alt_name: 'diri',
          type: type,
          label: "miembro DIRI",
          index: extraProps?.index,
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
