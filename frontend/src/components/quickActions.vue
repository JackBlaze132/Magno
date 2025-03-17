<template>
  <!--
    View button (toView):
    Displays an icon that redirects to the 'toView' route when clicked.
  -->
  <VBtn v-if="toView" icon class="action view" flat color="transparent" desity="compact" :to="toView">
    <VIcon icon="ri-eye-line" />
    <VTooltip activator="parent" location="top">
      View
    </VTooltip>
  </VBtn>

  <VBtn v-if="toCreate" class="mx-2" prepend-icon="ri-add-fill" @click="overlayCreate = !overlayCreate">
    Agregar
    <VOverlay v-model="overlayCreate" scrim="black" class="d-flex align-center justify-center" opacity="0.7">
      <FormCreateGeneral
        :type="typeCreate"
        :name="itemCreate"
        :fields="fields"
        @itemCreated="handleItemCreated"
      />
    </VOverlay>
  </VBtn>
  <!--
    Edit button (toEdit):
    Opens an overlay containing FormUpdateGeneral to update the item.
  -->
  <VBtn v-if="toEdit" icon class="action edit" flat color="transparent" desity="compact" @click="overlayEdit = !overlayEdit">
    <VIcon icon="ri-edit-box-line" />
    <VOverlay v-model="overlayEdit" scrim="black" class="d-flex align-center justify-center" opacity="0.7">
      <FormUpdateGeneral
        :index="toEdit"
        :type="typeEdit"
        :name="itemEdit"
        :fields="fields"
        :initialData="initialData"
        @itemEdited="handleItemEdited"
      />
    </VOverlay>
    <VTooltip activator="parent" location="top">
      Edit
    </VTooltip>
  </VBtn>

  <!--
    Delete button (toDelete):
    Opens an overlay with FormDeleteGeneral to perform a delete action.
  -->
  <VBtn v-if="toDelete" icon class="action delete" flat color="transparent" desity="compact" @click="overlayDelete = !overlayDelete">
    <VIcon icon="ri-delete-bin-5-line" />
    <VOverlay v-model="overlayDelete" scrim="black" class="d-flex align-center justify-center" opacity="0.7">
      <FormDeleteGeneral
        :index="toDelete"
        :type="typeDelete"
        :name="itemDelete"
        @itemDeleted="handleItemDeleted"
      />
    </VOverlay>
    <VTooltip activator="parent" location="top">
      Delete
    </VTooltip>
  </VBtn>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

// The 'quickActions' component centralizes quick actions (view, edit, delete).
export default defineComponent({
  name: 'quickActions',
  props: {
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
    typeCreate: {
      type: String,
      required: false,
    },
    itemCreate: {
      type: String,
      required: false,
    },
    // ---[Edit]---
    toEdit: {
      type: String,
      required: false,
    },
    typeEdit: {
      type: String,
      required: false,
    },
    itemEdit: {
      type: String,
      required: false,
    },
    // ---[Delete]---
    toDelete: {
      type: Number,
      required: false,
    },
    typeDelete: {
      type: String,
      required: false,
    },
    itemDelete: {
      type: String,
      required: false,
    },

    /**
     * Array of field definitions for the edit form,
     * e.g. [{ key: 'name', label: 'Name', type: 'text' }]
     */
    fields: {
      type: Array as () => Array<{ key: string; label: string; type?: string; options?: Array<{ label: string; value: string }> }>,
      default: () => [],
    },

    /**
     * Initial data for the edit form (e.g. { name, start_date, ... }).
     */
    initialData: {
      type: Object,
      default: () => ({}),
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

    };
  },
  methods: {

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
    }

  }
});
</script>

