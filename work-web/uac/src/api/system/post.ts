import http from "@/config/request";

export namespace Post {
  export interface PostInfo {
    id: number; // 主键
    postId: string; // 岗位 ID
    postCode: string; // 岗位编码
    postName: string; // 岗位名称
    orderNum: number; // 显示顺序
    intro: string; // 岗位介绍
    status: number; // 状态
    createTime: string; // 创建时间
  }

  export interface UserSelectPost {
    postIds: string[];
    postList: PostInfo[];
  }
}

const baseUri = "/system/post";

export const list = () => {
  return http.get<http.Response<Post.PostInfo[]>>(`${baseUri}/list`);
};

export const listPage = () => {
  return http.get<http.Page<Post.PostInfo[]>>(`${baseUri}/listPage`);
};

/**
 * 查询岗位列表和已选择的岗位列表
 */
export const userSelectPostList = (userId: string) => {
  return http.get<http.Response<Post.UserSelectPost[]>>(`${baseUri}/userSelectPostList/${userId}`);
};

export const addOne = (data: Post.PostInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Post.PostInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Post.PostInfo) => {
  return http.delete<http.Response<string>>(
    `${baseUri}/${data.id}`,
    {},
    {
      data: [data.postId],
    }
  );
};

export const deleteBatch = ({ idList, dataList }: { idList: string[]; dataList: Post.PostInfo[] }) => {
  return http.delete<http.Response<string>>(
    `${baseUri}/${idList.join(",")}`,
    {},
    {
      data: dataList.map(item => item.postId),
    }
  );
};
