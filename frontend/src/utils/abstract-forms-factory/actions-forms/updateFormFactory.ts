// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUpdateSchemas.json';

const UpdatePeriodo = defineAsyncComponent(() => import("@/components/forms/Put/formUpdateGeneral.vue"));
const CreateGrupo = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));
const CreateSemillero = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));

export class UpdateFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
      periodo: {
        component: UpdatePeriodo,
        props: {
          name: 'periodo',
          type: type,
          fields: schema.periodo,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
           } },
      grupo: { component: CreateGrupo, props: { name: "Crear Grupo", fields: ["nombre", "integrantes"], initialData: {} } },
      semillero: { component: CreateSemillero, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };
    console.log(componentMap[type].props);
    return componentMap[type] || this.getDefaultComponent();
  }
}
