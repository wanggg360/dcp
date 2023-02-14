import { request } from 'umi';

// 登陆
export async function login(body: Sys.LoginReq, options?: { [key: string]: any }) {
  return request('/sys/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

// 登出
export async function logout(options?: { [key: string]: any }) {
  return request('/sys/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

// 获取用户详情信息
export async function queryUserDetails(body: Sys.QueryUserDetailsReq, options?: { [key: string]: any }) {
  return request('/sys/user/queryUserDetails', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

//
