<template>
    <div class="main-container">
        <header class="header">
            <h1 class="title">Intelligent Packaging Management</h1>
            <button @click.prevent="refresh" class="refresh-button">Refresh Data</button>
        </header>
        <hr>
        <section class="resource-section">
            <h2># Manage Resources</h2>
            <div class="cards">
                <div class="card" v-for="link in links" :key="link.text">
                    <nuxt-link :to="link.to">
                        <div class="card-icon">{{ link.icon }}</div>
                        <div class="card-title">{{ link.text }}</div>
                    </nuxt-link>
                </div>
            </div>
        </section>
        <hr>

        <section class="data-section">
            <h2># Live Data</h2>
            <div v-if="error">
                <p class="error-message">Error: {{ error.message }}</p>
            </div>
            <div v-else>
                <div class="data-card" v-for="(data, name) in dataObjects" :key="name">
                    <h3>{{ name }}</h3>
                    <div>{{ data }}</div>
                </div>
            </div>
        </section>
    </div>
</template>


<script setup>
import { ref } from 'vue';

const links = ref([
    { to: '/consumers', text: 'Consumers', icon: '👥' },
    { to: '/operators', text: 'Operators', icon: '🔧' },
    { to: '/producers', text: 'Producers', icon: '🏭' },
    { to: '/products', text: 'Products', icon: '📦' },
    { to: '/smartpackages', text: 'Smart Packages', icon: '📦' },
    { to: '/orders', text: 'Orders', icon: '📦' },
    { to: '/transportpackages', text: 'Transport Packages', icon: '📦' },
]);

const dataObjects = ref({
    products: 'Product data will be shown here.',
    smartpackages: 'Smart Package data will be shown here.',
    orders: 'Order data will be shown here.'
});

// Your existing useFetch calls and error handling
</script>

<style>
.main-container {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #1d1d1d;
    color: #fff;
}

.header {
    display: flex;
    justify-content: space-between;
    padding: 50px 3rem 20px 3rem;
    color: #3485c0;
}

.title {
    margin: 0;
    font-size: 1.7rem;
}

.resource-section {
    padding: 2rem;
    text-align: center;
}

.resource-section h2,
.data-section h2 {
    margin-left: 16px;
    text-align: left;
}

.cards {
    display: flex;
}

.card {
    background-color: #263238;
    border-radius: 10px;
    padding: 1rem;
    margin: 1rem;
    width: 100%;
    border: 2px solid transparent;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
    transition: .2s ease;
}

.card a {
    text-decoration: none;
    color: #fff;
}

.card:hover {
    border: 2px solid #37474f;
}

.card-icon {
    font-size: 2rem;
}

.card-title {
    margin-top: 10px;
    font-weight: bold;
}

.card-description {
    color: #bbb;
}

.data-section {
    padding: 2rem;
}

.data-card {
    background-color: #263238;
    border: 1px solid #37474f;
    border-radius: 8px;
    padding: 1rem;
    margin: 1rem;
}

.error-message {
    color: #cf6679;
}

.refresh-button {
    background-color: transparent;
    border: 2px solid #37474f;
    color: white;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    cursor: pointer;
    transition: .2s ease;
}

.refresh-button:hover {
    background-color: #37474f;
}

/* Responsive styles */
@media (max-width: 768px) {
    .header {
        flex-direction: column;
    }

    .refresh-button {
        margin-top: 1rem;
    }
}

@media (max-width: 480px) {
    .title {
        font-size: 1.5rem;
    }

    .cards {
        grid-template-columns: 1fr;
    }

    .card {
        width: auto;
    }

    .data-card {
        margin: 1rem 0;
    }
}
</style>
