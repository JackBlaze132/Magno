<template>
  <!--
    View button (toView):
    Displays an icon that redirects to the 'toView' route when clicked.
  -->
  <VBtn v-if="toView" icon class="action view" flat color="transparent" desity="compact" :to="toView">
    <VIcon icon="ri-eye-line" />
    <VTooltip activator="parent" location="top">
      Ingresar
    </VTooltip>
  </VBtn>

  <VBtn v-if="toCreate && authStore.can('create', type)" class="mx-2" prepend-icon="ri-add-fill" @click="overlayCreate = !overlayCreate ; selectedAction = 'create'">
    Agregar
    <VOverlay v-model="overlayCreate" class="d-flex align-center justify-center" opacity="0.7">
      <v-progress-circular
        v-if="!componentLoaded"
        indeterminate
        color="primary"
        size="64"
      />
      <component :is="ComponentToRender.component" v-bind="ComponentToRender.props" @itemCreated=handleItemCreated @loaded="componentLoaded = true"/>
    </VOverlay>
  </VBtn>
  <!--
    Edit button (toEdit):
    Opens an overlay containing FormUpdateGeneral to update the item.
  -->
  <VBtn v-if="toEdit && authStore.can('update', type)" icon class="action edit" flat color="transparent" desity="compact" @click="overlayEdit = !overlayEdit; selectedAction = 'update';">
    <VIcon icon="ri-edit-box-line" />
    <VOverlay v-model="overlayEdit" class="d-flex align-center justify-center" opacity="0.7">
      <v-progress-circular
        v-if="!componentLoaded"
        indeterminate
        color="primary"
        size="64"
      />
      <component :is="ComponentToRender.component" v-bind="ComponentToRender.props" @itemEdited="handleItemEdited" @loaded="componentLoaded = true"/>
    </VOverlay>
    <VTooltip activator="parent" location="top">
      Editar
    </VTooltip>
  </VBtn>

  <!--
    Delete button (toDelete):
    Opens an overlay with FormDeleteGeneral to perform a delete action.
  -->
  <VBtn v-if="toDelete && authStore.can('delete', type)" icon class="action delete" flat color="transparent" desity="compact" @click="overlayDelete = !overlayDelete ; selectedAction = 'delete'">
    <VIcon icon="ri-delete-bin-5-line" />
    <VOverlay v-model="overlayDelete" class="d-flex align-center justify-center" opacity="0.7">
      <v-progress-circular
        v-if="!componentLoaded"
        indeterminate
        color="primary"
        size="64"
      />
      <component :is="ComponentToRender.component" v-bind="ComponentToRender.props" @itemDeleted="handleItemDeleted" @loaded="componentLoaded = true"/>
    </VOverlay>
    <VTooltip activator="parent" location="top">
      Eliminar
    </VTooltip>
  </VBtn>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { FormFactory } from '@/utils/abstract-forms-factory/FormFactory';
import type { ActionType, EntityType } from '@/utils/abstract-forms-factory/form-types/formsTypes';
import { useAuthStore } from '@/stores/authStore';


// The 'quickActions' component centralizes quick actions (view, edit, delete).
export default defineComponent({
  name: 'quickActions',
  emits: ['itemCreated', 'itemDeleted', 'itemEdited'],
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  props: {

    /**
     * The type of the item to handle (e.g. 'periodo', 'grupo', 'semillero').
     */
    type:
    { type: String as () => EntityType,
      required: true
    },

    /**
     * The index of the item to handle.
     */
    index: {
      type: [String, Number],
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
     * Here are the props received by the component, organized by prefixes:
     * to: index of the item to handle
     * type: type of the item to handle
     * item: name of the item to handle
     */
    // ---[View]---
    toView: {
      type: String,
      required: false,
    },
    //--[Create]---
    toCreate: {
      type: Boolean,
      required: false,
    },
    toEdit: {
      type: Boolean,
      required: false,
    },
    // ---[Delete]---
    toDelete: {
      type: Boolean,
      required: false,
    },
    /**
     * Initial data for the edit form (e.g. { name, start_date, ... }).
     */
    initialData: {
      type: Object,
      required: false,
      default: () => ({}),
    }
  },
  computed: {
    ComponentToRender(){
      const extraProps = {
        index: this.index,
        name: this.name,
        initialData: this.initialData,
      }
      return FormFactory.getComponentConfig(this.selectedAction, this.type, extraProps);
    }
  },
  watch : {
    // Watch for changes in the selectedAction to load the component
    overlayCreate(newVal) {
      if (newVal) this.componentLoaded = false;
    },
    overlayEdit(newVal) {
      if (newVal) this.componentLoaded = false;
    },
    overlayDelete(newVal) {
      if (newVal) this.componentLoaded = false;
    }
  },
  data() {
    return {
      // ---[Overlays]---
      // Controls the visibility of the create overlay
      overlayCreate: false,
      // Controls the visibility of the edit overlay
      overlayEdit: false,
      // Controls the visibility of the delete overlay
      overlayDelete: false,
      //----
      selectedAction: '' as ActionType,

      componentLoaded: false,

    };
  },

  methods: {
    runfetch() {
      // 1) Select the update action
      // 3) Once overlay is set to true, call the child's runFetchMapData
      this.$nextTick(() => {
        const child = this.$refs.updateGlobalGroupRef as any
        if (child && typeof child.runFetchMapData === 'function') {
          child.runFetchMapData()
        }
      })
    },
    /**
     * Handles the 'itemCreated' event from FormUpdateGeneral and closes the overlay.
     */
    handleItemCreated() {
      this.$emit('itemCreated');
      this.overlayCreate = false;
    },
    /**
     * Handles the 'itemDeleted' event from FormDeleteGeneral and closes the overlay.
     */
    handleItemDeleted(index: any) {
      this.$emit('itemDeleted', index);
      this.overlayDelete = false;
    },

    /**
     * Handles when an item is edited in FormUpdateGeneral and closes the overlay.
     */
    handleItemEdited(index: any, name: any) {
      this.$emit('itemEdited', index, name);
      this.overlayEdit = false;
    },

  }
});
</script>

