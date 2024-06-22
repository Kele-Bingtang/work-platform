export * from "./symbol";

export const HOME_URL = "/home";
export const HOME_NAME = "Home";
export const LOGIN_URL = "/login";
export const LOGIN_NAME = "Login";
export const LAYOUT_NAME = "Layout";
export const REDIRECT_NAME = "Redirect";
export const NOT_FOUND = "NotFound";

export const UAC_PREFIX = "uac";
export const uacAppSecret = "1000000000000000000";
export const uacAppGrantType = "password";

export const AG_PREFIX = "ag";
export const httpPrefix = "http://";
export const httpsPrefix = "https://";

export const baseEnum = [
  { label: "是", value: 1 },
  { label: "否", value: 0 },
];

export const uacPublicKey =
  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDehF5HmeATamXZ4GmEDxBcNjbrX8n2vYrN52G2wYpmNX6FgdbtVkIEphRT2u+xe9wqfjQFS/qoiLs2juk9XOfDReQ+Bot0bXuFEUknLSFFfaBm2OsAuTPEKolM1SXNCe8kVWYACgSG/+nsfgeQ4PEKZwWOl0xLvXV3NbTKhr6nbwIDAQAB";

// 前端不建议存放私钥 不建议解密数据 因为都是透明的意义不大
export const uacPrivateKey =
  "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKtpzNmnT83pjv2dkeWyCKXvXZ+GLBC0itv3qPBMD9dwQIrcRUQn9YdeQ79M3cPhoaNCd8WMkW+iFQTeTSpz37kM7E4CIbw9ELVUFoYNUGjjJmNbbOBMMeIrjZkNB5gi3Q+UYzqZGzS2x2L8vhKk49JdcEnJFM9LQIiHoPqvLn2XAgMBAAECgYBqK03GQescDt2SGFaVfn/8xxWmCOSiNDENrYflzjbBqbVQTL5aSKW7ApfTlTiGaupCxJiKs1YhXrLGISqKcNrQL7KEDyZeF4tDBCkzLsAciFdEQMHAgexoGNRLO5XI2FUTGPiyDWrRT2qHJSn67Oc25wiYtVqrTvJ3o896krOXgQJBANX/P7KawliWKXUSqYcOJJNqk7fFuknlm/LyZTXzHXf1L0vRx6BxvYDYAKcCJEr9rluJ/ZwzlqV1vbWcaE/QPl0CQQDNDtX+iM9Stenrc1EfUDjBOsXm0Z9Gh775KKXdiBcVrZS3fbPqp9vzqmxtcKd4qXjHrf5J0ZOazq+jDSi1p6SDAkBrsUGIC3PkiuUgIp0n90kW2tqt8Ba425EoqS2gAdIBrWT43gB9UOASOCHT6jMDfjjBnmb/tcKJUACBpc2k5OKNAkA/F0V4HxfK1vW137Eh9UjBCxhAwFZFGRcFCiCeXHTd0p5jJmXo2OSvv8vEkFi2o03qAfqvM7A6fUVE4ZOY8I+JAkBGW8H52YLh2WCnkXoxnqEg1AJZ8/RND5mnkyHxcUcD0+il1q9O2MXPZ5bSs8EXXXWx4gyXLjMd97NvSoDVOEaG";
