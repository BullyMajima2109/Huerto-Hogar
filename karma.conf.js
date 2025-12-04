// karma.conf.js
module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine'],
    files: [
      // primero la lógica del carrito
      'public/js/cartLogic.js',
      // luego todos los specs
      'tests/**/*.spec.js',
    ],
    exclude: [],
    preprocessors: {
      'public/js/cartLogic.js': ['coverage'],
    },
    reporters: ['progress', 'coverage'],
    coverageReporter: {
      type: 'html',
      dir: 'coverage/',
    },
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['ChromeHeadless'],
    singleRun: false,
    restartOnFileChange: true,
  });
};
