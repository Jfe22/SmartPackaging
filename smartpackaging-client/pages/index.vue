<template>
    <div class="container main-container">
        <!-- Header -->
        <header class="header">
            <h1 class="title text-warning">Intelligent Packaging Management</h1>
            <button @click.prevent="refresh" class="refresh-button">Refresh Data</button>
        </header>
        <hr>

        <!-- Resources -->
        <section class="resource-section">
            <div class="row">
                <h2># Manage Resources</h2>

                <div class="col links" v-for="link in links" :key="link.text">
                    <nuxt-link :to="link.to">
                        <div class="link-icon">{{ link.icon }}</div>
                        <div class="link-title">{{ link.text }}</div>
                    </nuxt-link>
                </div>
            </div>
        </section>
        <hr>

        <!-- Live Data -->
        <section class="data-section">
            <h2># Live Data</h2>
            <div v-if="error">
                <p class="error-message">Error: {{ error.message }}</p>
            </div>
            <div v-else>
                <div class="data-card" v-for="(data, name) in dataObjects" :key="name">
                    <h3 class="text-warning">{{ name }}</h3>

                    <table :id="`table${name}`" class="table table-dark table-striped">
                        <thead>
                        <tr>
                            <th scope="col" v-for="(value, key) in data[0]" :key="key">
                                {{ key }}
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item, index) in data" :key="index">
                            <td v-for="(value, key) in item" :key="key">
                                {{ value || '---' }}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </div>
</template>


<script setup>
import 'bootstrap/dist/css/bootstrap.min.css';
import {ref} from 'vue';

const config = useRuntimeConfig()
const api = config.public.API_URL

const products = await useFetch(`${api}/products`).data
const smartpackages = await useFetch(`${api}/smartpackages`).data
// const orders = await useFetch(`${api}/orders`).data

const links = ref([
    {to: '/consumers', text: 'Consumers', icon: '👥'},
    {to: '/operators', text: 'Operators', icon: '🔧'},
    {to: '/producers', text: 'Producers', icon: '🏭'},
    {to: '/products', text: 'Products', icon: '📦'},
    {to: '/smartpackages', text: 'Smart Packages', icon: '📦'},
    {to: '/orders', text: 'Orders', icon: '📦'},
    {to: '/transportpackages', text: 'Transport Packages', icon: '📦'},
]);
const dataObjects = ref({
    Products: products,
    Smartpackages: smartpackages,
    // orders: orders
});
</script>

<style scoped>
hr {
    width: 92%;
    margin: 20px auto;
    opacity: .1;
}

.main-container {
    color: #eaeaea;
}

header.header {
    display: flex;
    padding: 50px 3rem 20px 3rem;
    justify-content: space-between;
}

h1.title {
    margin: 0;
    font-size: 1.7rem;
}

h2 {
    margin-bottom: 20px;
    color: #afafaf;
    font-size: 1.5rem;
}

h3 {
    margin-bottom: 20px;
    font-size: 1.2rem;
}

section.resource-section {
    padding: 2rem;
    text-align: center;
}

section.resource-section h2,
section.data-section h2 {
    margin-left: 16px;
    text-align: left;
}

div.links {
    position: relative;
    width: 100%;
    margin: .7rem;
    padding: 0;
    border-radius: 10px;
    border: 2px solid transparent;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
    background-color: #263238;
    transition: .2s ease;
}

div.links a {
    padding: 1rem;
    text-decoration: none;
    color: #fff;
}

div.links:hover {
    border: 2px solid #37474f;
}

div.link-icon {
    pointer-events: none;
    font-size: 2rem;
}

div.link-title {
    margin-top: 10px;
    pointer-events: none;
    font-weight: bold;
}

section.data-section {
    padding: 2rem;
}

div.data-card {
    margin: 1rem;
    padding: 1rem;
    border-radius: 8px;
    border: 1px solid #37474f;
    background-color: #263238;
}

.error-message {
    color: #cf6679;
}

.refresh-button {
    cursor: pointer;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    border: 2px solid #37474f;
    background-color: transparent;
    color: white;
    transition: .2s ease;
}

.refresh-button:hover {
    background-color: #37474f;
}
</style>
