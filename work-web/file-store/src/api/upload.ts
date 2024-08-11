import http from "@/config/request";

export declare namespace Upload {
  interface UploadInfo {
    fileKey: string; // 附件唯一标识
    fileName: string; // 源附件名称
    expireTime: string; // 失效时间
  }
  interface FileInfo {
    file: any; // 文件
    appId: string; // 应用系统标识
    appModule: string; // 应用系统模块
    fileName: string; // 源附件名称
    uploadUserId: string; // 上传用户 ID
    uploadUserName: string; // 上传用户名
    expireTime: number; // 失效时间
  }
}

const baseUri = "/upload";

export const upload = (uploadFileInfo: Partial<Upload.FileInfo>) => {
  const formData = new FormData();
  formData.append("file", uploadFileInfo.file);
  uploadFileInfo.appId && formData.append("appId", uploadFileInfo.appId);
  uploadFileInfo.appModule && formData.append("appModule", uploadFileInfo.appModule);
  uploadFileInfo.fileName && formData.append("fileName", uploadFileInfo.fileName);
  uploadFileInfo.uploadUserId && formData.append("uploadUserId", uploadFileInfo.uploadUserId);
  uploadFileInfo.uploadUserName && formData.append("uploadUserName", uploadFileInfo.uploadUserName);

  return http.post<http.Response<Upload.UploadInfo[]>>(`${baseUri}`, formData, {
    params: {
      _type: "file",
    },
  });
};
