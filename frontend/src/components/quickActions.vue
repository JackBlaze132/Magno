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

  <!--
    Edit button (toEdit):
    Opens an overlay containing FormUpdateGeneral to update the item.
  -->
  <VBtn v-if="toEdit" icon class="action edit" flat color="transparent" desity="compact" @click="overlay = !overlay">
    <VIcon icon="ri-edit-box-line" />
    <VOverlay v-model="overlay" scrim="black" class="d-flex align-center justify-center" opacity="0.7">
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
    },
    // ---[Edit]---
    toEdit: {
      type: String,
    },
    typeEdit: {
      type: String,
    },
    itemEdit: {
      type: String,
    },
    // ---[Delete]---
    toDelete: {
      type: Number,
    },
    typeDelete: {
      type: String,
    },
    itemDelete: {
      type: String,
      required: true,
    },

    /**
     * Array of field definitions for the edit form,
     * e.g. [{ key: 'name', label: 'Name', type: 'text' }]
     */
    fields: {
      type: Array as () => Array<{ key: string; label: string; type?: string }>,
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
      // Controls the visibility of the edit overlay
      overlay: false,
      // Controls the visibility of the delete overlay
      overlayDelete: false,
    };
  },
  methods: {
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
      console.log('initial data:' + this.initialData);
      this.$emit('itemEdited', index, name);
      this.overlay = false;
    }
  }
});
</script>

