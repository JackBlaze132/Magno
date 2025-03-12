<template>
  <div>
    <h1>
      {{ items }}
    </h1>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue"

//utils
import API from "@/utils/api";
import Formatter from "@/utils/formatter";
//import { VChip } from "vuetify/components";
//import QuickActions from "@/components/quickActions.vue";



export default defineComponent({

  data() {
    return {
      items: '',
    }
  },
  // ...
  created() {
    this.getPeriods();
  },
  methods: {
    async getPeriods() {
      const apiHeaders = {
          'X-API-VERSION': '1',
      }
      try {
        this.items = await API.get(API.HEADER_TEST, apiHeaders)
        this.$emit('loaded');
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    periodActivityFormatter(state:boolean){
      return Formatter.periodActivityFormatter(state);
    },

  },
})
</script>
