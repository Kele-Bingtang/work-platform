package cn.youngkbt.uac.sys.model.vo.link;

import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2024/3/16 2:16
 * @note
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleListVO extends SysRoleVO {
    /**
     * UserRoleLink 表的主键
     */
    private String linkId;
    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;
}
