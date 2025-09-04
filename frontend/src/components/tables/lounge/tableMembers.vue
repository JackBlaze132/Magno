<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import Formater from "@/utils/formatter";


export default defineComponent({

  data() {
    return {
      items: [] as any[],
      search: '',
      links: '',
      loaded: false,
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'student_profile.user.full_name'},
        {title: 'Código', key: 'student_profile.user.user_code'},
        {title: 'Identificación', key: 'student_profile.user.identification_number'},
        {title: 'Semestre', key: 'student_profile.semester'},
        {title: 'Correo', key: 'student_profile.user.email'},
        {title: 'Sexo', key: 'student_profile.user.sex'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  mounted() {
    this.getSeedBeds();
  },
  methods: {
    async getSeedBeds() {
      const  headers = {
          'API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.RESEARCH_SEEDBED_STUDENT_PROFILES + this.$route.params.idSemillero, headers);
        console.log("members:" + this.items);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    externalFormatter(state:boolean){
      return Formater.externalFormatter(state)
    },
  },
})
</script>

<template>
  <VCard flat>
    <h2>Estudiantes</h2>
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
      <VBtn to="subir-estudiantes" class="mx-2" prepend-icon="ri-upload-cloud-2-fill" color="black"> Subir</VBtn>
      <VBtn to="agregar-estudiante" class="mx-2" prepend-icon="ri-add-fill"> Agregar</VBtn>

    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
    <template v-slot:item.userStudent.isExternalUser="{item}">
      {{ externalFormatter(item.userStudent.isExternalUser)}}

    </template>

      <!--<template v-slot:item.link="{item}">
        <RouterLink :to="item.id.toString()">
          <VIcon icon="ri-search-eye-fill"/>
        </RouterLink>
      </template>-->
    </VDataTable>
  </VCard>
</template>
