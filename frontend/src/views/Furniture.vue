<template>
    <div class="furniture">
        <CRUDTemplate>
            <template #tableContent>
                <CRUDTable v-if="tableData.length > 0"
                           :columnNames="columnNames"
                           :tableData="tableData"
                           @modelWasSelected="model = $event"
                           @modelWasDeleted="deleteModel"/>
                <h2 class="title is-2" v-else-if="userId === null || userId === 0">Please select a user</h2>
                <h2 class="title is-2" v-else-if="model.houseId === null || model.houseId === undefined">Please select a house</h2>
                <h2 class="title is-2" v-else-if="tableData.length <= 0">No data found</h2>
                <img alt="loading" src="../assets/loading.gif" v-else>
            </template>
            <template #formContent>
                <EditAddForm :model="model" resourceName="furniture" @submittedForm="submittedForm" @modelWasReset="model = $event">
                    <template #formFieldsContent>
                        <fieldset disabled>
                            <div class="field">
                                <label for="furnitureIdInput" class="label">Furniture ID</label>
                                <div class="control">
                                    <input id="furnitureIdInput" class="input" type="number" v-model="model.id">
                                </div>
                            </div>
                        </fieldset>

                        <div class="field">
                            <label for="furnitureNameInput" class="label">Furniture name</label>
                            <div class="control">
                                <input id="furnitureNameInput" class="input" type="text" v-model="model.name">
                            </div>
                        </div>

                        <div class="field">
                            <label for="furnitureTypeInput" class="label">Furniture type</label>
                            <div class="control">
                                <input id="furnitureTypeInput" class="input" type="text" v-model="model.type">
                            </div>
                        </div>

                        <UserSelector v-model="userId"/>
                        <HouseSelector :userId="userId" v-model="model.houseId" v-if="userId > 0"/>
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
    import HouseSelector from '@/components/HouseSelector.vue';
    import api from '@/components/api';

    export default {
        name: 'furniture',
        components: {
            CRUDTemplate,
            CRUDTable,
            EditAddForm,
            UserSelector,
            HouseSelector
        },
        data() {
            return {
                columnNames: ['id', 'name', 'type', 'houseId', 'createdAt', 'updatedAt'],
                model: {},
                tableData: [],
                userId: 0
            };
        },
        computed: {
            houseId() {
                return this.model.houseId;
            }
        },
        watch: {
            async houseId(newHouseId, oldHouseId) {
                this.refreshTable(newHouseId);
            }
        },
        methods: {
            async refreshTable(houseId) {
                if (houseId) this.tableData = await api.all('/house/' + houseId + '/furniture');
            },
            async deleteModel(model) {
                await api.delete('/furniture', model.id);
                this.refreshTable(model.houseId);
            },
            async submittedForm() {
                const houseId = this.model.houseId;
                this.refreshTable(houseId);
                this.model = {houseId: houseId};
            }
        }
    };
</script>

<style scoped>

</style>
