// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formCreateSchemas.json';

//const CreatePeriodo = defineAsyncComponent(() => import("@/components/forms/Create/formCreateGeneral.vue"));
const CreatePeriod = defineAsyncComponent(() => import("@/components/forms/Create/Periods/formCreatePeriod.vue"));
const CreateGroup = defineAsyncComponent(() => import("@/components/forms/Create/Groups/formCreateGroups.vue"));
const CreateSeedbed = defineAsyncComponent(() => import("@/components/forms/Create/formCreateGeneral.vue"));

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
        component: CreateSeedbed,
        props: {
          type: type,
          name: "usuario",
          fields: schema.user_integra
        }
      },
      seedbed: { component: CreateSeedbed, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };

    return componentMap[type as keyof typeof componentMap] || this.getDefaultComponent();
  }
}
