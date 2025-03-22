<template>
  <VCard class="pa-5 ma-5" max-width="600">
    <VCardTitle>Agregar {{ type }}</VCardTitle>
    <VDivider/>
    <VCardText>
      <VForm @submit.prevent="CreateItem">
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

          <VRadioGroup v-else-if="field.type === 'radio-group'"
            v-model="formValues[field.key]"
            class="d-flex"
            inline
          >
            <VRadio
             v-for="(option,index) in field.options"
             :key="index"
              :label="option.label"
              :value="option.value"
            />
          </VRadioGroup>
        </div>
        <VcardItem class="d-flex justify-end">
          <LoadingBtn icon="ri-save-2-line" text="Guardar" :loading="loading" color="primary"/>
        </VcardItem>
      </VForm>
    </VCardText>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";

export default defineComponent({
  name: 'formEditGeneral',
  props: {
    type: {
      type: String,
    },
    name: {
      type: String,
    },
    fields: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {

      //inputValue: this.itemName, // valor inicial
      loading: false,
      formValues: {...this.fields},

    };
  },
  methods: {
    async CreateItem() {
      this.loading = true;
      const headers = {
        'API-VERSION': '1',
      }
      try {
        let response;
        if (this.type === 'periodo') {
          // Ejemplo hipotético para editar un periodo
          response = await API.post(API.POST_ACADEMIC_PERIOD, {
            ...this.formValues,
          }, headers);
        } else if (this.type === 'semillero') {
          response = await API.post(API.POST_RESEARCH_SEEDBED, {
            ...this.formValues,
          }, headers);
        } else if (this.type === 'grupo') {
          response = await API.post(API.POST_INVESTIGATION_GROUP, {
            ...this.formValues,
          }, headers);
        }
        if (!response.error) {
          this.$emit('itemCreated', this.formValues.name);
        }
      } catch (error) {
        console.error("Error al crear", error);
      } finally {
        this.loading = false;
      }

    },
  },
});
</script>
