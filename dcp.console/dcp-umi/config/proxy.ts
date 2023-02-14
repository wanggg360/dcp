export default {
  dev: {
    // localhost:8000/api/** -> https://preview.pro.ant.design/api/**
    '/sys/': {
      // 要代理的地址
      target: 'http://127.0.0.1:8080',
      // 配置了这个可以从 http 代理到 https
      // 依赖 origin 的功能可能需要这个，比如 cookie
      changeOrigin: true,
    },
  },
  test: {
    '/sys/': {
      target: 'http://127.0.0.1:8081',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
  prod: {
    '/sys/': {
      target: 'http://127.0.0.1:8082',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
};
