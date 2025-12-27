<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import type {ActionType} from "@/utils/abstract-forms-factory/form-types/formsTypes";
import {FormFactory} from "@/utils/abstract-forms-factory/FormFactory";
import QuickControl from "@/components/operators/quickControl.vue";

/*interface Item {
  coordinator:{
    id: number,
    user:{
      user_code: string,
      full_name: string,
      identification_number: string,
      email: string,
    }
    user_code: string,
    full_name: string,
    identification_number: string,
    email: string,
  }
}¨/*/

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
        {title: 'ID', key: 'coordinator.id'},
        {title: 'Nombre', key: 'coordinator.user.full_name'},
        {title: 'Código', key: 'coordinator.user.user_code'},
        {title: 'Identificación', key: 'coordinator.user.identification_number'},
        {title: 'Correo', key: 'coordinator.user.email'},
        {title: 'Sexo', key: 'coordinator.user.sex'},
        {title: 'Dependecia', key: 'coordinator.dependency.name'},
        { key: 'link', sortable: false},
      ],
    }
  },
  // ...
  created() {
    this.getSeedBeds();
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
      return FormFactory.getComponentConfig(this.selectedAction, 'seedbed_coordinator', extraProps);
    }
  },
  methods: {
    async getSeedBeds() {
      const headers = {
          'API-VERSION': '1',
      }
      try {
      this.items = await API.get(API.RESEARCH_SEEDBEDS_PROFILES + this.$route.params.idSemillero, headers);
        //
      console.log("items" + this.items);
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    handleItemRefresh(){
      this.getSeedBeds();
    },
    handleItemEdited(index: any, name: any) {
      this.getSeedBeds()
      this.overlayEdit = false;
    },
    setInitialData(item: any) {
      return {
        research_seedbed_id: item.research_seedbed.id,
        coordinator_id: item.coordinator.user.id,
        tutor_id: item.tutor?.id || null,
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
    <h2>Coordinador</h2>
    <VCardTitle class="d-flex align-center justify-end">
      <QuickControl
        toEdit
        type="seedbed_coordinator"
        @itemEdited="handleItemRefresh"
        :index="parseInt($route.params.idSemillero as string)"
        :initialData="items.length ? setInitialData(items[0]) : {}"
      />
    </VCardTitle>
    <VDataTableVirtual
      :items="items"
      :search="search"
      :headers="headers"
      @itemEdited="handleItemEdited"
    >

    </VDataTableVirtual>
  </VCard>
</template>
