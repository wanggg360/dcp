import { defineConfig } from 'umi';
import { join } from 'path';
import defaultSettings from './defaultSettings';
import proxy from './proxy';

const { REACT_APP_ENV } = process.env;

export default defineConfig({
  hash: true,
  antd: {},
  dva: {
    hmr: true,
  },
  layout: {
    locale: true,
    siderWidth: 208,
    ...defaultSettings,
  },

  locale: {
    // default zh-CN
    default: 'zh-CN',
    antd: true,
    baseNavigator: true,
  },
  dynamicImport: {
    loading: '@ant-design/pro-layout/es/PageLoading',
  },
  targets: {
    ie: 11,
  },

  routes: [
    {
      path: '/user',
      layout: false,
      routes: [
        {
          path: '/user/login',
          layout: false,
          name: 'login',
          component: './user/login',
        },
        {
          path: '/user',
          redirect: '/user/login',
        },
        {
          component: '404',
        },
      ],
    },

    // 根路径重定向
    {
      path: '/',
      redirect: '/home',
    },

    // 首页
    {
      name: 'home',
      icon: 'home',
      path: '/home',
      component: './home'
    },

    // 工作空间
    {
      name: 'workspace',
      icon: 'desktop',
      path: '/workspace',
      routes: [
        {
          name: 'process-center',
          path: '/workspace/process-center',
          component: './account/settings',
          routes: [
            {
              path: '/workspace/process-center',
              redirect: '/workspace/process-center/my-process',
            },
            {
              name: 'new-process',
              path: '/workspace/process-center/new-process',
              component: './list/search/articles',
            },
            {
              name: 'my-process',
              path: '/workspace/process-center/my-process',
              component: './list/search/articles',
            },
            {
              name: 'process-map',
              path: '/workspace/process-center/process-map',
              component: './list/search/articles',
            }
          ]
        },
        {
          name: 'task-center',
          path: '/workspace/task-center',
          component: './account/settings',
          routes: [
            {
              path: '/workspace/task-center',
              redirect: '/workspace/task-center/my-task',
            },
            {
              name: 'new-task',
              path: '/workspace/task-center/new-task',
              component: './list/search/articles',
            },
            {
              name: 'my-task',
              path: '/workspace/task-center/my-task',
              component: './list/search/articles',
            }
          ]
        },
        {
          name: 'warn-center',
          path: '/workspace/warn-center',
          component: './account/settings',
        },
        {
          name: 'my-profile',
          path: '/workspace/my-profile',
          component: './account/settings',
        }
      ],
    },

    // 系统管理
    {
      name: 'system',
      icon: 'setting',
      path: '/system',
      routes: [
        {
          name: 'configuration',
          path: '/system/configuration',
          component: './account/center',
          routes: [
            {
              name: 'parameter',
              path: '/system/configuration/parameter',
              component: './list/search/articles',
            },
            {
              name: 'dictionary',
              path: '/system/configuration/dictionary',
              component: './list/search/articles',
            }
          ]
        },
        {
          name: 'permission',
          path: '/system/permission',
          component: './account/settings',
          routes: [
            {
              name: 'department',
              path: '/system/permission/department',
              component: './list/search/articles',
            },
            {
              name: 'user',
              path: '/system/permission/user',
              component: './list/search/articles',
            },
            {
              name: 'role',
              path: '/system/permission/role',
              component: './list/search/articles',
            }
          ]
        },
      ],
    },








    {
      name: 'account',
      icon: 'user',
      path: '/account',
      routes: [
        {
          path: '/account',
          redirect: '/account/center',
        },
        {
          name: 'center',
          icon: 'smile',
          path: '/account/center',
          component: './account/center',
        },
        {
          name: 'settings',
          icon: 'smile',
          path: '/account/settings',
          component: './account/settings',
        },
      ],
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      icon: 'dashboard',
      routes: [
        {
          path: '/dashboard',
          redirect: '/dashboard/analysis',
        },
        {
          name: 'analysis',
          icon: 'smile',
          path: '/dashboard/analysis',
          component: './dashboard/analysis',
        },
        {
          name: 'monitor',
          icon: 'smile',
          path: '/dashboard/monitor',
          component: './dashboard/monitor',
        },
        {
          name: 'workplace',
          icon: 'smile',
          path: '/dashboard/workplace',
          component: './dashboard/workplace',
        },
      ],
    },
    {
      path: '/form',
      icon: 'form',
      name: 'form',
      routes: [
        {
          path: '/form',
          redirect: '/form/basic-form',
        },
        {
          name: 'basic-form',
          icon: 'smile',
          path: '/form/basic-form',
          component: './form/basic-form',
        },
        {
          name: 'step-form',
          icon: 'smile',
          path: '/form/step-form',
          component: './form/step-form',
        },
        {
          name: 'advanced-form',
          icon: 'smile',
          path: '/form/advanced-form',
          component: './form/advanced-form',
        },
      ],
    },
    {
      path: '/list',
      icon: 'table',
      name: 'list',
      routes: [
        {
          path: '/list/search',
          name: 'search-list',
          component: './list/search',
          routes: [
            {
              path: '/list/search',
              redirect: '/list/search/articles',
            },
            {
              name: 'articles',
              icon: 'smile',
              path: '/list/search/articles',
              component: './list/search/articles',
            },
            {
              name: 'projects',
              icon: 'smile',
              path: '/list/search/projects',
              component: './list/search/projects',
            },
            {
              name: 'applications',
              icon: 'smile',
              path: '/list/search/applications',
              component: './list/search/applications',
            },
          ],
        },
        {
          path: '/list',
          redirect: '/list/table-list',
        },
        {
          name: 'table-list',
          icon: 'smile',
          path: '/list/table-list',
          component: './list/table-list',
        },
        {
          name: 'basic-list',
          icon: 'smile',
          path: '/list/basic-list',
          component: './list/basic-list',
        },
        {
          name: 'card-list',
          icon: 'smile',
          path: '/list/card-list',
          component: './list/card-list',
        },
      ],
    },
    {
      path: '/profile',
      name: 'profile',
      icon: 'profile',
      routes: [
        {
          path: '/profile',
          redirect: '/profile/basic',
        },
        {
          name: 'basic',
          icon: 'smile',
          path: '/profile/basic',
          component: './profile/basic',
        },
        {
          name: 'advanced',
          icon: 'smile',
          path: '/profile/advanced',
          component: './profile/advanced',
        },
      ],
    },
    {
      name: 'result',
      icon: 'CheckCircleOutlined',
      path: '/result',
      routes: [
        {
          path: '/result',
          redirect: '/result/success',
        },
        {
          name: 'success',
          icon: 'smile',
          path: '/result/success',
          component: './result/success',
        },
        {
          name: 'fail',
          icon: 'smile',
          path: '/result/fail',
          component: './result/fail',
        },
      ],
    },
    {
      name: 'exception',
      icon: 'warning',
      path: '/exception',
      routes: [
        {
          path: '/exception',
          redirect: '/exception/403',
        },
        {
          name: '403',
          icon: 'smile',
          path: '/exception/403',
          component: './exception/403',
        },
        {
          name: '404',
          icon: 'smile',
          path: '/exception/404',
          component: './exception/404',
        },
        {
          name: '500',
          icon: 'smile',
          path: '/exception/500',
          component: './exception/500',
        },
      ],
    },
    {
      name: 'editor',
      icon: 'highlight',
      path: '/editor',
      routes: [
        {
          path: '/editor',
          redirect: '/editor/flow',
        },
        {
          name: 'flow',
          icon: 'smile',
          path: '/editor/flow',
          component: './editor/flow',
        },
        {
          name: 'mind',
          icon: 'smile',
          path: '/editor/mind',
          component: './editor/mind',
        },
        {
          name: 'koni',
          icon: 'smile',
          path: '/editor/koni',
          component: './editor/koni',
        },
      ],
    },
  ],


  access: {},

  theme: {
    // 值 default/variable
    'root-entry-name': 'default',
  },
  esbuild: {},

  title: false,
  ignoreMomentLocale: true,
  proxy: proxy[REACT_APP_ENV || 'dev'],
  manifest: {
    basePath: '/',
  },

  fastRefresh: {},

  request: {
    dataField: 'data'
    },

  openAPI: [
    {
      requestLibPath: "import { request } from 'umi'",
      schemaPath: join(__dirname, 'oneapi.json'),
      mock: true,
    },
  ],
  nodeModulesTransform: {
    type: 'none',
  },
  mfsu: {},
  webpack5: {},
  exportStatic: {},
});
