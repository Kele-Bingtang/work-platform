import http from "@/config/request";

export namespace App {
  export interface AppTree {
    appId: string; // 应用 ID
    appName: string; // 应用名
  }
}

const baseUri = "/system/app";

export const getAppTreeList = () => {
  return http.get<App.AppTree>(`${baseUri}/appTreeList`);
};

export const addOneApp = () => {};

export const editOneApp = () => {};

export const removeOneApp = () => {};
