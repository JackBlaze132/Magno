<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import type {ActionType} from "@/utils/abstract-forms-factory/form-types/formsTypes";
import {FormFactory} from "@/utils/abstract-forms-factory/FormFactory";
import QuickControl from "@/components/operators/quickControl.vue";

export default defineComponent({
  components: {QuickControl},

  data() {
    return {
      overlayEdit: false,
      selectedAction: '' as ActionType,
      componentLoaded: false,
      items: [] as any[],
      search: '',
      links: '',
      loaded: false,
      headers: [
        {title: 'ID', key: 'tutor.id'},
        {title: 'Nombre', key: 'tutor.user.full_name'},
        {title: 'Código', key: 'tutor.user.user_code'},
        {title: 'Identificación', key: 'tutor.user.identification_number'},
        {title: 'Correo', key: 'tutor.user.email'},
        {title: 'Sexo', key: 'tutor.user.sex'},
        {title: 'Dependecia', key: 'tutor.dependency.name'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getSeedBeds();
    console.log(this.$route.params.idSemillero);
  },
  watch  : {
    overlayEdit(newVal) {
      if (newVal) this.componentLoaded = false;
    },
  },
  computed: {
    ComponentToRender(){
      const extraProps = {
        index: this.$route.params.idSemillero,
        //name: this.items[0].id,
        initialData: this.setInitialData(this.items[0]),
      }
      return FormFactory.getComponentConfig(this.selectedAction, 'seedbed_tutor', extraProps);
    }
  },
  methods: {
    handleItemRefresh(){
      this.getSeedBeds();
    },
    async getSeedBeds() {
      const headers = {
        'API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.RESEARCH_SEEDBEDS_PROFILES + this.$route.params.idSemillero, headers);
        //
        console.log(this.items);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    handleItemEdited(index: any, name: any) {
      this.getSeedBeds()
      this.overlayEdit = false;
    },
    setInitialData(item: any) {
      return {
        research_seedbed_id: item.research_seedbed.id,
        coordinator_id: item.coordinator.user.id,
        tutor_id: item.tutor?.user?.id || null,
        academic_period_id: this.$route.params.idPeriodo,
        investigation_group_profile_id: this.$route.params.idGrupo,
        was_active: item.was_active,
      }
    }
  },
})
</script>

<template>
  <VCard flat>

    <VCardTitle class="d-flex align-center justify-space-between">
      <h2>Tutor</h2>
      <QuickControl
        toEdit
        type="seedbed_tutor"
        @itemEdited="handleItemRefresh"
        :index="parseInt($route.params.idSemillero as string)"
        :initialData="items.length ? setInitialData(items[0]) : {}"
      />
    </VCardTitle>
    <VDataTableVirtual
      class="mb-7"
      :items="items"
      :search="search"
      :headers="headers"
      @itemEdited="handleItemEdited"
    >
    </VDataTableVirtual>
  </VCard>
</template>
