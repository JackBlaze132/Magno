// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';

const DeletePeriod = defineAsyncComponent(() => import("@/components/forms/Delete/formDeleteGeneral.vue"));
const DeleteGroup = defineAsyncComponent(() => import("@/components/forms/Delete/formDeleteGeneral.vue"));
const CreateSeedbed = defineAsyncComponent(() => import("@/components/forms/Create/formCreateGeneral.vue"));

export class DeleteFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
      period: {
        component: DeletePeriod,
        props: {
          name: extraProps?.name,
          type: type,
          index: extraProps?.index,
           } },
      group: {
        component: DeleteGroup,
        props: {
          name: extraProps?.name,
          type: type,
          index: extraProps?.index,
        }
      },
      seedbed: { component: CreateSeedbed, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };
    if (type in componentMap) {
        console.log(componentMap[type as keyof typeof componentMap].props);
    }
    return componentMap[type as keyof typeof componentMap] || this.getDefaultComponent();
  }
}
