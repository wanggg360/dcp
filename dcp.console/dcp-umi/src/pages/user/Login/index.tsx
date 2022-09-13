import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { Alert, message } from 'antd';
import React, { useState } from 'react';
import { ProFormCheckbox, ProFormText, LoginForm } from '@ant-design/pro-form';
import { useIntl, history, FormattedMessage, SelectLang, useModel } from 'umi';
import Footer from '@/components/Footer';
//import { login } from '@/services/dcpconsole/login'
import { login } from '@/services/sys/user'
import styles from './index.less';

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

const Login: React.FC = () => {
  const [userLoginState, setUserLoginState] = useState({});
  const { initialState, setInitialState } = useModel('@@initialState');
  const intl = useIntl();

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      await setInitialState((s) => ({
        ...s,
        currentUser: userInfo,
      }));
    }
  };

  const handleSubmit = async (values) => {
    // 请求体转换
    const formData: Sys.LoginReq = {
      userId: values?.username,
      password: values?.password,
      autoLogin: values?.autoLogin
    };

    try {
      // 登录
      const msg = await login( {...formData}, {skipErrorHandler: true} );

      if (msg?.data.status === 'ok') {
        const defaultLoginSuccessMessage = intl.formatMessage({
          id: 'pageDaoBeans.login.success',
          defaultMessage: '登录成功！',
        });
        message.success(defaultLoginSuccessMessage);
        await fetchUserInfo();
        /** 此方法会跳转到 redirect 参数所在的位置 */
        if (!history) return;
        const { query } = history.location;
        const { redirect } = query as { redirect: string };
        history.push(redirect || '/');
        return;
      }

      // 设置失败用户信息
      setUserLoginState(msg);
    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pageDaoBeans.login.failure',
        defaultMessage: '登录失败，请重试！',
      });
      message.error(defaultLoginFailureMessage);
    }
  };
  const { status } = userLoginState;

  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang />}
      </div>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src="/htsc_logo.svg" />}
          title="Digital CP"
          subTitle={intl.formatMessage({ id: 'pageDaoBeans.layouts.userLayout.title' })}
          initialValues={{
          }}

          onFinish={async (values) => {
            await handleSubmit(values);
          }}
        >

          {status === 'error' && (
            <LoginMessage
              content={intl.formatMessage({
                id: 'pageDaoBeans.login.error.username_or_passwd',
                defaultMessage: '用户名或密码错误',
              })}
            />
          )}
          {(
            <>
              <ProFormText
                name="username"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'pageDaoBeans.login.username.placeholder',
                  defaultMessage: '请输入用户名',
                })}
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage
                        id="pageDaoBeans.login.username.required"
                        defaultMessage="用户名必填!"
                      />
                    ),
                  },
                  {
                    pattern: /[a-zA-Z0-9_-]{5,10}/,
                    message: (
                      <FormattedMessage
                        id="pageDaoBeans.login.username.rule1"
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
                  id: 'pageDaoBeans.login.password.placeholder',
                  defaultMessage: '请输入密码',
                })}
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage
                        id="pageDaoBeans.login.password.required"
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
              <FormattedMessage id="pageDaoBeans.login.rememberMe" defaultMessage="自动登录" />
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
            >
              <FormattedMessage id="pageDaoBeans.login.forgotPassword" defaultMessage="忘记密码" />
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
