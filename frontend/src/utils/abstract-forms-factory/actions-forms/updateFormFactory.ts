// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import {EntityType, EntityTypes} from '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUpdateSchemas.json';

const UpdatePeriod = defineAsyncComponent(() => import("@/components/forms/update/formUpdateGeneral.vue"));
const UpdateGroup = defineAsyncComponent(() => import("@/components/forms/update/groups/formUpdateGroups.vue"));
const UpdateSeedbed = defineAsyncComponent(() => import("@/components/forms/update/seedbeds/formUpdateSeedbeds.vue"));
const UpdateGroupProfile = defineAsyncComponent(() => import("@/components/forms/update/groups/formUpdateGroupProfile.vue"));
const UpdateSeedbedProfile = defineAsyncComponent(() => import("@/components/forms/update/seedbeds/formUpdateSeedbedProfile.vue"));
const UpdateSeedbedMember = defineAsyncComponent(() => import("@/components/forms/update/seedbeds/formUpdateSeedbedMember.vue"));
const UpdateSeedbedExternalProfile = defineAsyncComponent(() => import("@/components/forms/update/seedbeds/formUpdateSeedbedExternalProfile.vue"));

/**
 * Factory for creating 'update' form components for different entities.
 * Returns a component and props for a specific entity type using a pre-defined component map.
 *
 * @class UpdateFormFactory
 * @extends AbstractFormFactory
 * @example
 * // Get an update form configuration for a seedbed
 * const config = new UpdateFormFactory().getComponentConfig('seedbed', { index: 1, initialData: { name: 'Testing' } });
 */
export class UpdateFormFactory extends AbstractFormFactory {
  /**
   * Returns the component configuration for the given entity type with update-specific props.
   * Extra props (`index`, `initialData`, etc.) are merged into the component props to support
   * pre-filling the form and identifying the resource to update.
   *
   * @param {EntityType} type - The entity type to generate an update form for
   * @param {Record<string, any>} [extraProps] - Optional additional props (e.g., `index`, `initialData`)
   * @returns {{ component: any; props: Record<string, any> }} Component configuration
   * @example
   * // Get update form for a group with pre-filled data
   * UpdateFormFactory.getComponentConfig('group', { index: 12, initialData: { name: 'My Group' } });
   */
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
    const componentMap: Partial<Record<EntityType, any>> = {
      period: {
        component: UpdatePeriod,
        props: {
          type: type,
          label: "periodo",
          fields: schema.period,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      group: {
        component: UpdateGroup,
        props: {
          type: type,
          label: "grupo",
          fields: schema.group,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      seedbed: {
        component: UpdateSeedbed,
        props: {
          type: type,
          label: "semillero",
          fields: schema.seedbed,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      group_profile: {
        component: UpdateGroupProfile,
        props: {
          type: type,
          label: "perfil de grupo",
          fields: schema.group_profile,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      seedbed_profile: {
        component: UpdateSeedbedProfile,
        props: {
          type: type,
          label: "perfil de semillero",
          fields: schema.seedbed_profile,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      seedbed_coordinator: {
        component: UpdateSeedbedProfile,
        props: {
          type: type,
          label: "coordinador de semillero",
          fields: schema.seedbed_coordinator,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      seedbed_tutor: {
        component: UpdateSeedbedProfile,
        props: {
          type: type,
          label: "Tutor de semillero",
          fields: schema.seedbed_tutor,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      seedbed_member: {
        component: UpdateSeedbedMember,
        props: {
          type: type,
          label: "Miembro del semillero",
          fields: schema.seedbed_member,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      external_seedbed_profile: {
        component: UpdateSeedbedExternalProfile,
        props: {
          type: type,
          label: "Perfil de aliado externo",
          fields: schema.external_seedbed_profile,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
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
