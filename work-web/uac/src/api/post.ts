export namespace Post {
  export interface PostInfo {
    id: number; // 主键
    postId: string; // 岗位 ID
    postCode: string; // 岗位编码
    postName: string; // 岗位名称
    orderNum: string; // 显示顺序
    intro: string; // 岗位介绍
  }
}
