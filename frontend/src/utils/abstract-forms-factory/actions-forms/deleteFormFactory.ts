// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { componentStatus } from "@iconify/utils/lib/emoji/test/parse.js";

const DeleteComponent = defineAsyncComponent(() => import("@/components/forms/delete/formDeleteGeneral.vue"));

export class DeleteFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
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
      }
    };
    if (type in componentMap) {
        console.log(componentMap[type as keyof typeof componentMap].props);
    }
    return componentMap[type as keyof typeof componentMap] || this.getDefaultComponent();
  }
}
