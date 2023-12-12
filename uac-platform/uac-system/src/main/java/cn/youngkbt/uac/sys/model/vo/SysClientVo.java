package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 22:49
 * @note
 */
@Data
public class SysClientVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 客户端 Key
     */
    private String clientKey;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 授权类型
     */
    private List<String> grantTypeList;

    /**
     * token 最低活跃频率时间，超出则 token 失效（-1 不限制，单位秒），isolate_auth 为 1 生效
     */
    private Long activeTimeout;

    /**
     * token 有效期，超出则 token 失效，默认 30 天（单位秒），isolate_auth 为 1 生效
     */
    private Long timeout;

    private String status;
}
