// vue.config.js
module.exports = {
    // proxy all webpack dev-server requests starting with /api/v1
    // to our Spring Boot backend (localhost:8080) using http-proxy-middleware
    // see https://cli.vuejs.org/config/#devserver-proxy
    devServer: {
        proxy: {
            '/api/v1': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true
            }
        }
    },
    // Change build paths to make them Maven compatible
    // see https://cli.vuejs.org/config/
    outputDir: 'target/dist',
    assetsDir: 'static'
};