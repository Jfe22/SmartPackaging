<template>
    <a href="#" @click.prevent="logout">Logout</a>

    <div>
        <slot/>
    </div>
</template>

<script setup>
import {useAuthStore} from "~/store/auth-store.js";

const router = useRouter()
const authStore = useAuthStore()

function logout() {
    authStore.logout()
    router.push('/auth/login')
}
</script>