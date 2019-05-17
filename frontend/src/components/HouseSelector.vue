<template>
    <div class="field">
        <label for="houseSelectorInput" class="label">Select house</label>
        <div class="control">
            <div class="select">
                <select id="houseSelectorInput" @change="updateHouseId($event.target.value)">
                    <option :value="null">Select House</option>
                    <option v-for="house in houses" :key="house.id" :value="house.id">{{ house.name }}</option>
                </select>
            </div>
        </div>
    </div>
</template>

<script>
    import api from '@/components/api';

    export default {
        name: 'HouseSelector',
        props: {
            userId: {
                type: Number,
                required: true,
            }
        },
        data() {
            return {
                houses: [],
            };
        },
        watch: {
            userId() {
                this.getHouses();
            }
        },
        async created() {
            this.getHouses();
        },
        methods: {
            async getHouses() {
                this.houses = await api.all('/user/' + this.userId + '/houses');
            },
            updateHouseId(houseId) {
                this.$emit('input', houseId);
            }
        }
    };
</script>

<style scoped>

</style>