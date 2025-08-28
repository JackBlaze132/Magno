<template>
  <div class="profile-picture-container">
    <v-menu min-width="200px">
      <template v-slot:activator="{ props }">
        <IconBtn v-bind="props">
          <AvatarPicture @loaded="onAvatarLoaded" />
        </IconBtn>
      </template>
      <VCard>
        <VCardText>
          <div class="mx-auto text-center">
            <AvatarPicture />
            <h3>{{ item.name }}</h3>
            <p class="text-caption mt-1">
              {{ item.email }}
            </p>
            <VDivider class="my-3"></VDivider>
            <VBtn
              variant="text"
              rounded
              prepend-icon="ri-user-smile-line"
            >
              Tu perfil
            </VBtn>
            <VDivider class="my-3"></VDivider>
            <LogoutBtn/>
          </div>
        </VCardText>
      </VCard>
    </v-menu>
  </div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import AvatarPicture from './avatarPicture.vue';
import API from '@/utils/api';
//import LogoutBtn from "./logoutBtn.vue";

interface Item {
  name: string;
  email: string;
  picture: string | null;
}

export default defineComponent({
  name: "ProfilePicture",
  components: {
    //LogoutBtn,
    AvatarPicture
  },
  data(){
    return {
      item: {} as Item
    }
  },
  async created() {
    await this.fetchGoogle();
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

