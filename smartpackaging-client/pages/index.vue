<template>
    <div class="container">
        <h1 class="title">Welcome to Intelligent Packaging Management</h1>
        <div class="resource-container">
            <div class="card" v-for="link in links" :key="link.text">
                <nuxt-link :to="link.to">
                    <div class="card-content">
                        <h2 class="card-title">{{ link.text }}</h2>
                        <p class="card-description">{{ link.description }}</p>
                    </div>
                </nuxt-link>
            </div>
        </div>
    </div>
    <div class="data-section">
        <h2>Live Data</h2>
        <div v-if="error">
            Error: {{ error.message }}
        </div>
        <div v-else>
            <div class="data-card" v-for="(data, name) in dataObjects" :key="name">
                <h3>{{ name }}</h3>
                <div>{{ data }}</div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const links = ref([
    { to: '/consumers', text: 'Consumers', description: 'Manage consumer profiles and data.' },
    { to: '/operators', text: 'Operators', description: 'Oversee logistics and operations.' },
    { to: '/producers', text: 'Producers', description: 'Control product quality and standards.' },
    // Add other links as necessary
]);

const dataObjects = ref({
    products: 'Product data will be shown here.',
    smartpackages: 'Smart Package data will be shown here.',
    orders: 'Order data will be shown here.',
    transportpackages: 'Transport Package data will be shown here.',
    // Populate with actual data from your API
});

// Your existing useFetch calls and error handling
</script>


<style>
.container {
    text-align: center;
    margin: 2rem auto;
}

.title {
    color: #333;
}

.resource-container {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 1rem;
}

.card {
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 200px;
    margin: 1rem;
    transition: transform 0.2s;
}

.card:hover {
    transform: translateY(-5px);
}

.card-content {
    padding: 1rem;
}

.card-title {
    margin: 0;
    color: #007bff;
}

.card-description {
    color: #666;
}

.data-section {
    margin: 2rem auto;
}

.data-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    margin-bottom: 1rem;
}
</style>
