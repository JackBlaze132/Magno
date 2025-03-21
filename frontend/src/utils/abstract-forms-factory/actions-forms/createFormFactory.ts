// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formCreateSchemas.json';

const CreatePeriodo = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));
const CreateGrupo = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));
const CreateSemillero = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));

export class CreateFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType) {
      const componentMap = {
      periodo: {
        component: CreatePeriodo,
        props: {
          type: type,
          name: 'periodo',
          fields: schema.periodo
        }
      },
      grupo: { component: CreateGrupo, props: { name: "Crear Grupo", fields: ["nombre", "integrantes"], initialData: {} } },
      semillero: { component: CreateSemillero, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };

    return componentMap[type] || this.getDefaultComponent();
  }
}
