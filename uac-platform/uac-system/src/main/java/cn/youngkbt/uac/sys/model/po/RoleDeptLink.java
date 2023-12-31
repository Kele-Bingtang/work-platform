package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-44-11 23:44:15
 * @note 角色关联部门
*/
@TableName("t_role_dept_link")
@Data
@EqualsAndHashCode(callSuper = true)
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
     * 应用 ID
     */
    private String appId;

}