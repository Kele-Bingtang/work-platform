package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysClient;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 22:55
 * @note
 */
@Data
@AutoMapper(target = SysClient.class)
public class SysClientDto {

    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    public Long id;

    /**
     * 客户端 ID
     */
    private String clientId;

    /**
     * 客户端 Key
     */
    @NotBlank(message = "客户端 key 不能为空", groups = { RestGroup.AddGroup.class, RestGroup.EditGroup.class })
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
    @NotBlank(message = "授权类型不能为空", groups = { RestGroup.AddGroup.class, RestGroup.EditGroup.class })
    private String grantTypes;

    /**
     * token 最低活跃频率时间，超出则 token 失效（-1 不限制，单位秒），isolate_auth 为 1 生效
     */
    private Long activeTimeout;

    /**
     * token 有效期，超出则 token 失效，默认 30 天（单位秒），isolate_auth 为 1 生效
     */
    private Long timeout;

    /**
     * 状态（0 异用 1 正常）
     */
    private Integer status;
}
