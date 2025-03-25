// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUpdateSchemas.json';

const UpdatePeriodo = defineAsyncComponent(() => import("@/components/forms/Update/formUpdateGeneral.vue"));
const UpdateGrupo = defineAsyncComponent(() => import("@/components/forms/Update/Groups/formUpdateGroups.vue"));
const CreateSemillero = defineAsyncComponent(() => import("@/components/forms/Create/formCreateGeneral.vue"));

export class UpdateFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
      const componentMap = {
      periodo: {
        component: UpdatePeriodo,
        props: {
          type: type,
          fields: schema.periodo,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
      },
      grupo: {
        component: UpdateGrupo,
        props: {
          type: type,
          fields: schema.grupo,
          index: extraProps?.index,
          initialData: extraProps?.initialData,
        }
       },
      semillero: { component: CreateSemillero, props: { name: "Crear Semillero", fields: ["tema", "lider"], initialData: {} } }
    };
    console.log(componentMap[type].props);
    return componentMap[type] || this.getDefaultComponent();
  }
}
