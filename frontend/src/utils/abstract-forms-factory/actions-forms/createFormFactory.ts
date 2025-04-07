// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formCreateSchemas.json';

//const CreatePeriodo = defineAsyncComponent(() => import("@/components/forms/create/formCreateGeneral.vue"));
const CreatePeriod = defineAsyncComponent(() => import("@/components/forms/create/Periods/formCreatePeriods.vue"));
const CreateGroup = defineAsyncComponent(() => import("@/components/forms/create/Groups/formCreateGroups.vue"));
const CreateSeedbed = defineAsyncComponent(() => import("@/components/forms/create/seedbeds/formCreateSeedbed.vue"));
const CreateUser = defineAsyncComponent(() => import("@/components/forms/create/Users/formCreateUsers.vue"));
const CreateRole = defineAsyncComponent(() => import("@/components/forms/create/Roles/formCreateRoles.vue"));

export class CreateFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType) {
      const componentMap = {
      period: {
        component: CreatePeriod,
        props: {
          type: type,
          name: 'periodo',
          fields: schema.period
        }
      },
      group: {
        component: CreateGroup,
        props: {
          type: type,
          name: "grupo",
          fields: schema.group,
        }
      },
      user_integra: {
        component: CreateUser,
        props: {
          type: type,
          name: "usuario",
          fields: schema.user_integra
        }
      },
      role:{
        component: CreateRole,
        props: {
          type: type,
          name: "rol",
          fields: schema.role
        }
      },
      seedbed: {
        component: CreateSeedbed,
        props: {
          type: type,
          name: "rol",
          fields: schema.seedbed
        }
      }
    };

    return componentMap[type as keyof typeof componentMap] || this.getDefaultComponent();
  }
}
