// src/utils/ComponentFactory.ts
import { defineAsyncComponent, defineComponent } from "vue";

// Importa tus componentes (pueden ser asíncronos para lazy loading)
// ----[Create]----
const CreateComponent = defineAsyncComponent(() => import("@/components/forms/Post/formCreateGeneral.vue"));
//----[Edit]----
const EditComponent = defineAsyncComponent(() => import("@/components/EditComponent.vue"));
//----[Delte]----
const DeleteComponent = defineAsyncComponent(() => import("@/components/DeleteComponent.vue"));

// Tipos de acción soportados
type ActionType = "periodo" | "grupo" | "semillero";

// Mapa de componentes
const componentMap: Record<ActionType, ReturnType<typeof defineAsyncComponent>> = {
  periodo: CreateComponent,
  grupo : EditComponent,
  semillero: DeleteComponent
};

export class ComponentFactory {
  static getComponent(type: ActionType) {
    return componentMap[type] || defineComponent({ template: "<div>Componente no encontrado</div>" });
  }
}
