<script lang='ts'>
import tableAcademic from '../../components/tableAcademic.vue';

export default {
  name: 'academic-programs',
  components: {
    tableAcademic,
  },
  data() {
    return {
      items: [],
    };
  },
  created() {
    this.getacademicPrograms();
  },
  methods: {
    async getacademicPrograms() {
      try {
        const response = await fetch('/api/getAcademicPrograms');
        console.log(response);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        this.items = data;
        console.log(data);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
  },
};


</script>

<template>
  <div>
    <h1>Tabla Programas</h1>
    <div >
    <tableAcademic :items="items"></tableAcademic>
  </div>
  </div>
</template>