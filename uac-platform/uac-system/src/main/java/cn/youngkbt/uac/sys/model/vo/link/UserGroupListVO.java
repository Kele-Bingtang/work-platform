package cn.youngkbt.uac.sys.model.vo.link;

import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2024/3/16 2:12
 * @note
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupListVO extends SysUserGroupVO {
    /**
     * UserGroupLink 表的主键
     */
    private Long linkId;
    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;
}
