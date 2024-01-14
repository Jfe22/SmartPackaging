<template>
  <div v-if="error">
    Error: {{ error.message }} </div>
  <div v-else>
    <h2>Products</h2>
    <div>{{ products }}</div>
    <h2>SmartPackages</h2>
    <div>{{ smartpackages }}</div>
    <h2>Orders</h2>
    <div>{{ orders }}</div>
    <h2>TransportPackages</h2>
    <div>{{ transportpackages }}</div>
    <button @click.prevent="refresh">Refresh Data</button>
  </div>
</template>

<script setup>
  const config = useRuntimeConfig()
  const api = config.public.API_URL
  const { data: products, error, refresh } = await useFetch(`${api}/products`) 
  const { data: smartpackages, errorSP, refreshSP } = await useFetch(`${api}/smartpackages`) 
  const { data: orders, errorO, refreshO } = await useFetch(`${api}/orders`) 
  const { data: transportpackages, errorTP, refreshTP } = await useFetch(`${api}/transportpackages`) 
</script>