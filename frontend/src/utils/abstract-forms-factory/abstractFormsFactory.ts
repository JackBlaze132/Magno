// src/factories/AbstractComponentFactory.ts
import type { EntityType } from "./form-types/formsTypes";
import { defineComponent, h } from "vue";

export abstract class AbstractFormFactory {
  abstract getComponentConfig(type: EntityType, extraProps?: Record<string, any>): { component: any; props: Record<string, any> };

  protected getDefaultComponent() {
    return {
      component: defineComponent({
        render() {
          return h('div', 'Formulario no encontrado');
        }
      }),
      props: {}
    };
  }
}

