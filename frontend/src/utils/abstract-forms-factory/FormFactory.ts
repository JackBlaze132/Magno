// src/factories/ComponentFactory.ts
import type { ActionType, EntityType } from "./form-types/formsTypes";
import { AbstractFormFactory } from "./abstractFormsFactory";
import { CreateFormFactory } from "./actions-forms/createFormFactory";
import { UpdateFormFactory } from "./actions-forms/updateFormFactory";
import { DeleteFormFactory } from "./actions-forms/deleteFormFactory";
import { UploadFormFactory } from "./actions-forms/uploadFormFactory";
// Importar otras fábricas (Edit, View, delete) cuando estén listas

export class FormFactory {
  static getFactory(action: ActionType): AbstractFormFactory {
    const factoryMap: { [key in ActionType]: AbstractFormFactory } = {
      create: new CreateFormFactory(),
      upload: new UploadFormFactory(),
      update: new UpdateFormFactory(),
      view: new CreateFormFactory(),
      delete: new DeleteFormFactory()
    };

    return factoryMap[action] || new FormFactory(); // Fallback a "create"
  }

  static getComponentConfig(action: ActionType, type: EntityType , extraProps?: Record<string, any>) {
    return this.getFactory(action).getComponentConfig(type, extraProps);
  }
}
