import http from "@/config/request";

const baseUri = "/download";

export const download = (appId: string, fileKey: string) => {
  return http.post(`${baseUri}/${appId}/${fileKey}`);
};
