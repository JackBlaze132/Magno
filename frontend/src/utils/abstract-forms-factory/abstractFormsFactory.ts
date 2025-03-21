// src/factories/AbstractComponentFactory.ts
import type { EntityType } from "./form-types/formsTypes";
import { defineComponent } from "vue";

export abstract class AbstractFormFactory {
  abstract getComponentConfig(type: EntityType, extraProps?: Record<string, any>): { component: any; props: Record<string, any> };

  protected getDefaultComponent() {
    return {
      component: defineComponent({ template: "<div>Componente no encontrado</div>" }),
      props: {}
    };
  }
}

