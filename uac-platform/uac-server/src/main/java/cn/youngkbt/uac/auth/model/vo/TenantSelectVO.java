package cn.youngkbt.uac.auth.model.vo;

import cn.youngkbt.uac.system.model.vo.SysTenantVO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/1/16 1:37
 * @note 租户下拉选择列表
 */
@Data
@AutoMapper(target = SysTenantVO.class)
public class TenantSelectVO {
    private String tenantId;
    private String tenantName;
    private String domain;
}
