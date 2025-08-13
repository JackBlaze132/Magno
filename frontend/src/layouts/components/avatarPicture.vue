<template>
  <VAvatar
    :color="!item.picture ? 'secondary' : undefined"
    variant="flat"
    class="ma-1"
  >
    <VImg
      v-if="item.picture"
      :src="item.picture"
      :alt="item.name || 'Profile'"
      cover
    />
    <VIcon
      v-else
      icon="ri-user-line"
      :size="iconSize"
    />
  </VAvatar>
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
  name: 'AvatarPicture',
  data(){
    return {
      item: {} as Item
    }
  },

  created(){
    this.fetchGoogle()
    console.log(this.item.name)
  },
  methods: {
    async fetchGoogle(){
      const apiHeaders = {
        'API-VERSION': '1'
      }
      try {
        const response = await API.get(API.GOOGLE_DATA, apiHeaders);
        console.log("google repsonse: ", response)

        this.item = response[0];
        console.log("this is the item: ", this.item);
        console.log("this is the piture", this.item.picture);
        //this.Items = response.data;
        this.$emit('loaded')
      } catch (error) {
        console.error("Error fetching Google profile:", error);
        return null;
      }
    }
  }
})
</script>
