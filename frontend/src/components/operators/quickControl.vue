<template>
   <!--
    Refresh button (toRefresh):
    Emits a refresh event to the parent component.
  -->
  <VBtn v-if="toRefresh"  class="ms-2 pa-1 action view" flat color="transparent"  @click="$emit('refresh')">
    <VIcon icon="ri-refresh-line" />
    <VTooltip activator="parent" location="top">
      Refrescar
    </VTooltip>
  </VBtn>
  <VBtn v-if="toUpload && authStore.can('upload', type)" prepend-icon="ri-upload-cloud-fill" class="mx-2" color="black" @click="overlayUpload = !overlayUpload; selectedAction = 'upload';">
    Subir
    <VOverlay v-model="overlayUpload" class="d-flex align-center justify-center" opacity="0.7">
      <v-progress-circular
        v-if="!componentLoaded"
        indeterminate
        color="primary"
        size="64"
      />
      <component :is="ComponentToRender.component" v-bind="ComponentToRender.props" @itemUploaded="handleItemUploaded" @loaded="componentLoaded = true"/>
    </VOverlay>
  </VBtn>

  <!--
    Create button (toCreate):
    Opens an overlay containing the appropriate create form component.
  -->
  <VBtn v-if="toCreate && authStore.can('create', type)" class="mx-2" prepend-icon="ri-add-fill" @click="overlayCreate = !overlayCreate ; selectedAction = 'create'">
    Agregar
    <VOverlay v-model="overlayCreate"  class="d-flex align-center justify-center" opacity="0.7">
      <v-progress-circular
        v-if="!componentLoaded"
        indeterminate
        color="primary"
        size="64"
      />
      <component :is="ComponentToRender.component" v-bind="ComponentToRender.props" @itemCreated="handleItemCreated" @loaded="componentLoaded = true"/>
    </VOverlay>
  </VBtn>

  <!--
    Edit button (toEdit):
    Opens an overlay containing the appropriate edit form component.
  -->
  <VBtn v-if="toEdit && authStore.can('update', type)" prepend-icon="ri-pencil-fill" class="mx-2" @click="overlayEdit = !overlayEdit; selectedAction = 'update';">
    Editar
    <VOverlay v-model="overlayEdit" class="d-flex align-center justify-center" opacity="0.7">
      <v-progress-circular
        v-if="!componentLoaded"
        indeterminate
        color="primary"
        size="64"
      />
      <component :is="ComponentToRender.component" v-bind="ComponentToRender.props" @itemEdited="handleItemEdited" @loaded="componentLoaded = true"/>
    </VOverlay>
  </VBtn>




</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { FormFactory } from '@/utils/abstract-forms-factory/FormFactory';
import type { ActionType, EntityType } from '@/utils/abstract-forms-factory/form-types/formsTypes';
import { useAuthStore } from '@/stores/authStore';

// The 'quickControl' component handles create and edit actions for entities.
export default defineComponent({
  name: 'quickControl',
  emits: ['itemCreated', 'itemEdited', "itemUploaded", "refresh"],
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  props: {
    /**
     * The type of the item to handle (e.g. 'periodo', 'grupo', 'semillero').
     */
    type: {
      type: String as () => EntityType,
      required: true
    },

    /**
     * The index of the item to handle.
     */
    index: {
      type: Number,
      required: false,
    },

    /**
     * The name of the item to handle.
     */
    name: {
      type: String,
      required: false,
    },

    /**
     * Flag to enable the create action.
     */
    toCreate: {
      type: Boolean,
      required: false,
    },

    /**
     * Flag to enable the edit action.
     */
    toEdit: {
      type: Boolean,
      required: false,
    },

    toUpload: {
      type: Boolean,
      required: false,
    },

    toRefresh: {
      type: Boolean,
      required: false,
    },

    /**
     * Initial data for the create or edit form (e.g. { name, start_date, ... }).
     */
    initialData: {
      type: Object,
      required: false,
      default: () => ({}),
    }
  },

  computed: {
    ComponentToRender() {
      const extraProps = {
        index: this.index,
        name: this.name,
        initialData: this.initialData,
      }
      return FormFactory.getComponentConfig(this.selectedAction, this.type, extraProps);
    }
  },

  watch: {
    // Watch for changes in the overlayCreate to load the component
    overlayCreate(newVal) {
      if (newVal) this.componentLoaded = false;
    },
    // Watch for changes in the overlayEdit to load the component
    overlayEdit(newVal) {
      if (newVal) this.componentLoaded = false;
    }
  },

  data() {
    return {
      // Controls the visibility of the create overlay
      overlayCreate: false,
      // Controls the visibility of the edit overlay
      overlayEdit: false,
      // Controls the visibility of the upload overlay
      overlayUpload: false,

      selectedAction: '' as ActionType,
      componentLoaded: false,
    };
  },

  methods: {
    /**
     * Handles the 'itemCreated' event from the create form and closes the overlay.
     */
    handleItemCreated() {
      this.$emit('itemCreated');
      this.overlayCreate = false;
    },

    /**
     * Handles the 'itemEdited' event from the edit form and closes the overlay.
     */
    handleItemEdited(index: any, name: any) {
      this.$emit('itemEdited', index, name);
      this.overlayEdit = false;
    },

    handleItemUploaded() {
      this.$emit('itemUploaded');
      this.overlayUpload = false;
    }
  }
});
</script>
