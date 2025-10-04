<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import QuickControl from "@/components/quickControl.vue";





export default defineComponent({
  components: {QuickControl},

  data() {
    return {
      items: [] as any[],
      search: '',
      links: '',
      loaded: false,
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'user.full_name'},
        {title: 'País', key: 'country'},
        {title: 'Código', key: 'user.user_code'},
        {title: 'Identificación', key: 'user.identification_number'},
        {title: 'Correo', key: 'user.email'},
        {title: 'Sexo', key: 'user.sex'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getSeedBeds();
  },
  methods: {
    handleItemRefresh(){
      this.getSeedBeds();
    },
    async getSeedBeds() {
      const  headers = {
          'API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.EXTERNAL_USER_PROFILES_BY_RESEARCH_SEEDBED + this.$route.params.idSemillero, headers);
        this.$emit('loaded')
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
  },
})
</script>

<template>
  <VCard flat>
    <h2>Aliados externos</h2>
    <VCardTitle class="d-flex align-center justify-end">
      <VTextField
        v-model="search"
        density="compact"
        label="Search"
        prepend-inner-icon="ri-search-line"
        variant="outlined"
        hide-details
        single-line
      ></VTextField>
      <QuickControl
        toCreate
        type="external_seedbed_profile"
        @itemCreated="handleItemRefresh"
        :index="parseInt($route.params.idSemillero as string)"
      />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >


    </VDataTable>
  </VCard>
</template>
