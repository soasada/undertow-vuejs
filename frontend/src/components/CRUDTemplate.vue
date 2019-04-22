<template>
    <div class="container-fluid">
        <h1 class="h1">{{ serviceName }}</h1>
        <slot name="selectorContent"></slot>
        <div class="row">
            <div class="col-lg-9">
                <slot name="tableContent"></slot>
            </div>
            <div class="col-lg-3">
                <div class="form-row">
                    <div class="col-lg-12">
                        <slot name="formContent"></slot>
                    </div>
                </div>
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
