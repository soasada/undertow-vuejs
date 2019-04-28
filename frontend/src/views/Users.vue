<template>
    <div class="users">
        <CRUDTemplate>
            <template #tableContent>
                <CRUDTable v-if="tableData.length > 0"
                           :columnNames="columnNames"
                           :tableData="tableData"
                           @modelWasSelected="model = $event"
                           @modelWasDeleted="deleteModel"/>
                <img src="../assets/loading.gif" v-else>
            </template>
            <template #formContent>
                <EditAddForm :model="model" resourceName="users" @submittedForm="submittedForm"
                             @modelWasReset="model = $event">
                    <template #formFieldsContent>
                        <fieldset disabled>
                            <div class="field">
                                <label for="userIdInput" class="label">User ID</label>
                                <div class="control">
                                    <input id="userIdInput" class="input" type="number" v-model="model.id">
                                </div>
                            </div>
                        </fieldset>

                        <div class="field">
                            <label for="usernameInput" class="label">Username</label>
                            <div class="control">
                                <input id="usernameInput" class="input" type="text" v-model="model.username">
                            </div>
                        </div>

                        <div class="field">
                            <label for="passwordInput" class="label">Username</label>
                            <div class="control">
                                <input id="passwordInput" class="input" type="text" v-model="model.password">
                            </div>
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
        name: 'users',
        components: {
            CRUDTemplate,
            CRUDTable,
            EditAddForm
        },
        data() {
            return {
                columnNames: ['id', 'username', 'createdAt', 'updatedAt'],
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