package cn.youngkbt.encrypt.encryptor;

import cn.youngkbt.encrypt.core.EncryptContext;
import cn.youngkbt.encrypt.enumerate.AlgorithmType;
import cn.youngkbt.encrypt.enumerate.EncodeType;
import cn.youngkbt.encrypt.helper.EncryptHelper;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:14:44
 * @note Base64 算法实现
 */
public class Base64Encryptor extends AbstractEncryptor {
    
    public Base64Encryptor(EncryptContext context) {
        super(context);
    }

    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.BASE64;
    }

    @Override
    public String encrypt(String value, EncodeType encodeType) {
        return EncryptHelper.encryptByBase64(value);
    }

    @Override
    public String decrypt(String value) {
        return EncryptHelper.decryptByBase64(value);
    }
}
