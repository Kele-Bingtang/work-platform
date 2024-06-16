package cn.youngkbt.encrypt.encryptor;

import cn.youngkbt.encrypt.core.EncryptContext;
import cn.youngkbt.encrypt.enumerate.AlgorithmType;
import cn.youngkbt.encrypt.enumerate.EncodeType;
import cn.youngkbt.encrypt.helper.EncryptHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:36:47
 * @note RSA 算法
 */
public class RsaEncryptor extends AbstractEncryptor {

    private final EncryptContext context;

    public RsaEncryptor(EncryptContext context) {
        super(context);
        String privateKey = context.getPrivateKey();
        String publicKey = context.getPublicKey();
        if (StringUtils.isAnyEmpty(privateKey, publicKey)) {
            throw new IllegalArgumentException("RSA公私钥均需要提供，公钥加密，私钥解密。");
        }
        this.context = context;
    }

    /**
     * 获得当前算法
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.RSA;
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
            return EncryptHelper.encryptByRsaHex(value, context.getPublicKey());
        } else {
            return EncryptHelper.encryptByRsa(value, context.getPublicKey());
        }
    }

    /**
     * 解密
     *
     * @param value 待加密字符串
     */
    @Override
    public String decrypt(String value) {
        return EncryptHelper.decryptByRsa(value, context.getPrivateKey());
    }
}
