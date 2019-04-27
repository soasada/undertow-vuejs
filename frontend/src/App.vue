<template>
    <div id="app">
        <nav class="level">
            <p class="level-item has-text-centered">
                <router-link class="p-2 text-dark" active-class="active" to="/" exact>Home</router-link>
            </p>
            <p class="level-item has-text-centered">
                <router-link class="p-2 text-dark" active-class="active" to="/about">About</router-link>
            </p>
            <p class="level-item has-text-centered">
                <img src="https://bulma.io/images/bulma-type.png" alt="" style="height: 30px;">
            </p>
            <p class="level-item has-text-centered">
                <router-link class="p-2 text-dark" active-class="active" to="/users">Users</router-link>
            </p>
            <p class="level-item has-text-centered">
                <a class="link is-info">Contact</a>
            </p>
        </nav>

        <router-view/>

        <footer class="footer">
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
