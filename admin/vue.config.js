const path = require('path');
function resolve(dir){
    return path.join(__dirname,dir)
}

module.exports = {
    chainWebpack: config => {
        config.resolve.alias
            .set('@', './src')
            .set('@api', resolve('./src/api'))
            .set('@assets', resolve('./src/assets'))
            .set('@components', resolve('./src/components'))
            .set('@layouts', resolve('./src/layouts'))
            .set('@router', resolve('./src/router'))
            .set('@store', resolve('./src/store'))
            .set('@utils', resolve('./src/utils'))
            .set('@views', resolve('./src/views'))
    },
    productionSourceMap: false,
}