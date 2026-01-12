// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { EntityTypes } from '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formCreateSchemas.json';

//const CreatePeriodo = defineAsyncComponent(() => import("@/components/forms/create/formCreateGeneral.vue"));
const CreatePeriod = defineAsyncComponent(() => import("@/components/forms/create/Periods/formCreatePeriods.vue"));
const CreateGroup = defineAsyncComponent(() => import("@/components/forms/create/Groups/formCreateGroups.vue"));
const CreateSeedbed = defineAsyncComponent(() => import("@/components/forms/create/seedbeds/formCreateSeedbed.vue"));
const CreateUser = defineAsyncComponent(() => import("@/components/forms/create/Users/formCreateUsers.vue"));
const CreateRole = defineAsyncComponent(() => import("@/components/forms/create/Roles/formCreateRoles.vue"));
const CreateInternalProfile = defineAsyncComponent(() => import("@/components/forms/create/Users/formCreateInternalProfile.vue"));
const CreateExternalProfile = defineAsyncComponent(() => import("@/components/forms/create/Users/formCreateExternalProfile.vue"));
const CreateGroupProfile = defineAsyncComponent(() => import("@/components/forms/create/Groups/formCreateGroupProfile.vue"));
const CreateSeedbedProfile = defineAsyncComponent(() => import("@/components/forms/create/seedbeds/formCreateSeedbedProfile.vue"));
const CreateSeedbedMember = defineAsyncComponent(() => import("@/components/forms/create/seedbeds/formCreateSeedbedMember.vue"));
const CreateDiriUser = defineAsyncComponent(() => import("@/components/forms/create/Users/formCreateDiriUser.vue"));

/**
 * Factory for creating 'create' form components for different entities.
 * Returns a component and props for a specific entity type using a pre-defined component map.
 *
 * @class CreateFormFactory
 * @extends AbstractFormFactory
 * @example
 * // Get a create form for periods
 * const config = new CreateFormFactory().getComponentConfig('period');
 */
export class CreateFormFactory extends AbstractFormFactory {
  /**
   * Returns the component configuration for the given entity type.
   * The componentMap maps each EntityType to a lazy-loaded Vue component and the props
   * that will be passed to it (including the form fields from the JSON schema).
   *
   * @param {EntityType} type - The entity type to generate a form for
   * @returns {{ component: any; props: Record<string, any> }} Component configuration
   * @example
   * CreateFormFactory.getComponentConfig('seedbed');
   */
  getComponentConfig(type: EntityType) {
    const componentMap: Partial<Record<EntityType, any>> = {
      period: {
        component: CreatePeriod,
        props: {
          type: type,
          label: 'periodo',
          fields: schema.period
        }
      },
      group: {
        component: CreateGroup,
        props: {
          type: type,
          label: "grupo",
          fields: schema.group,
        }
      },

      user_integra: {
        component: CreateUser,
        props: {
          type: type,
          label: "usuario",
          fields: schema.user_integra
        }
      },
      user_diri:{
        component: CreateDiriUser,
        props: {
          type: type,
          label: "usuario DIRI",
          fields: schema.user_diri
        }
      },
      user_external: {
        component: CreateUser,
        props: {
          type: type,
          label: "usuario externo",
          fields: schema.user_external
        }
      },
      role:{
        component: CreateRole,
        props: {
          type: type,
          label: "rol",
          fields: schema.role
        }
      },
      seedbed: {
        component: CreateSeedbed,
        props: {
          type: type,
          label: "semillero",
          fields: schema.seedbed
        }
      },
      functionary_profile: {
        component: CreateInternalProfile,
        props: {
          type: type,
          label: "perfil de funcionario",
          fields: schema.internal_profile
        }
      },
      student_profile: {
        component: CreateInternalProfile,
        props: {
          type: type,
          label: "perfil de estudiante",
          fields: schema.internal_profile
        }
      },
      external_profile: {
        component: CreateExternalProfile,
        props: {
          type: type,
          label: "perfil de aliado externo",
          fields: schema.external_profile
        }
      },
      external_seedbed_profile: {
        component: CreateExternalProfile,
        props: {
          type: type,
          label: "perfil de aliado externo en semillero",
          fields: schema.external_seedbed_profile
        }
      },
      group_profile: {
        component: CreateGroupProfile,
        props: {
          type: type,
          label: "perfil de grupo",
          fields: schema.group_profile
        }
      },
      seedbed_profile: {
        component: CreateSeedbedProfile,
        props: {
          type: type,
          label: "perfil de semillero",
          fields: schema.seedbed_profile
        }
      },
      seedbed_member: {
        component: CreateSeedbedMember,
        props: {
          type: type,
          label: "miembro de semillero",
          fields: schema.seedbed_member
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
