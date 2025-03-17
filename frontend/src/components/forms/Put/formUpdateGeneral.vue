<template>
  <VCard class="pa-5 ma-5" max-width="600">
    <VCardTitle>Editar {{ type }}</VCardTitle>
    <VDivider/>
    <VCardText>
      <VForm @submit.prevent="editItem">
        <!-- Campos para editar, por ejemplo: nombre -->
        <div v-for="(field, index) in fields" :key="index">
          <VTextField v-if="field.type === 'text'"
            v-model="formValues[field.key]"
            :label="field.label"
            rows="5"
          />
          <VDateInput v-else-if="field.type === 'date'"
            v-model="formValues[field.key]"
            :label="field.label"
            type="text"
            prepend-icon=""
            prepend-inner-icon="ri-calendar-2-line"
          />
        </div>
        <VcardItem class="d-flex justify-end">
          <LoadingBtn @click="editItem" icon="ri-save-2-line" text="Guardar" :loading="loading" color="primary"/>
        </VcardItem>
      </VForm>
    </VCardText>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import LoadingBtn from '../loadingBtn.vue';

export default defineComponent({
  name: 'formEditGeneral',
  props: {
    type: {
      type: String,
    },
    name: {
      type: String,
    },
    index: {
      type: Number,
    },
    fields: {
      type: Array as () => Array<{ key: string; label: string; type?: string }>,
      default: () => [],
    },
    initialData: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {

      //inputValue: this.itemName, // valor inicial
      loading: false,
      formValues: {...this.initialData},

    };
  },
  methods: {
    async editItem() {
      this.loading = true;
      const headers = {
        'API-VERSION': '1',
      }
      try {
        let response;
        if (this.type === 'periodo') {
          // Ejemplo hipotético para editar un periodo
          response = await API.put(API.PUT_ACADEMIC_PERIOD + this.index, {
            ...this.formValues,
          }, headers);
        }
        if (!response.error) {
          this.$emit('itemEdited', this.index, this.formValues.name);
        }
      } catch (error) {
        console.error("Error al editar", error);
      } finally {
        this.loading = false;
      }
    },
  },
});
</script>
