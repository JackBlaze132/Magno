<template>
  <div class="profile-picture-container">
    <IconBtn
      :color="!Item.picture ? 'secondary' : undefined"
      variant="flat"
    >
      <VImg
        v-if="Item.picture"
        :src="Item.picture"
        :alt="Item.name || 'Profile'"
        cover
      />
      <VIcon
        v-else
        icon="ri-user-line"
        :size="iconSize"
      />
    </IconBtn>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import API from '@/utils/api';

interface Item {
  name: string;
  email: string;
  picture: string | null;
}
export default defineComponent({

  data(){
    return {
      Item: {} as Item
    }
  },

  created(){
    this.fetchGoogle()
  },

  name: "ProfilePicture",
  methods: {
    async fetchGoogle(){
      const apiHeaders = {
        'API-VERSION': '1'
      }
      try {
        const response = await API.get(API.GOOGLE_DATA, apiHeaders);
        this.Item = response.data;
        console.log(this.Item);
      } catch (error) {
        console.error("Error fetching Google profile:", error);
        return null;
      }
    }
  }
})
</script>

