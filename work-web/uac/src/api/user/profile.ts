import http from "@/config/request";

export namespace Profile {
  export interface ProfileInfo {
    nickname: string;
    phone: string;
    email: string;
    sex: number;
  }

  export interface ProfilePassword {
    oldPassword: string;
    newPassword: string;
    confirmPassword: string;
  }
}

const baseUri = "/system/user/profile";

export const editOne = (data: Profile.ProfileInfo) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const updatePassword = (data: Profile.ProfilePassword) => {
  return http.put<http.Response<boolean>>(`${baseUri}/updatePassword`, data);
};