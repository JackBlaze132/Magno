<template>
  <VCard class="pa-5 ma-5" color="surface" max-width="600">
    <VCardTitle>
      Eliminar {{label}}
    </VCardTitle>
    <VDivider/>
    <VCardText>
      Esta a punto eliminar el {{ label }} denominado {{name}}, si esta seguro de que desea eliminar este elemento por favor ingrese <span class="px-1" style="background-color:rgb(var(--v-theme-grey-300))"> {{ expectedValue }}</span>en el campo de abajo.
    </VCardText>
    <VForm validate-on="submit" @submit.prevent="deleteItem">
      <VTextField  name="field" id="field" v-model="inputValue" :placeholder="expectedValue"/>
      <VCardItem class="d-flex justify-end">
        <LoadingBtn icon="ri-delete-bin-5-line" text="Eliminar" :loading="loading" color="error" ></LoadingBtn>
      </VCardItem>
    </VForm>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import { useFeedbackToast } from '@/composables/useFeedbackToast';

export default defineComponent({
  name: 'formUpdateGroup',
  emits: ['itemDeleted','loaded'],
  setup() {
    const { showError, showSuccess } = useFeedbackToast()
    return { showError, showSuccess }
  },
  props: {
    type: {
      type: String,
    },
    name:{
      type: String,
    },
    index: {
      type: Number,
    },
    label:{
      type: String,
    },
    alt_name:{
      type: String,
    },
  },
  data() {
    return {
      inputValue: '',
      loading: false,
    };
  },
  computed: {
    expectedValue() {
      return this.alt_name ? `eliminar ${this.alt_name}` : `eliminar ${this.name}`;
    }
  },
  created() {
    this.$emit('loaded');
  },
  methods: {
    async deleteItem() {
      this.loading = true;

      const headers = {
        'API-VERSION': '1',
      }

      const endpoint =
      this.type === 'seedbed' ? API.RESEARCH_SEEDBEDS :
      this.type === 'group' ? API.INVESTIGATION_GROUPS :
      this.type === 'period' ? API.ACADEMIC_PERIODS :
      this.type === 'group_profile' ? API.INVESTIGATION_GRUOPS_PROFILES :
      this.type === 'seedbed_profile' ? API.RESEARCH_SEEDBEDS_PROFILES :
      this.type === 'seedbed_member' ? API.RESEARCH_SEEDBEDS_MEMBERS :
      this.type === 'external_seedbed_profile' ? API.EXTERNAL_USER_PROFILES : '';

      const expectedValue = this.expectedValue;
      if (this.inputValue !== expectedValue) {
        alert(`Por favor ingrese "${expectedValue}" para confirmar la eliminación.`);
        this.loading = false;
        return;
      }

      try {
        let response;
        response = await API.delete(endpoint + this.index, headers);

        if (!response.error) {
          this.showSuccess('Elemento eliminado exitosamente');
          this.$emit('itemDeleted', this.index); // Emitir evento al eliminar el objeto
        }

      } catch (error) {
        console.error("Error al realizar la solicitud", error);
        this.showError(error.response?.data);
      } finally {
        this.loading = false;
      }
    },
  }
});
</script>

