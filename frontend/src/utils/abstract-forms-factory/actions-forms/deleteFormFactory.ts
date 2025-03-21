// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUpdateSchemas.json';

const DeletePeriodo = defineAsyncComponent(() => import("@/components/forms/Delete/formDeleteGeneral.vue"));
const CreateGrupo = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));
const CreateSemillero = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));

export class DeleteFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
      periodo: {
        component: DeletePeriodo,
        props: {
          name: extraProps?.name,
          type: 'periodo',
          index: extraProps?.index,
           } },
      grupo: { component: CreateGrupo, props: { name: "Crear Grupo", fields: ["nombre", "integrantes"], initialData: {} } },
      semillero: { component: CreateSemillero, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };
    console.log(componentMap[type].props);
    return componentMap[type] || this.getDefaultComponent();
  }
}
