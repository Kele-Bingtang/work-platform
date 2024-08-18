import http from "@/config/request";

const baseUri = "/download";

export const download = (appId: string, fileKey: string) => {
  return http.post<any>(`${baseUri}/${appId}/${fileKey}`, {}, { responseType: "blob" });
};
