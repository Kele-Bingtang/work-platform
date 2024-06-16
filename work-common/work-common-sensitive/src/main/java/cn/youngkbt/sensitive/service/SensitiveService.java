package cn.youngkbt.sensitive.service;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 01:29:43
 * @note 自定义敏感服务
 */
public interface SensitiveService {
    /**
     * 是否脱敏
     */
    boolean isSensitive(String roleKey, String perms);
}
