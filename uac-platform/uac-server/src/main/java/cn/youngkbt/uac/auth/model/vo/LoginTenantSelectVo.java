package cn.youngkbt.uac.auth.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/1/16 0:34
 * @note
 */
@Data
@Builder
public class LoginTenantSelectVo {
    private Boolean tenantEnabled;
    private List<TenantSelectVo> tenantSelectList;
}