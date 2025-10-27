// src/factories/CreateComponentFactory.ts
import { AbstractFormFactory } from "../abstractFormsFactory";
import { defineAsyncComponent } from "vue";
import type { EntityType } from  '@/utils/abstract-forms-factory/form-types/formsTypes';
import { EntityTypes } from '@/utils/abstract-forms-factory/form-types/formsTypes';

const DeleteComponent = defineAsyncComponent(() => import("@/components/forms/delete/formDeleteGeneral.vue"));

export class DeleteFormFactory extends AbstractFormFactory {
  getComponentConfig(type: EntityType, extraProps?: Record<any, any>) {
    const componentMap: Partial<Record<EntityType, any>> = {
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
      },
      seedbed_profile: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          type: type,
          label: "perfil de semillero",
          index: extraProps?.index,
        }
      },
      seedbed_member: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          alt_name: 'miembro',
          type: type,
          label: "miembro de semillero",
          index: extraProps?.index,
        }
      },
      external_seedbed_profile: {
        component: DeleteComponent,
        props: {
          name: extraProps?.name,
          alt_name: 'aliado',
          type: type,
          label: "perfil externo de semillero",
          index: extraProps?.index,
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
