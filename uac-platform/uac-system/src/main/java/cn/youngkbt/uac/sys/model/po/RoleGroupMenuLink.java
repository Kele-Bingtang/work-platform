package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-19-12 00:19:04
 * @note 角色组关联菜单
*/
@TableName("t_role_group_menu_link")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleGroupMenuLink extends BaseDO {
    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 菜单 ID
     */
    private String menuId;

    /**
     * 应用 ID
     */
    private String appId;

}