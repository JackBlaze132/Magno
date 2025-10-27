// src/factories/AbstractComponentFactory.ts
import type { EntityType } from "./form-types/formsTypes";
import { defineComponent, h } from "vue";
import { useFeedbackToast } from "@/composables/useFeedbackToast";

export abstract class AbstractFormFactory {
  abstract getComponentConfig(type: EntityType, extraProps?: Record<string, any>): { component: any; props: Record<string, any> };

  protected getDefaultComponent() {
    return {
      component: defineComponent({
        emits: ['loaded'],
        setup(props, { emit }) {

          // Emitir evento para que los componentes padre puedan manejar esto
          emit('loaded');

          return () => h('div', {
            class: 'text-center bg-background pa-4',
            style:
            'border: thin solid rgba(var(--v-border-color), var(--v-border-opacity)); border-radius: 4px; ',
          }, 'Formulario no encontrado');
        }
      }),
      props: {}
    };
  }
}

