<template>
    <div id="app">
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Demo</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <router-link class="p-2 text-dark" active-class="active" to="/" exact>Home</router-link>
                <router-link class="p-2 text-dark" active-class="active" to="/about">About</router-link>
                <router-link class="p-2 text-dark" active-class="active" to="/users">Users</router-link>
            </nav>
            <a class="btn btn-outline-primary" href="#">Sign up</a>
        </div>

        <router-view/>

        <footer class="container">
            <p>© {{ new Date().getFullYear() }} Popokis.com ·
                <router-link to="/about">About</router-link>
                ·
                <router-link to="/users">Users</router-link>
            </p>
        </footer>
    </div>
</template>

<style lang="scss">
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }
</style>
