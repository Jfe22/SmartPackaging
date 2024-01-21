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

                <!-- Operators -->
                <div class="data-card scroll-table">
                    <div>
                        <div v-for="(operator, key) in operators" :key="key">
                            <h3 class="text-warning">{{ operator.username.toUpperCase() }}</h3>

                            <div v-if="dataOperators[operator.username]">
                                <table class="table table-dark table-striped" v-if="dataOperators[operator.username][0]">
                                    <thead>
                                    <tr>
                                        <th scope="col" v-for="(value, key) in dataOperators[operator.username][0]"
                                            :key="key">
                                            {{ key }}
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="transport in dataOperators[operator.username]" :key="transport.id">
                                        <td v-for="(value, key) in transport" :key="key">
                                            {{ value }}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div v-else>
                                    <p class="text-secondary">No transport available</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Consumers -->
                <div class="data-card scroll-table">
                    <div>
                        <div v-for="(consumer, key) in consumers" :key="key">
                            <h3 class="text-warning">{{ consumer.username.toUpperCase() }}</h3>

                            <div v-if="dataConsumers[consumer.username]">
                                <table class="table table-dark table-striped" v-if="dataConsumers[consumer.username][0]">
                                    <thead>
                                    <tr>
                                        <th scope="col" v-for="(value, key) in dataConsumers[consumer.username][0]"
                                            :key="key">
                                            {{ key }}
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="order in dataConsumers[consumer.username]" :key="order.id">
                                        <td v-for="(value, key) in order" :key="key">
                                            {{ value }}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div v-else>
                                    <p class="text-secondary">No orders available</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Producers -->
                <div class="data-card scroll-table">
                    <div>
                        <div v-for="(producer, key) in producers" :key="key">
                            <h3 class="text-warning">{{ producer.username.toUpperCase() }}</h3>

                            <div v-if="dataProducers[producer.username]">
                                <table class="table table-dark table-striped" v-if="dataProducers[producer.username][0]">
                                    <thead>
                                    <tr>
                                        <th scope="col" v-for="(value, key) in dataProducers[producer.username][0]"
                                            :key="key">
                                            {{ key }}
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="pack in dataProducers[producer.username]" :key="pack.id">
                                        <td v-for="(value, key) in pack" :key="key">
                                            {{ value }}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div v-else>
                                    <p class="text-secondary">No packages available</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </section>
    </div>
</template>


<script setup>
import 'bootstrap/dist/css/bootstrap.min.css';
import {ref, onMounted} from 'vue';

const config = useRuntimeConfig()
const api = config.public.API_URL

const producers = await useFetch(`${api}/producers`).data
const smartpackages1 = await useFetch(`${api}/producers/prod1/smartpackages`).data
const smartpackages2 = await useFetch(`${api}/producers/prod2/smartpackages`).data
const smartpackages3 = await useFetch(`${api}/producers/prod3/smartpackages`).data
const dataProducers = ref({
    prod1: smartpackages1,
    prod2: smartpackages2,
    prod3: smartpackages3,
});

const consumers = await useFetch(`${api}/consumers`).data
const order1 = await useFetch(`${api}/consumers/consumer1/orders`).data
const order2 = await useFetch(`${api}/consumers/consumer2/orders`).data
const order3 = await useFetch(`${api}/consumers/consumer3/orders`).data
const dataConsumers = ref({
    consumer1: order1,
    consumer2: order2,
    consumer3: order3,
});

const operators = await useFetch(`${api}/operators`).data
const transport1 = await useFetch(`${api}/operators/operator1/transportpackages`).data
const transport2 = await useFetch(`${api}/operators/operator2/transportpackages`).data
const transport3 = await useFetch(`${api}/operators/operator3/transportpackages`).data
const dataOperators = ref({
    operator1: transport1,
    operator2: transport2,
    operator3: transport3,
});


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
    //Products: products,
    //Smartpackages: smartpackages,
});
</script>

<style scoped>
hr {
    width: 92%;
    margin: 20px auto;
    opacity: .1;
}

.scroll-table {
    overflow-x: scroll;
}

.parameter {
    background: #2d2d2d;
    color: #ffffff;
    margin: 0.5rem 0;
    padding: 0.5rem;
    border-radius: 4px;
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
