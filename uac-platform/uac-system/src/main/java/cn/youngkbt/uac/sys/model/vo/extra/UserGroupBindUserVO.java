package cn.youngkbt.uac.sys.model.vo.extra;

import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Kele-Bingtang
 * @date 2024/3/13 1:30
 * @note
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserGroupBindUserVO extends SysUserGroupVO implements Serializable {

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
