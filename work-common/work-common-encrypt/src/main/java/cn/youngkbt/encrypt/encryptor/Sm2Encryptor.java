package cn.youngkbt.encrypt.encryptor;

import cn.youngkbt.encrypt.core.EncryptContext;
import cn.youngkbt.encrypt.enumerate.AlgorithmType;
import cn.youngkbt.encrypt.enumerate.EncodeType;
import cn.youngkbt.encrypt.helper.EncryptHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:37:26
 * @note SM2 算法
 */
public class Sm2Encryptor extends AbstractEncryptor {


    private final EncryptContext context;

    public Sm2Encryptor(EncryptContext context) {
        super(context);
        String privateKey = context.getPrivateKey();
        String publicKey = context.getPublicKey();
        if (StringUtils.isAnyEmpty(privateKey, publicKey)) {
            throw new IllegalArgumentException("SM2公私钥均需要提供，公钥加密，私钥解密。");
        }
        this.context = context;
    }

    /**
     * 获得当前算法
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM2;
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
            return EncryptHelper.encryptBySm2Hex(value, context.getPublicKey());
        } else {
            return EncryptHelper.encryptBySm2(value, context.getPublicKey());
        }
    }

    /**
     * 解密
     *
     * @param value 待加密字符串
     */
    @Override
    public String decrypt(String value) {
        return EncryptHelper.decryptBySm2(value, context.getPrivateKey());
    }
}
