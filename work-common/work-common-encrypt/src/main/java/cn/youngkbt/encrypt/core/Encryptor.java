package cn.youngkbt.encrypt.core;

import cn.youngkbt.encrypt.enumerate.AlgorithmType;
import cn.youngkbt.encrypt.enumerate.EncodeType;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:15:03
 * @note
 */
public interface Encryptor {
    /**
     * 获得当前算法
     */
    AlgorithmType algorithm();

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     * @return 加密后的字符串
     */
    String encrypt(String value, EncodeType encodeType);

    /**
     * 解密
     *
     * @param value      待加密字符串
     * @return 解密后的字符串
     */
    String decrypt(String value);
}
