<template>
    <div class="container">
        <h1>{{ serviceName }}</h1>
        <div class="columns">
            <slot name="selectorContent"></slot>
            <div class="column is-9">
                <slot name="tableContent"></slot>
            </div>
            <div class="column is-3">
                <slot name="formContent"></slot>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'CRUDTemplate',
        props: {
            serviceName: {
                type: String,
                required: true
            }
        }
    };
</script>
