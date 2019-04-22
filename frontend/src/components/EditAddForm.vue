<template>
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">{{ model.id ? 'Edit ID#' + model.id : 'Add' }}</h4>
            <button type="button" class="btn number active btn-secondary" @click="removeValToAdd()">Clear form</button>
            <form @submit.prevent="sendForm()">
                <slot name="formFieldsContent"></slot>
                <button type="submit" class="btn number active btn-secondary">{{ model.id ? 'Edit' : 'Add' }}</button>
            </form>
        </div>
    </div>
</template>

<script>
    import api from '@/components/api';

    export default {
        name: 'EditAddForm',
        props: {
            model: {
                type: Object,
                required: true,
            },
            resourceName: {
                type: String,
                required: true,
            },
        },
        methods: {
            async sendForm() {
                if (this.model.id) {
                    await api.update('/' + this.resourceName, this.model);
                } else {
                    await api.create('/' + this.resourceName, this.model);
                }
                this.$emit('submittedForm', {});
            },
            removeValToAdd() {
                this.$emit('modelWasReset', {});
            },
        },
    };
</script>

<style scoped>
    .number:hover, .number.active {
        background: #42b983 !important;
        border: #42b983 !important;
    }
</style>
