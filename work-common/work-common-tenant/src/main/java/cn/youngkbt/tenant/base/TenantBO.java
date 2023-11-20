package cn.youngkbt.tenant.base;

import cn.youngkbt.mp.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023/11/19 23:35
 * @note
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantBO extends BaseDO {
    /**
     * 租户编号
     */
    private String tenantId;
}
