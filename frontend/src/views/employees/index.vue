<script lang='ts'>
import tableTeachers from '../../components/tableTeachers.vue';

export default {
  name: 'teachers',
  components: {
    tableTeachers,
  },
  data() {
    return {
      items: [],
    };
  },
  created() {
    this.getTeachers();
  },
  methods: {
    async getTeachers() {
      try {
        const response = await fetch('/api/getTeacherProfiles');
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
    <h1>Tabla Profesores</h1>
    <div >
    <tableTeachers :items="items"></tableTeachers>
  </div>
  </div>
</template>