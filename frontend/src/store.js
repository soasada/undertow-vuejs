import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import jwt from 'jsonwebtoken';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        token: null,
        signInError: false,
        signInErrorMsg: ''
    },
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
        },
        SET_SIGN_IN_ERROR(state, signInError) {
            state.signInError = signInError;
        },
        SET_SIGN_IN_ERROR_MSG(state, signInErrorMsg) {
            state.signInErrorMsg = signInErrorMsg;
        }
    },
    actions: {
        signIn({commit}, {username, password, router, route}) {

            axios.post('/api/v1/login', {
                username: username,
                password: password
            }).then((response) => {
                const token = response.data.token;
                commit('SET_TOKEN', token);
                sessionStorage.setItem('token', token);
                if (route.query.redirect) {
                    router.push(route.query.redirect);
                } else {
                    router.push('home');
                }
            }).catch((error) => {
                commit('SET_TOKEN', null);
                commit('SET_SIGN_IN_ERROR', true);
                commit('SET_SIGN_IN_ERROR_MSG', error.response);
                sessionStorage.removeItem('token');
            });
        },
        signOut({commit}) {
            commit('SET_TOKEN', null);
            sessionStorage.removeItem('token');
        }
    },
    getters: {
        isAuthenticated(state) {
            if (state.token === null) {
                return false;
            }

            const decoded = jwt.decode(state.token.replace('Bearer ', ''));
            const expDate = new Date(decoded.exp * 1000);
            const now = new Date();
            return expDate > now;
        }
    }
});
