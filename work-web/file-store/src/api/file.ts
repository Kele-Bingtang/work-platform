import http from "@/config/request";

export declare namespace File {
  interface FileInfo {
    appId: string; // 应用 ID
    appModule: string; // 应用系统模块
    fileKey: string; // 附件唯一标识
    fileName: string; // 源附件名称
    filePath: string; // 附件存储路径
    fileType: string; // 附件类型
    fileSize: string; // 附件大小（KB）
    expireTime: number; // 失效时间
    createTime: string; // 创建时间
  }
}

const baseUri = "/file";

export const listAppModule = (appId: string) => {
  return http.get<http.Response<File.FileInfo[]>>(`${baseUri}/listAppModule/${appId}`);
};

export const listFilePage = (params: Partial<File.FileInfo>) => {
  return http.get<http.Response<File.FileInfo[]>>(`${baseUri}/listPage`, params);
};

export const addFile = (data: Partial<File.FileInfo>) => {
  return http.post<http.Response<Boolean>>(baseUri, data);
};

export const editFile = (data: File.FileInfo) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const removeFile = (appId: string, fileKey: string) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/${appId}/${fileKey}`);
};

export const removeBatchFile = (appId: string, fileKeyList: string[]) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/batch/${appId}`, {}, { data: fileKeyList });
};
