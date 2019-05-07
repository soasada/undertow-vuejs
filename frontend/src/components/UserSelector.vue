<template>
    <div class="field">
        <label for="userSelectorInput" class="label">Select user</label>
        <div class="control">
            <div class="select">
                <select id="userSelectorInput" @change="updateUserId($event.target.value)">
                    <option :value="null">Please select one User</option>
                    <option v-for="user in users" :key="user.id" :value="user.id">{{ user.username }}</option>
                </select>
            </div>
        </div>
    </div>
</template>

<script>
    import api from '@/components/api';

    export default {
        name: 'UserSelector',
        data() {
            return {
                users: [],
            };
        },
        async created() {
            this.getUsers();
        },
        methods: {
            async getUsers() {
                this.users = await api.all('/users');
            },
            updateUserId(userId) {
                this.$emit('input', userId);
            }
        }
    };
</script>

<style scoped>

</style>