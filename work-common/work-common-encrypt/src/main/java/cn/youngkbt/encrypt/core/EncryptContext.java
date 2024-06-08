package cn.youngkbt.encrypt.core;

import cn.youngkbt.encrypt.enumerate.AlgorithmType;
import cn.youngkbt.encrypt.enumerate.EncodeType;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:26:01
 * @note 加密上下文，用于 encryptor 传递必要的参数
 */
@Data
public class EncryptContext {
    /**
     * 默认算法
     */
    private AlgorithmType algorithm;

    /**
     * 安全秘钥
     */
    private String password;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 编码方式，base64/hex
     */
    private EncodeType encode;
}
