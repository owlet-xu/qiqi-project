module.exports = {
  baseUrl: './',
  devServer: {
    port: 8080
  },
  css: {
    loaderOptions: {
      sass: {
        data: `@import "@/assets/styles/helpers/mixin.scss";@import "@/assets/styles/themes/variables.scss";`
      }
    }
  }
};
