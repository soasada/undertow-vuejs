<template>
    <div class="houses">
        <CRUDTemplate>
            <template #tableContent>
                <CRUDTable v-if="tableData.length > 0"
                           :columnNames="columnNames"
                           :tableData="tableData"
                           @modelWasSelected="model = $event"
                           @modelWasDeleted="deleteModel"/>
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
        async mounted() {
            this.refreshTable();
        },
        methods: {
            async refreshTable() {
                this.tableData = await api.all('/houses');
            },
            async deleteModel(model) {
                await api.delete('/houses', model.id);
                this.refreshTable();
            },
            async submittedForm() {
                this.model = {};
                this.refreshTable();
            }
        }
    }
</script>

<style scoped>

</style>