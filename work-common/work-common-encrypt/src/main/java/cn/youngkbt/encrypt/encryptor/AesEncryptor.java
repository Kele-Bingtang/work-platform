package cn.youngkbt.encrypt.encryptor;

import cn.youngkbt.encrypt.core.EncryptContext;
import cn.youngkbt.encrypt.enumerate.AlgorithmType;
import cn.youngkbt.encrypt.enumerate.EncodeType;
import cn.youngkbt.encrypt.helper.EncryptHelper;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:25:23
 * @note AES 算法实现
 */
public class AesEncryptor extends AbstractEncryptor {

    private final EncryptContext context;

    public AesEncryptor(EncryptContext context) {
        super(context);
        this.context = context;
    }

    /**
     * 获得当前算法
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.AES;
    }

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     */
    @Override
    public String encrypt(String value, EncodeType encodeType) {
        if (encodeType == EncodeType.HEX) {
            return EncryptHelper.encryptByAesHex(value, context.getPassword());
        } else {
            return EncryptHelper.encryptByAes(value, context.getPassword());
        }
    }

    /**
     * 解密
     *
     * @param value 待加密字符串
     */
    @Override
    public String decrypt(String value) {
        return EncryptHelper.decryptByAes(value, context.getPassword());
    }
}
