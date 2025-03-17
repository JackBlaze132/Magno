<template>
  <VCard class="pa-5 ma-5" color="surface" max-width="600">
    <VCardTitle>
      Eliminar {{type}}
    </VCardTitle>
    <VDivider/>
    <VCardText>
      Esta a punto eliminar el {{ type }} denominado {{name}}, si esta seguro de que desea eliminar este elemento por favor ingrese <span class="px-1" style="background-color:rgb(var(--v-theme-grey-300))"> eliminar {{ name }}</span>en el campo de abajo.
    </VCardText>
    <VForm validate-on="submit" @submit.prevent="deleteItem">
      <VTextField  name="field" id="field" v-model="inputValue" :placeholder="`eliminar ${name}`"/>
      <VcardItem class="d-flex justify-end">
        <LoadingBtn @click="deleteItem" icon="ri-delete-bin-5-line" text="Eliminar" :loading="loading" color="error" ></LoadingBtn>
      </VcardItem>
    </VForm>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import LoadingBtn from '../loadingBtn.vue';
import { VCardText } from 'vuetify/components';

export default defineComponent({
  name: 'formUpdateGroup',
  props: {
    label: {
      type: String,
    },
    type: {
      type: String,
    },
    name:{
      type: String,
    },
    index: {
      type: Number,
    },
  },
  data() {
    return {
      inputValue: '',
      loading: false,
    };
  },
  methods: {
    async deleteItem() {
      const expectedValue = `eliminar ${this.name}`;
      if (this.inputValue !== expectedValue) {
        alert(`Por favor ingrese "${expectedValue}" para confirmar la eliminación.`);
        return;
      }

      this.loading = true;

      try {
        let response;
        if (this.type === 'semillero') {
          response = await API.delete(API.DELETE_RESEARCH_SEEDBED + this.index);
        } else if (this.type === 'grupo') {
          response = await API.delete(API.DELETE_INVESTIGATION_GROUP + this.index);
        } else if (this.type === 'periodo') {
          response = await API.delete(API.DELETE_ASSESMENT_PERIOD + this.index);
        }

        if (response.error) {
          console.error("Error al realizar la solicitud", response.error);
        } else {
          console.log(response);
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

