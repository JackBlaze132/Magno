<template>
  <VCard flat class="pa-5 my-3">
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
      type="student_profile"
      @itemCreated="handleItemRefresh"
    />
    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
      <template v-slot:item.is_external_user="{item}">
        {{ externalFormatter(item.is_external_user)}}
      </template>
      <template v-slot:item.role.name="{item}">
        <VChipGroup column="false">
          <VChip size="small" variant="outlined">
            {{ item.role.name }}
          </VChip>
        </VChipGroup disabled>
      </template>
    </VDataTable>
  </VCard>
</template>

<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import Formatter from "@/utils/formatter";
import QuickControl from "@/components/quickControl.vue";

interface Item {
  id: number,
  user: {
    full_name: string,
    identification_number: string,
    user_code: string,
    email: string,
  },
  academic_period: {
    name: string,
  },
  role:{
    id: number,
    name: string,
  },
}

export default defineComponent({
  components: {QuickControl},
  props: {
    userId: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      items: [] as Item[],
      userRols: [] as Array<String>,
      search: '',
      headers: [
        {title: 'ID', key: 'id'},
        {title: 'Período académico', key: 'academic_period.name'},
        {title: 'Nombre', key: 'user.full_name'},
        {title: 'Rol', key: 'role.name'},
        {title: 'Número de identificación', key: 'user.identification_number'},
        {title: 'Código de usuario', key: 'user.user_code'},
        {title: 'Correo electrónico', key: 'user.email'},
      ]
    }
  },
  // ...
  created() {
    this.getProfiles();
    //this.externalFormatter();
  },
  methods: {
    async getProfiles() {
      const headers={
        'API-VERSION': '1',
      }
      try {
        // Use prop userId if provided, otherwise fall back to route param
        const id = this.userId || this.$route.params.idStudent;
        this.items = await API.get(API.STUDENT_PROFILES_ASSIGNED + id, headers);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    externalFormatter(state: boolean){
      return Formatter.externalFormatter(state)
    },
    handleItemRefresh(){
      this.getProfiles();
    }
  },
})


</script>



