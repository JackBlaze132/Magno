<template>
  <VCard class="pa-5 ma-5 overflow-auto" max-width="600" max-height="85vh">
    <VCardTitle>Agregar {{ label }}</VCardTitle>
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
          <VSelect v-else-if="field.type === 'select'"
            v-model="formValues[field.key]"
            :items="field.options"
            item-title="label"
            item-value="value"
            class="mb-5"
            :label="field.label"
          />
          <VSelect v-else-if="field.type === 'multiple-select'"
            multiple
            v-model="formValues[field.key]"
            :items="field.options"
            item-title="label"
            item-value="value"
            :label="field.label"
            class="mb-5"
          />
          <VTextarea no-resize v-else-if="field.type === 'textarea'"
            v-model="formValues[field.key]"
            :label="field.label"
            rows="5"
            class="mb-5"
          />
        </div>
        <VCardItem class="d-flex justify-end">
          <LoadingBtn icon="ri-save-2-line" text="Guardar" :loading="loading" color="primary"/>
        </VCardItem>
      </VForm>
    </VCardText>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";

export default defineComponent({
  name: 'formEditGeneral',
  emits: ['itemCreated'],
  props: {
    type: {
      type: String,
    },
    label: {
      type: String,
    },
    fields: {
      type: Object,
      default: () => ({}),
    },
    additionalData: {
      type: Object,
      default: () => ({}),
    }
  },
  data() {
    return {
      //inputValue: this.itemName, // valor inicial
      loading: false,
      formValues: {...this.fields, ...this.additionalData},
    };
  },
  mounted() {
    console.log("formvalues are: " + this.formValues)
  },
  methods: {
    async CreateItem() {
      this.loading = true;
      const headers = {
        'API-VERSION': '1',
      }
      try {
        let response;
        if (this.type === 'period') {
          // Ejemplo hipotético para editar un periodo
          response = await API.post(API.ACADEMIC_PERIODS, {
            ...this.formValues,
          }, headers);
        } else if (this.type === 'group') {
          response = await API.post(API.INVESTIGATION_GROUPS, {
            ...this.formValues,
          }, headers);
        } else if (this.type === 'seedbed') {
          response = await API.post(API.RESEARCH_SEEDBEDS, {
          ...this.formValues,
          }, headers);
        } else if (this.type === 'user_integra') {
          response = await API.post(API.USERS_INTEGRA, {
           ...this.formValues,
          }, headers);
        } else if (this.type === 'user_external') {
          response = await API.post(API.USERS, {
            ...this.formValues,
          }, headers);
        } else if (this.type === 'role') {
          response = await API.post(API.ROLES, {
          ...this.formValues,
          }, headers);
        } else if (this.type === 'functionary_profile') {
          response = await API.post(API.FUNCTIONARY_PROFILES, {
         ...this.formValues,
          }, headers);
        } else if (this.type === 'student_profile') {
          response = await API.post(API.STUDENT_PROFILES, {
         ...this.formValues,
          }, headers);
        } else if (this.type === 'group_profile') {
          response = await API.post(API.INVESTIGATION_GRUOPS_PROFILES, {
            ...this.formValues,
          }, headers);
        } else if (this.type === 'seedbed_profile') {
          response = await API.post(API.RESEARCH_SEEDBEDS_PROFILES, {
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
