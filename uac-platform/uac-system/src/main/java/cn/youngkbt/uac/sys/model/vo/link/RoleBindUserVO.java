package cn.youngkbt.uac.sys.model.vo.link;

import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Kele-Bingtang
 * @date 2024/3/14 1:38
 * @note
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleBindUserVO extends SysRoleVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 是否禁用，给前端穿梭框使用
     */
    private Boolean disabled;
}