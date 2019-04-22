<template>
    <div class="user">
        <CRUDTemplate serviceName="Users">
            <template #tableContent>
                <CRUDTable v-if="tableData.length > 0"
                           :columnNames="columnNames"
                           :tableData="tableData"
                           @modelWasSelected="model = $event"
                           @modelWasDeleted="deleteModel"/>
                <img src="../assets/loading.gif" v-else>
            </template>
            <template #formContent>
                <EditAddForm :model="model" resourceName="users" @submittedForm="submittedForm" @modelWasReset="model = $event">
                    <template #formFieldsContent>
                        <div class="form-group">
                            <label for="userIdInput">User ID</label>
                            <input type="number" class="form-control" id="userIdInput" v-model="model.id" readonly>
                        </div>
                        <div class="form-group">
                            <label for="usernameInput">Username</label>
                            <input type="text" class="form-control" id="usernameInput" v-model="model.username">
                        </div>
                        <div class="form-group">
                            <label for="passwordInput">Password</label>
                            <input type="text" class="form-control" id="passwordInput" v-model="model.password">
                        </div>
                    </template>
                </EditAddForm>
            </template>
        </CRUDTemplate>
    </div>
</template>

<script>
    import CRUDTemplate from '@/components/CRUDTemplate.vue';
    import CRUDTable from '@/components/CRUDTable.vue';
    import EditAddForm from '@/components/EditAddForm.vue';
    import api from '@/components/api';

    export default {
        name: 'user',
        components: {
            CRUDTemplate,
            CRUDTable,
            EditAddForm
        },
        data() {
            return {
                columnNames: ['id', 'username', 'password', 'createdAt', 'updatedAt'],
                model: {},
                tableData: [],
            };
        },
        async mounted() {
            this.refreshTable();
        },
        methods: {
            async refreshTable() {
                this.tableData = await api.all('/users');
            },
            async deleteModel(model) {
                await api.delete('/users', model.id);
                this.refreshTable();
            },
            async submittedForm() {
                this.model = {};
                this.refreshTable();
            }
        }
    };
</script>

<style scoped>

</style>