<template>
  <VForm validate-on="submit" @submit.prevent="updateGroup">
    <VTextField label="Nombre" name="name" id="name" v-model="item.new_name"/>

    <LoadingBtn icon="ri-save-2-line" text="Guardar" :loading="loading"></LoadingBtn>
  </VForm>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import API from "@/utils/api";
import LoadingBtn from '../loadingBtn.vue';


interface Item {
  investigation_group_id: string,
  new_name: string
}


export default defineComponent({
  name: 'formUpdateGroup',
  props:{
    label:{
      type: String,
    }
  },
  data() {
    return {
      item: {} as Item,
      loading: false,
      selectedFunctionary: null
    }
  },
  created() {
      this.item.investigation_group_id = this.$route.params.idGrupo.toString();
  },

  methods: {
    updateGroup() {
      this.loading = true;
      API.patch(API.PATCH_INVESTIGATION_GROUP_NAME, this.item)
      .then((data) => {
        if (data.error) {
          console.error("Error al realizar la solicitud", data.error);
          this.loading = false;
        } else {
          console.log(data);
          this.$router.push({name: 'grupos-investigacion'});
        }
      })
      .catch((error) => {
        console.error('Error al realizar la solicitud:', error);
        this.loading = false
      });
    }
  },
});
</script>
