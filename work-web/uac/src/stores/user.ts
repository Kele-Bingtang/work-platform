import { defineStore } from "pinia";
import type { UserInfo } from "./interface";
import { useRoutes } from "@/hooks/useRoutes";
import { resetRouter } from "@/router";
import { useLayoutStore } from "./layout";
import { getUserInfo, login, logout, type Auth } from "@/api/auth";

export const useUserStore = defineStore(
  "userStore",
  () => {
    const token = ref("");
    const userInfo = ref<UserInfo>({
      id: 0,
      userId: "",
      tenantId: "",
      deptId: "",
      username: "",
      nickname: "",
      email: "",
      phone: "",
      sex: 0,
      avatar: "",
      loginIp: "",
      loginTime: "",
      registerTime: "",
    });
    const roles = ref<string[]>([]);

    const tryLogin = async (loginBody: Auth.LoginBody) => {
      const res = await login(loginBody);
      const token = res.data.accessToken;
      setToken(token);
      return true;
    };

    const tryLogout = async () => {
      if (unref(token) === "") throw Error("LogOut: token is undefined!");
      await logout();
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
      setToken("");
      setRoles([]);
    };

    const changeRoles = async (rolesParam: string[]) => {
      // 模拟新的 token
      const token = rolesParam[0] + "-token";
      setToken(token);
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
      paths: ["token"],
    },
  }
);
