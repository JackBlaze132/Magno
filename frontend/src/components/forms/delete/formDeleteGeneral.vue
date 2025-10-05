<template>
  <VCard class="pa-5 ma-5" color="surface" max-width="600">
    <VCardTitle>
      Eliminar {{label}}
    </VCardTitle>
    <VDivider/>
    <VCardText>
      Esta a punto eliminar el {{ label }} denominado {{name}}, si esta seguro de que desea eliminar este elemento por favor ingrese <span class="px-1" style="background-color:rgb(var(--v-theme-grey-300))"> eliminar {{ name }}</span>en el campo de abajo.
    </VCardText>
    <VForm validate-on="submit" @submit.prevent="deleteItem">
      <VTextField  name="field" id="field" v-model="inputValue" :placeholder="`eliminar ${name}`"/>
      <VCardItem class="d-flex justify-end">
        <LoadingBtn icon="ri-delete-bin-5-line" text="Eliminar" :loading="loading" color="error" ></LoadingBtn>
      </VCardItem>
    </VForm>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";

export default defineComponent({
  name: 'formUpdateGroup',
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
  },
  data() {
    return {
      inputValue: '',
      loading: false,
    };
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
      this.type === 'seedbed_profile' ? API.RESEARCH_SEEDBEDS_PROFILES : '';

      const expectedValue = `eliminar ${this.name}`;
      if (this.inputValue !== expectedValue) {
        alert(`Por favor ingrese "${expectedValue}" para confirmar la eliminación.`);
        this.loading = false;
        return;
      }

      try {
        let response;
        response = await API.delete(endpoint + this.index, headers);

        if (response.error) {
          console.error("Error al realizar la solicitud", response.error);
        } else {
          this.$emit('itemDeleted', this.index); // Emitir evento al eliminar el objeto
        }
      } catch (error) {
        console.error("Error al realizar la solicitud", error);
      } finally {
        this.loading = false;
      }
    },
  }
});
</script>

