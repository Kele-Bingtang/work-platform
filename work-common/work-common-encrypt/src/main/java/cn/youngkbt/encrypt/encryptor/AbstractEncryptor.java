package cn.youngkbt.encrypt.encryptor;

import cn.youngkbt.encrypt.core.EncryptContext;
import cn.youngkbt.encrypt.core.Encryptor;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 22:41:33
 * @note
 */
public abstract class AbstractEncryptor implements Encryptor {

    public AbstractEncryptor(EncryptContext context) {
        // 用户配置校验与配置注入
    }
}
