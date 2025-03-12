<!--<script lang='ts'>
import tableUsers from '../../components/tableUsers.vue';


export default {
  name: 'users',
  components: {
    tableUsers,
  },
  data() {
    return {
      items: [],
    };
  },
  created() {
    this.getUsers();
  },
  methods: {
    async getUsers() {
      try {
        const response = await fetch('/api/getUsers');
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


</script>-->

<template>

    <RouterView/>

</template>
