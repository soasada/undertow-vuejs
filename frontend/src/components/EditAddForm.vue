<template>
    <div class="edit-add-form">
        <div class="card">
            <header class="card-header">
                <p class="card-header-title">{{ model.id ? 'Edit ID#' + model.id : 'Add' }}</p>
                <a class="card-header-icon button is-info" @click="removeValToAdd()">Clear form</a>
            </header>

            <div class="card-content">
                <form @submit.prevent="sendForm()">
                    <slot name="formFieldsContent"></slot>
                    <button type="submit" class="button is-info">{{ model.id ? 'Edit' : 'Add' }}</button>
                </form>
            </div>
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

</style>
