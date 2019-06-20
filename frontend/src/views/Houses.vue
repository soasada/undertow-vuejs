<template>
    <div class="houses">
        <CRUDTemplate>
            <template #tableContent>
                <CRUDTable v-if="tableData.length > 0"
                           :columnNames="columnNames"
                           :tableData="tableData"
                           @modelWasSelected="model = $event"
                           @modelWasDeleted="deleteModel"/>
                <h2 class="title is-2" v-else-if="model.userId === null || model.userId === undefined">Please select a
                    user</h2>
                <h2 class="title is-2" v-else-if="tableData.length <= 0">No data found</h2>
                <img alt="loading" src="../assets/loading.gif" v-else>
            </template>
            <template #formContent>
                <EditAddForm :model="model" resourceName="houses" @submittedForm="submittedForm"
                             @modelWasReset="model = $event">
                    <template #formFieldsContent>
                        <fieldset disabled>
                            <div class="field">
                                <label for="houseIdInput" class="label">House ID</label>
                                <div class="control">
                                    <input id="houseIdInput" class="input" type="number" v-model="model.id">
                                </div>
                            </div>
                        </fieldset>

                        <div class="field">
                            <label for="nameInput" class="label">House name</label>
                            <div class="control">
                                <input id="nameInput" class="input" type="text" v-model="model.name">
                            </div>
                        </div>

                        <UserSelector v-model="model.userId"/>
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
    import UserSelector from '@/components/UserSelector.vue';
    import api from '@/components/api';

    export default {
        name: 'houses',
        components: {
            CRUDTemplate,
            CRUDTable,
            EditAddForm,
            UserSelector
        },
        data() {
            return {
                columnNames: ['id', 'name', 'userId', 'createdAt', 'updatedAt'],
                model: {},
                tableData: []
            };
        },
        computed: {
            userId() {
                return this.model.userId;
            }
        },
        watch: {
            async userId(newUserId) {
                this.refreshTable(newUserId);
            }
        },
        methods: {
            async refreshTable(userId) {
                if (userId) this.tableData = await api.all('/user/' + userId + '/houses');
            },
            async deleteModel(model) {
                await api.delete('/houses', model.id);
                this.refreshTable(model.userId);
            },
            async submittedForm() {
                const userId = this.model.userId;
                this.refreshTable(userId);
                this.model = {userId: userId};
            }
        }
    };
</script>

<style scoped>

</style>