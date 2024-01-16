package cn.youngkbt.uac.auth.model.vo;

import cn.youngkbt.uac.sys.model.vo.SysTenantVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/1/16 1:37
 * @note
 */
@Data
@AutoMapper(target = SysTenantVo.class)
public class TenantSelectVo {
    private String tenantId;
    private String tenantName;
    private String domain;
}
