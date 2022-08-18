module.exports = {
  testURL: 'http://localhost:8000',
  verbose: false,
  setupFiles: ['./tests/setupTests.js'],
  globals: {
    BACKEND_SERVER_URL: false,
    localStorage: null,
  },
};
