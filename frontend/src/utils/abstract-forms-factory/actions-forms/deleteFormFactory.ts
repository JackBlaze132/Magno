// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { componentStatus } from "@iconify/utils/lib/emoji/test/parse.js";

const DeletePeriod = defineAsyncComponent(() => import("@/components/forms/delete/formDeleteGeneral.vue"));
const DeleteGroup = defineAsyncComponent(() => import("@/components/forms/delete/formDeleteGeneral.vue"));
const DeleteSeedbed = defineAsyncComponent(() => import("@/components/forms/delete/formDeleteGeneral.vue"));

export class DeleteFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
      period: {
        component: DeletePeriod,
        props: {
          name: extraProps?.name,
          type: type,
          label: "periodo",
          index: extraProps?.index,
           } },
      group: {
        component: DeleteGroup,
        props: {
          name: extraProps?.name,
          type: type,
          label: "grupo",
          index: extraProps?.index,
        }
      },
      seedbed: {
        component: DeleteSeedbed,
        props: {
          name: extraProps?.name,
          type: type,
          label: "semillero",
          index: extraProps?.index,
        }
      },
    };
    if (type in componentMap) {
        console.log(componentMap[type as keyof typeof componentMap].props);
    }
    return componentMap[type as keyof typeof componentMap] || this.getDefaultComponent();
  }
}
