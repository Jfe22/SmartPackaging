<template>
  <div v-if="error">
    Error: {{ error.message }} </div>
  <div v-else>
    <h2>Products</h2>
    <div>{{ products }}</div>
    <button @click.prevent="refresh">Refresh Data</button>
  </div>
</template>

<script setup>
  const config = useRuntimeConfig()
  const api = config.public.API_URL
  const { data: products, error, refresh } = await useFetch(`${api}/products`) 
</script>