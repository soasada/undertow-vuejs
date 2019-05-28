import axios from 'axios';

const CLIENT = axios.create({
    baseURL: '/api/v1'
});

export default {
    all(resource) {
        return this.execute('GET', resource);
    },

    create(resource, data) {
        return this.execute('POST', resource, data);
    },

    read(resource, id) {
        return this.execute('GET', resource + '/' + id);
    },

    update(resource, data) {
        return this.execute('PUT', resource, data);
    },

    delete(resource, id) {
        if (confirm('Are you sure? ID: ' + id)) {
            return this.execute('DELETE', resource + '/' + id);
        }
    },

    execute(method, resource, data = {}) {
        return CLIENT.request({
            method,
            url: resource,
            data,
            headers: {authorization: sessionStorage.getItem('token')}
        })
            .then((res) => res.data)
            .catch((error) => alert(error));
    }
};
