<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import Formater from "@/utils/formatter";
import QuickControl from "@/components/operators/quickControl.vue";
import Formatter from "@/utils/formatter";


export default defineComponent({
  components: {QuickControl},

  data() {
    return {
      items: [] as any[],
      search: '',
      links: '',
      loaded: false,
      headers: [
        { key: 'is_leader', sortable: false},
        {title: 'ID', key: 'id'},
        {title: 'Nombre', key: 'student_profile.user.full_name'},
        {title: 'Código', key: 'student_profile.user.user_code'},
        {title: 'Identificación', key: 'student_profile.user.identification_number'},
        {title: 'Semestre', key: 'student_profile.semester'},
        {title: 'Correo', key: 'student_profile.user.email'},
        {title: 'Sexo', key: 'student_profile.user.sex'},
        {key: 'link', sortable: false},

      ],
    }
  },
  // ...
  mounted() {
    this.getSeedBeds();
  },
  methods: {
    Formatter() {
      return Formatter
    },
    handleItemRefresh(){
      this.getSeedBeds();
    },
    setInitialData(item: any) {
      return {
        student_profile_id: item.student_profile.id,
        research_seedbed_profile_id: this.$route.params.idSemillero,
        is_leader: item.is_leader,
        was_active: item.was_active,
      };
    },
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


      <QuickControl
        toUpload
        toCreate
        type="seedbed_member"
        @itemCreated="handleItemRefresh"
        @itemUploaded="handleItemRefresh"
        :index="parseInt($route.params.idSemillero as string)"
        :initialData="items.length ? setInitialData(items[0]) : {}"
      />

    </VCardTitle>
    <VDataTable
      :items="items"
      :search="search"
      :headers="headers"
    >
    <template v-slot:item.is_leader="{item}">
      {{ Formatter().leaderFormatter(item.is_leader) }}

    </template>

    <template v-slot:item.link="{item}">
        <QuickActions
          type="seedbed_member"
          toEdit
          toDelete
          :index="item.id"
          :name="item.student_profile.user.full_name"
          :initialData="setInitialData(item)"
          @itemDeleted="handleItemRefresh"
          @itemEdited="handleItemRefresh"
        />
      </template>

      <!--<template v-slot:item.link="{item}">
        <RouterLink :to="item.id.toString()">
          <VIcon icon="ri-search-eye-fill"/>
        </RouterLink>
      </template>-->
    </VDataTable>
  </VCard>
</template>
