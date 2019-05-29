<template>
    <div class="home">
        <div class="container">
            <div class="starter-template">
                <h1>Undertow + Vue.js demo web application</h1>
                <p class="lead">Use this project as a seed for building web applications with Undertow and Vue.js</p>
            </div>
            <button class="button is-primary" :class="{'is-loading': loading}" @click="streamUsers" type="button" :disabled="loading">
                Stream users
            </button>
            <ul>
                <li v-for="user in users" :key="user.id">
                    {{user.id}} - {{ user.username }}
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'home',
        data() {
            return {
                users: [],
                loading: false
            };
        },
        watch: {
            users(newUsers) {
                return this.users = newUsers;
            }
        },
        methods: {
            streamUsers() {
                let eventSource = new EventSource("sse");
                this.loading = true;
                const self = this;

                eventSource.addEventListener('user', (event) => {
                    self.users.push(JSON.parse(event.data));
                }, false);

                axios.get('/api/stream/users')
                    .then((response) => {
                        self.loading = false;
                        eventSource.close();
                        self.users = [];
                    })
                    .catch((error) => {

                    });
            }
        }
    }
</script>

<style scoped>
    .starter-template {
        padding: 3rem 1.5rem;
        text-align: center;
    }
</style>
