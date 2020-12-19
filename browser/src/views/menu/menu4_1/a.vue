<template>
  <div class="login-container">
    <h1>{{pageName}}</h1>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'

  export default {
    name: 'Menu4_1_a',
    data() {
      return {
        pageName: '菜单4-1-a'
      }
    },
    mounted() {
      console.log(getToken());
    }

  }
</script>

