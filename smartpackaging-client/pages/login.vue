<template>
    <div class="main-container">
        <div class="login-container">
            <h1>Login</h1>
            <form @submit.prevent="login">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" v-model="username" required />
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" v-model="password" required />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');

const login = () => {
    // Handle login logic here
    console.log("Login with:", username.value, password.value);
    // Replace this with your actual login request to the server
};
</script>

<style scoped>
.main-container {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #1d1d1d;
    color: #fff;
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.login-container {
    background-color: #1d1d1d;
    color: #fff;
    width: 40%;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
}

h1 {
    text-align: center;
    color: #fff;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 10px;
    color: #bbb;
}

.form-group input {
    width: 100%;
    padding: 10px 0px;
    border: 1px solid #37474f;
    border-radius: 4px;
    background-color: #1d1d1d;
    color: #fff;
}

button {
    width: 100%;
    margin-top: 10px;
    padding: 10px;
    border: none;
    border-radius: 4px;
    background-color: #3485c0;
    color: white;
    cursor: pointer;
    transition: background-color .2s ease;
}

button:hover {
    background-color: #307ba0;
}

/* Responsive styles */
@media (max-width: 768px) {
    .login-container {
        width: 90%;
    }
}

@media (max-width: 480px) {
    h1 {
        font-size: 1.5rem;
    }
}
</style>
