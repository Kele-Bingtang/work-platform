import CryptoJS from "crypto-js";
import { randomString } from "./idGenerator";
import JSEncrypt from "jsencrypt";

/**
 * 随机生成 AES 密钥
 * @param length 长度
 */
export const generateAesKey = () => {
  return CryptoJS.enc.Utf8.parse(randomString(32));
};

/**
 * 加密 base64
 */
export const encryptBase64 = (word: CryptoJS.lib.WordArray) => {
  return CryptoJS.enc.Base64.stringify(word);
};

/**
 * 解密 base64
 */
export const decryptBase64 = (word: string) => {
  return CryptoJS.enc.Base64.parse(word);
};

/**
 * 使用密钥对数据进行加密
 * @param word 加密内容
 * @param aesKey 密钥
 */
export const encryptAes = (word: string, aesKey: CryptoJS.lib.WordArray) => {
  const encrypted = CryptoJS.AES.encrypt(word, aesKey, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  });
  return encrypted.toString();
};

/**
 * 使用密钥对数据进行解密
 * @param word 加密内容
 * @param aesKey 密钥
 */
export const decryptAes = (word: string, aesKey: CryptoJS.lib.WordArray) => {
  const decrypted = CryptoJS.AES.decrypt(word, aesKey, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  });
  return decrypted.toString(CryptoJS.enc.Utf8);
};

/**
 * RAS 加密
 * @param word 加密内容
 * @param publicKey 公钥
 */
export const encryptRas = (word: string, publicKey: string) => {
  const encrypt = new JSEncrypt();
  encrypt.setPublicKey(publicKey); // 设置公钥
  return encrypt.encrypt(word); // 对数据进行加密
};

/**
 * RAS 解密
 * @param word 加密内容
 * @param publicKey 私钥
 */
export const decryptRas = (word: string, privateKey: string) => {
  const encrypt = new JSEncrypt();
  encrypt.setPrivateKey(privateKey); // 设置私钥
  return encrypt.decrypt(word); // 对数据进行解密
};
