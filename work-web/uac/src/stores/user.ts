import { defineStore } from "pinia";
import type { UserInfo } from "./interface";
import { removeCacheToken, setCacheToken } from "@/utils/cache";
import { useRoutes } from "@/hooks/useRoutes";
import { resetRouter } from "@/router";
import { useLayoutStore } from "./layout";
import { getUserInfo, login, type Auth } from "@/api/auth";
import settings from "@/config/settings";

export const useUserStore = defineStore(
  "userStore",
  () => {
    const token = ref("");
    const userInfo = ref<UserInfo>({
      userId: "",
      tenantId: "",
      deptId: "",
      username: "",
      nickName: "",
      email: "",
      phone: "",
      sex: "",
      avatar: "",
      loginIp: "",
      loginDate: "",
      registerTime: "",
    });
    const roles = ref<string[]>([]);

    const tryLogin = async (loginBody: Auth.LoginBody) => {
      const res = await login(loginBody);
      const token = res.data.accessToken;
      setCacheToken(token);
      setToken(token);
      return true;
    };

    const tryLogout = async () => {
      if (token.value === "") throw Error("LogOut: token is undefined!");
      removeCacheToken();
      resetRouter();
      setToken("");
      setRoles([]);
      const layoutStore = useLayoutStore();
      layoutStore.removeAllTabs();
      layoutStore.setKeepAliveName();
    };

    const tryGetUserInfo = async () => {
      const res = await getUserInfo();
      const roles = res.data.roles || ["admin"];
      setRoles(roles);
      setUserInfo(res.data.user);
      return roles;
    };

    const resetToken = () => {
      removeCacheToken();
      setToken("");
      setRoles([]);
    };

    const changeRoles = async (rolesParam: string[]) => {
      // 模拟新的 token
      const token = rolesParam[0] + "-token";
      setToken(token);
      setCacheToken(token);
      setRoles(rolesParam); // 正常不是直接赋予角色，而是调用 this.getUserInfo(token)，根据 token 重新获取对应的角色
      // await this.getUserInfo(token);
      resetRouter();
      useRoutes().initDynamicRouters(rolesParam);
    };

    const setUserInfo = (userInfoParam: UserInfo) => {
      userInfo.value = userInfoParam;
    };

    const setToken = (tokenParam: string) => {
      token.value = tokenParam;
    };

    const setRoles = (rolesParam: string[]) => {
      roles.value = rolesParam;
    };

    return {
      token,
      userInfo,
      roles,

      tryLogin,
      tryLogout,
      tryGetUserInfo,
      resetToken,
      changeRoles,
      setUserInfo,
      setToken,
      setRoles,
    };
  },
  {
    persist: {
      key: settings.userCacheKey,
      storage: localStorage,
      paths: ["token"],
    },
  }
);
