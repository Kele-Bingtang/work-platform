package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.RoleDeptLinkVO;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023-44-11 23:44:15
 * @note 角色关联部门
*/
@TableName("t_role_dept_link")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RoleDeptLinkVO.class, reverseConvertGenerate = false)
public class RoleDeptLink extends BaseDO {

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

}