package cn.youngkbt.encrypt.enumerate;

import cn.youngkbt.encrypt.encryptor.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:04:47
 * @note
 */
@Getter
@AllArgsConstructor
public enum AlgorithmType {
    /**
     * 默认走yml配置
     */
    DEFAULT(null),

    /**
     * base64
     */
    BASE64(Base64Encryptor.class),

    /**
     * aes
     */
    AES(AesEncryptor.class),

    /**
     * rsa
     */
    RSA(RsaEncryptor.class),

    /**
     * sm2
     */
    SM2(Sm2Encryptor.class),

    /**
     * sm4
     */
    SM4(Sm4Encryptor.class);

    private final Class<? extends AbstractEncryptor> clazz;

}
