<template>
    <div class="home">
        <div class="container">
            <div class="starter-template">
                <h1>Undertow + Vue.js demo web application</h1>
                <p class="lead">Use this project as a seed for building web applications with Undertow and Vue.js</p>
            </div>
            <div class="columns">
                <div class="column">
                    <button class="button is-primary" :class="{'is-loading': loadingUsers}" @click="streamUsers" type="button" :disabled="loadingUsers">
                        Stream users
                    </button>
                    <ul>
                        <li v-for="user in users" :key="user.id">
                            {{user.id}} - {{ user.username }}
                        </li>
                    </ul>
                </div>
                <div class="column">
                    <button class="button is-primary" :class="{'is-loading': loadingNumbers}" @click="streamNumbers" type="button" :disabled="loadingNumbers">
                        Stream numbers
                    </button>
                    <ul>
                        <li v-for="number in numbers" :key="number">
                            - {{ number }}
                        </li>
                    </ul>
                </div>
            </div>
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
                numbers: [],
                loadingUsers: false,
                loadingNumbers: false
            };
        },
        watch: {
            users(newUsers) {
                return this.users = newUsers;
            },
            numbers(newNumbers) {
                return this.numbers = newNumbers;
            }
        },
        methods: {
            streamUsers() {
                let eventSource = new EventSource("sse");
                this.loadingUsers = true;
                const self = this;

                eventSource.addEventListener('user', (event) => {
                    self.users.push(JSON.parse(event.data));
                }, false);

                axios.get('/api/stream/users')
                    .then(() => {
                        self.loadingUsers = false;
                        eventSource.close();
                        self.users = [];
                    });
            },
            streamNumbers() {
                let eventSource = new EventSource("sse");
                this.loadingNumbers = true;
                const self = this;

                eventSource.addEventListener('number', (event) => {
                    self.numbers.push(event.data);
                }, false);

                eventSource.addEventListener('close', () => {
                    self.loadingNumbers = false;
                    self.numbers = [];
                    eventSource.close();
                }, false);

                axios.get('/api/stream/numbers');
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
