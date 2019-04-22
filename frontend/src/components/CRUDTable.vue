<template>
    <div class="custom-table">
        <div class="row">
            <div class="col-12 text-right">
                Search <input name="query" v-model="searchQuery">
            </div>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th v-for="columnName in columnNames">
                    {{ columnName }}
                </th>
                <th>actions</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="val in getRows()" :key="val.id">
                <td v-for="columnName in columnNames">
                    {{ discoverValue(val, columnName) }}
                </td>
                <td class="text-right">
                    <a href="#" @click.prevent="$emit('modelWasSelected', val)">Edit</a>&nbsp;&nbsp;&nbsp;
                    <a href="#" @click.prevent="$emit('modelWasDeleted', val)">Delete</a>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="row">
            <div class="col-6 text-left">
                <div class="number"
                     v-for="i in numPages()"
                     v-bind:class="[i === currentPage ? 'active' : '']"
                     v-on:click="changePage(i)">{{ i }}
                </div>
            </div>
            <div class="col-6 text-right">
                Total: {{ tableData.length }}
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'CRUDTable',
        props: {
            columnNames: {
                type: Array,
                required: true,
            },
            tableData: {
                type: Array,
                required: true,
            }
        },
        data() {
            return {
                elementsPerPage: 10,
                currentPage: 1,
                searchQuery: ''
            };
        },
        methods: {
            changePage(page) {
                this.currentPage = page;
            },

            getRows() {
                const start = (this.currentPage - 1) * this.elementsPerPage;
                const end = start + this.elementsPerPage;

                let dataToShow = this.tableData;
                if (this.searchQuery) {
                    return dataToShow.filter((row) => {
                        return Object.keys(row).some((key) => {
                            return JSON.stringify(row[key]).toUpperCase().indexOf(this.searchQuery.toUpperCase()) > -1;
                        });
                    });
                }

                return dataToShow.slice(start, end);
            },

            discoverValue(val, columnName) {
                const properties = columnName.split('.');

                if (properties.length === 1) {
                    return val[properties[0]];
                } else {
                    return this.discoverValue(val[properties[0]], properties.slice(1).join('.'));
                }
            },

            numPages() {
                return Math.ceil(this.tableData.length / this.elementsPerPage);
            }
        }
    };
</script>

<style lang="scss">
    .pagination {
        font-family: 'Open Sans', sans-serif;
        text-align: right;
        width: 750px;
        padding: 8px;
    }

    .number {
        display: inline-block;
        padding: 4px 10px;
        color: #FFF;
        border-radius: 4px;
        background: #2c3e50;
        margin: 0px 5px;
        cursor: pointer;
    }

    .number:hover, .number.active {
        background: #42b983 !important;
        border: #42b983 !important;
    }
</style>
