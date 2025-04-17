// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUpdateSchemas.json';

const UpdatePeriod = defineAsyncComponent(() => import("@/components/forms/update/formUpdateGeneral.vue"));
const UpdateGroup = defineAsyncComponent(() => import("@/components/forms/update/Groups/formUpdateGroups.vue"));
const UpdateSeedbed = defineAsyncComponent(() => import("@/components/forms/update/seedbeds/formUpdateSeedbeds.vue"));

export class UpdateFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
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
      }
    };
    if (type in componentMap) {
      console.log(componentMap[type as keyof typeof componentMap].props);
  }
  return componentMap[type as keyof typeof componentMap] || this.getDefaultComponent();
  }
}
