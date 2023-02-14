import type { Settings as LayoutSettings } from '@ant-design/pro-layout';
import { SettingDrawer } from '@ant-design/pro-layout';
import { PageLoading } from '@ant-design/pro-layout';
import type { RunTimeLayoutConfig } from 'umi';
import { history, Link } from 'umi';
import RightContent from '@/components/RightContent';
import Footer from '@/components/Footer';
import { BookOutlined, LinkOutlined } from '@ant-design/icons';
import defaultSettings from '../config/defaultSettings';
import {RequestConfig} from 'umi';

const isDev = process.env.NODE_ENV === 'development';

const loginPath = '/user/login';

/** 获取用户信息比较慢的时候会展示一个 loading */
export const initialStateConfig = {
  loading: <PageLoading />,
};

export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: Sys.User;
  loading?: boolean;
  fetchUserInfo?: () => Sys.User | undefined;
}> {
  const fetchUserInfo = () => {
    try {
      const userObj = localStorage.getItem(USER_DETAILS_TAG);
      if (userObj === null) {
        return undefined;
      }
      return JSON.parse(userObj);
    } catch (error) {
      history.push(loginPath);
      return undefined;
    }
  };
  // 如果不是登录页面，执行
  if (history.location.pathname !== loginPath) {
    const currentUser = await fetchUserInfo();
    return {
      fetchUserInfo,
      currentUser,
      settings: defaultSettings,
    };
  }
  return {
    fetchUserInfo,
    settings: defaultSettings,
  };
}

export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: {
      content: initialState?.currentUser?.userId,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    links: isDev
      ? [
          <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
          <Link to="/~docs" key="docs">
            <BookOutlined />
            <span>业务组件文档</span>
          </Link>,
        ]
      : [],
    menuHeaderRender: undefined,

    // menuItemRender: (menuItemProps, defaultDom) => {
    //   if (menuItemProps.isUrl || !menuItemProps.path) {
    //     return defaultDom;
    //   }
    //   // 支持二级菜单显示icon
    //   return (
    //     <Link to={menuItemProps.path}>
    //       {menuItemProps.pro_layout_parentKeys
    //       && menuItemProps.pro_layout_parentKeys.length > 0 &&
    //       menuItemProps.icon}{defaultDom}
    //     </Link>
    //   );
    // },

    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,

    // 增加一个 loading 的状态
    childrenRender: (children, props) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {!props.location?.pathname?.includes('/login') && (
            <SettingDrawer
              disableUrlParams
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({
                  ...preInitialState,
                  settings,
                }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};

// RequestConfig 统一处理响应
export const request: RequestConfig = {
  errorConfig: {
    adaptor: (resData) => {
      return {
        success: resData.success,
        data: resData.data,
        errorCode: resData.code,
        errorMessage: resData.message
      };
    },
  },
};
