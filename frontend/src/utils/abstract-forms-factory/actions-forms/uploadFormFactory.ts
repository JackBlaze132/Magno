// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { EntityTypes } from '@/utils/abstract-forms-factory/form-types/formsTypes';
import schema from '@/schemas/formUploadSchemas.json';

//const CreatePeriodo = defineAsyncComponent(() => import("@/components/forms/create/formCreateGeneral.vue"));
const UploadComponent = defineAsyncComponent(() => import("@components/forms/upload/formUploadGeneral.vue"));

export class UploadFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<string, any>) {
    const componentMap: Partial<Record<EntityType, any>> = {
      seedbed_member: {
        component: UploadComponent,
        props: {
          type: type,
          label: 'Miembro del semillero',
          fields: schema.seedbed_member,
          ...extraProps
        }
      },
    };
    if (!(type in componentMap) || !EntityTypes.includes(type)) {
      console.log( `Componente no encontrado para el tipo: ${type}`);
      return this.getDefaultComponent();
    }
    return componentMap[type as keyof typeof componentMap]
  }
}
