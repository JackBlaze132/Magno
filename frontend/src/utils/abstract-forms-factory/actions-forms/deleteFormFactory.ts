// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';

const DeletePeriodo = defineAsyncComponent(() => import("@/components/forms/Delete/formDeleteGeneral.vue"));
const DeleteGrupo = defineAsyncComponent(() => import("@/components/forms/Delete/formDeleteGeneral.vue"));
const CreateSemillero = defineAsyncComponent(() => import("@/components/forms/Create/formCreateGeneral.vue"));

export class DeleteFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
      periodo: {
        component: DeletePeriodo,
        props: {
          name: extraProps?.name,
          type: type,
          index: extraProps?.index,
           } },
      grupo: {
        component: DeleteGrupo,
        props: {
          name: extraProps?.name,
          type: type,
          index: extraProps?.index,
        }
      },
      semillero: { component: CreateSemillero, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };
    console.log(componentMap[type].props);
    return componentMap[type] || this.getDefaultComponent();
  }
}
