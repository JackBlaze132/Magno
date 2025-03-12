<script lang="ts">
  import { defineComponent } from 'vue'
  import API from "@/utils/api";

  interface Item {
    identification: string,
    email: string,
    isExternalUser: boolean
  }

  export default defineComponent({
    name: 'formAddUser',
    data() {
      return {
        item: {} as Item,
        loading: false
      }
  },
  methods: {
    addUser() {
      API.post(API.POST_USER, this.item)
        .then((data) => {
          this.$router.push('/usuarios');
        })
        .catch((error) => {
          console.error('Error al realizar la solicitud:', error);
        });
    }
  }
});
</script>

<template>
  <VForm validate-on="submit" @submit.prevent="addUser">
    <VTextField label="Cedula" type="text" name="identification" id="identification" v-model="item.identification"/>
    <VTextField label="Correo" type="text" name="mail" id="mail"  v-model="item.email"/>
    <VRadioGroup v-model="item.isExternalUser" inline>
      <VRadio label="Externo" :value="true"/>
      <VRadio label="Interno" :value="false"/>
    </VRadioGroup>
    <VBtn
    :loading="loading"
      text="Agregar"
      prepend-icon="ri-add-fill"
      type="submit"

    />
  </VForm>
</template>
