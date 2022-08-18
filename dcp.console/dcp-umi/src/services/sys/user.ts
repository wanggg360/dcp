import { request } from 'umi';

const serverUrl = BACKEND_SERVER_URL

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

export async function logout(options?: { [key: string]: any }) {
  return request('/api/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}
