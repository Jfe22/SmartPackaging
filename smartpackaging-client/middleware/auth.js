// middleware/auth.js
export default function ({app, redirect}) {
    // Use your auth store
    const authStore = app.$pinia.getStore(useAuthStore);

    console.log("authStore.token: " + authStore.token)

    // Check if the user is authenticated (i.e., if a token exists)
    if (!authStore.token) {
        console.log("authStore.token is null")
        // Redirect to the login page
        return redirect('/auth/login');
    } else {
        console.log("authStore.token is not null")
        // Redirect to the home page
        return redirect('/');
    }
}