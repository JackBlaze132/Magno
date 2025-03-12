<script setup lang="ts">
import { defineComponent, ref } from 'vue';
import google from '@images/logos/google.png'
import magnoLight from '@images/logos/magno-light.svg?raw'

const form = ref({
  email: '',
  password: '',
  remember: false


})
const isPasswordVisible = ref(false)

/*interface Item {

  userCode: string,
  email: string,
  isExternalUser: boolean
}*/
</script>

<script lang="ts">
export default defineComponent({
  name: 'formLogin',
  /*props: {
    item: {
      type: Object as PropType<Item>,
      required: true,
    },
  },*/
});
</script>


<template>
  <div class="bg">
    <VCard
      class="auth-card pa-4 pt-7 me-md-8"
      max-width="448"
      rounded="lg"
    >
      <VCardTitle class="d-flex justify-center">
        Ingresar
      </VCardTitle>
      <VCardText class="d-flex justify-center">
        <VBtn
          type="submit"
          rounded="xl"
          class="text-capitalize"
          variant="outlined"
          color="grey-darken-1"
        >
          <VImg
            :width="24"
            :src=google
            class="custom-icon px-1"
          />
          google
        </VBtn>
      </VCardText>
      <VCol cols="12" class="d-flex align-center">
        <VDivider />
        <span
          class="v-col-4"
        >
          Aliado Externo
        </span>
        <VDivider />
      </VCol>
      <VCardText>
        <VForm>
          <VRow>
            <VCol cols="12">
              <VTextField
                v-model="form.email"
                label="correo"
                placeholder="ejemplo@mail.com"
                type="email"
                variant="outlined"
              />
            </VCol>

            <VCol cols="12">
              <VTextField
                v-model="form.password"
                label="Contraseña"
                placeholder="············"
                variant="outlined"
                :type="isPasswordVisible ? 'text' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'ri-eye-off-line' : 'ri-eye-line'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible"
              />
              <div class="d-flex align-center justify-space-between flex-wrap mt-1 mb-4">
                <VCheckbox
                  v-model="form.remember"
                  label="Recordar"
                />
                <a
                  class="ms-2 mb-1"
                  href="javascript:void(0)"
                >
                  ¿Olvidaste tu contraseña?
                </a>
              </div>
              <span class="d-flex justify-center">
                <VBtn rounded="xl"
                  class="px-12 text-capitalize"
                  type="submit"
                  color="black"
                  to="/inicio"
                >
                  Ingresar
                </VBtn>
              </span>
            </VCol>
            <VCol
              cols="12"
              class="text-center text-base"
            >
              <span>
                Solicitar acceso
              </span>
              <RouterLink
                class="text-primary"
                to="/register"
              >
                aquí
              </RouterLink>
            </VCol>

            <VCol cols="12" class="d-flex align-center justify-center">
              <span>
                <RouterLink to="/">
                  <VIcon size="24" icon="ri-information-fill" />
                  Aviso de cookies
                </RouterLink>
              </span>
            </VCol>
          </VRow>
        </VForm>
      </VCardText>
    </VCard>

    <VImg
      :width="300"
      v-html=magnoLight
      class="position-absolute logo-login"
    />
  </div>
</template>
