<template>
    <div class="login">
        <section class="hero is-success is-fullheight">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-grey">Login</h3>
                        <p class="subtitle has-text-grey">Please login to proceed.</p>
                        <div class="notification is-warning" v-if="signInError">
                            {{signInErrorMsg}}
                        </div>
                        <div class="box">
                            <figure class="avatar">
                                <img src="../assets/example_128x128.png" alt="">
                            </figure>
                            <form @submit.prevent="signIn">
                                <div class="field">
                                    <label for="usernameInput" class="label">Username</label>
                                    <div class="control">
                                        <input id="usernameInput" class="input is-large" type="text"
                                               placeholder="Username" autofocus="" v-model="model.username">
                                    </div>
                                </div>

                                <div class="field">
                                    <label for="passwordInput" class="label">Password</label>
                                    <div class="control">
                                        <input id="passwordInput" class="input is-large" type="password"
                                               placeholder="Your Password" v-model="model.password">
                                    </div>
                                </div>
                                <div class="field">
                                    <label class="checkbox">
                                        <input type="checkbox">
                                        Remember me
                                    </label>
                                </div>
                                <button type="submit" class="button is-block is-info is-large is-fullwidth">Login
                                </button>
                            </form>
                        </div>
                        <p class="has-text-grey">
                            <a href="../">Sign Up</a> &nbsp;·&nbsp;
                            <a href="../">Forgot Password</a> &nbsp;·&nbsp;
                            <a href="../">Need Help?</a>
                        </p>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>

    export default {
        name: 'login',
        data() {
            return {
                model: {}
            };
        },
        computed: {
            signInError() {
                return this.$store.state.signInError;
            },
            signInErrorMsg() {
                return this.$store.state.signInErrorMsg;
            }
        },
        methods: {
            signIn() {
                const self = this;
                this.$store.dispatch('signIn', {
                    username: this.model.username,
                    password: this.model.password,
                    router: self.$router,
                    route: self.$route
                });
            }
        }
    }
</script>

<style scoped>
    html, body {
        font-family: 'Open Sans', serif;
        font-size: 14px;
        font-weight: 300;
    }

    .hero.is-success {
        background: #F2F6FA;
    }

    .hero .nav, .hero.is-success .nav {
        -webkit-box-shadow: none;
        box-shadow: none;
    }

    .box {
        margin-top: 5rem;
    }

    .avatar {
        margin-top: -70px;
        padding-bottom: 20px;
    }

    .avatar img {
        padding: 5px;
        background: #fff;
        border-radius: 50%;
        -webkit-box-shadow: 0 2px 3px rgba(10, 10, 10, .1), 0 0 0 1px rgba(10, 10, 10, .1);
        box-shadow: 0 2px 3px rgba(10, 10, 10, .1), 0 0 0 1px rgba(10, 10, 10, .1);
    }

    input {
        font-weight: 300;
    }

    p {
        font-weight: 700;
    }

    p.subtitle {
        padding-top: 1rem;
    }
</style>