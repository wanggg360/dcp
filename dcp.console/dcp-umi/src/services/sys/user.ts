import { request } from 'umi';

const serverUrl = BACKEND_SERVER_URL

// 登陆
export async function login(body: Sys.LoginReq, options?: { [key: string]: any }) {
  return request('/api/user/login', {
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
  return request('/api/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}


// 获取用户信息
export async function queryUsers(body: Sys.QueryUsersReq, options?: { [key: string]: any }) {
  return request('/api/user/queryUsers', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
