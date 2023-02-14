import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { message } from 'antd';
import React from 'react';
import { ProFormCheckbox, ProFormText, LoginForm } from '@ant-design/pro-form';
import { useIntl, history, FormattedMessage, SelectLang, useModel } from 'umi';
import Footer from '@/components/Footer';
import {login, queryUserDetails} from '@/services/sys/user'
import styles from './index.less';


const Login: React.FC = () => {
  const { initialState, setInitialState } = useModel('@@initialState');
  const intl = useIntl();

  const initUserInfo = async () => {
    const userInfo = initialState?.fetchUserInfo?.();
    if (userInfo) {
      await setInitialState((s) => ({
        ...s,
        currentUser: userInfo,
      }));
    }
  };

  const handleSubmit = async (values: Sys.LoginReq) => {
    try {
      // 登录
      const loginRsp = await login( {...values}, {skipErrorHandler: true} );

      if (loginRsp?.success === true && loginRsp?.data) {
        // const queryDetailsReq: Sys.QueryUserDetailsReq = {
        //   userId: values?.userId
        // };
        // const userDetailsResp = await queryUserDetails(queryDetailsReq)
        // if (userDetailsResp?.success === true) {

          // 登陆成功后设置storage
          localStorage.setItem(USER_DETAILS_TAG, JSON.stringify(loginRsp.data))

          const defaultLoginSuccessMessage = intl.formatMessage({
            id: 'pages.login.success',
            defaultMessage: '登录成功！',
          });
          message.success(defaultLoginSuccessMessage);

          await initUserInfo();

          /** 此方法会跳转到 redirect 参数所在的位置 */
          if (!history) return;
          const { query } = history.location;
          const { redirect } = query as { redirect: string };
          history.push(redirect || '/');
          return;
        // }
      }
      const loginFailureMessage = intl.formatMessage({
        id: 'pages.login.error.username_or_passwd',
        defaultMessage: '登录失败，请重试！',
      });
      message.error(loginFailureMessage);
    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pages.login.failure',
        defaultMessage: '登录失败，请重试！',
      });
      message.error(defaultLoginFailureMessage);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang />}
      </div>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src="/htsc_logo.svg" />}
          title="Digital CP"
          subTitle={intl.formatMessage({ id: 'pages.layouts.userLayout.title' })}
          initialValues={{
          }}

          onFinish={async (values) => {
            await handleSubmit(values);
          }}
        >

          {(
            <>
              <ProFormText
                name="userId"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'pages.login.username.placeholder',
                  defaultMessage: '请输入用户名',
                })}
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage
                        id="pages.login.username.required"
                        defaultMessage="用户名必填!"
                      />
                    ),
                  },
                  {
                    pattern: /[a-zA-Z0-9_-]{5,10}/,
                    message: (
                      <FormattedMessage
                        id="pages.login.username.rule1"
                        defaultMessage="用户名是5-10位字母或者数字！"
                      />
                    ),
                  },
                ]}
              />
              <ProFormText.Password
                name="password"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'pages.login.password.placeholder',
                  defaultMessage: '请输入密码',
                })}
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage
                        id="pages.login.password.required"
                        defaultMessage="密码必填！"
                      />
                    ),
                  },
                ]}
              />
            </>
          )}

          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              <FormattedMessage id="pages.login.rememberMe" defaultMessage="自动登录" />
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
            >
              <FormattedMessage id="pages.login.forgotPassword" defaultMessage="忘记密码" />
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
