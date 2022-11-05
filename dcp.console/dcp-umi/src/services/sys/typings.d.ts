declare namespace Sys {
  // 请求响应
  type LoginReq = {
    userId?: string;
    password?: string;
    autoLogin?: boolean;
  };

  type QueryUsersReq = {
    userId?: string;
    fullName?: string;
    email?: string;
    mobile?: string;
    status?: string;
    departmentCode?: string;
    createType?: string;
    updateBy?: string;
    createBy?: string;
  };


  type User = {
    userId?: string;
    fullName?: string;
    password?: string;
    email?: string;
    mobile?: string;
    createType?: string;
    departmentCode?: string;
    avatar?: string;
    status?: string;
    createBy?: string;
    updateBy?: string;
    remark?: string;
    createTime?: date;
    updateTime?: date;
  };

  type NoticeIconItemType = 'notification' | 'message' | 'event';

  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
}
