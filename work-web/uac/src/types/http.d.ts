declare namespace http {
  interface Response<T> {
    code: number; // 状态码
    status: string; // 状态码信息
    message: string; // 消息
    data: T; // 数据
  }

  interface Page<T> {
    code: number; // 状态码
    status: string; // 状态码信息
    message: string; // 消息
    data: PageData<T>;
  }

  interface PageData<T> {
    pageNum: number; // 页码
    pageSize: number; // 每页数量
    total: number; // 总记录数
    list: T; // 数据
  }
}
