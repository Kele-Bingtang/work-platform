export namespace Role {
  export interface RoleInfo {
    id: number; // 主键
    roleId: string; // 角色 ID
    roleCode: string; // 角色码
    roleName: string; // 角色名
    orderNum: string; // 显示顺序
    intro: string; // 岗位介绍
    appId: string; // 应用 ID
  }
}
