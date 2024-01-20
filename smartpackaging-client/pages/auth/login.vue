<template>
    <div class="container main-container">
        <div class="login-container">
            <h1>Login</h1>
            <form @submit.prevent="login">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" class="form-control" required v-model="loginFormData.username">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" class="form-control" required v-model="loginFormData.password">
                </div>
                <button type="submit" @click="login">Login</button>
                <button type="button" class="btn reset-button text-secondary">Reset token</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue';
import {useAuthStore} from "~/store/auth-store.js"

console.log(useAuthStore());

const config = useRuntimeConfig()
const api = config.public.API_URL

// const token = ref(null)
const messages = ref([])

const authStore = useAuthStore()
const {token, user} = storeToRefs(authStore)

const loginFormData = reactive({
    username: null,
    password: null
})

const apiFormData = reactive({
    path: "consumers"
})

async function login() {
    const {data, error} = await useFetch(`${api}/auth/login`, {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: loginFormData.value
    })
    if (error.value) {
        messages.value.push({tokenError: error.value.message})
        return
    }
    token.value = data.value

    const {data: userData, error: userError} = await useFetch(`${api}/auth/user`, {
        method: 'get',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + token.value
        }
    })
    if (userError.value) {
        messages.value.push({userDataError: userError.value.message})
        return
    }
    user.value = userData.value
}

function reset() {
    token.value = null
}

</script>

<style scoped>
div.main-container {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #1d1d1d;
    color: #fff;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

div.login-container {
    background-color: #1d1d1d;
    color: #fff;
    width: 60%;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
}

button.reset-button {
    background-color: transparent;
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

button.reset-button:hover {
    background-color: transparent;
    color: #fec007 !important;
    opacity: 0.5;
}

@media only screen and (max-width: 768px) {
    div.login-container {
        width: 100%;
    }
}
</style>
