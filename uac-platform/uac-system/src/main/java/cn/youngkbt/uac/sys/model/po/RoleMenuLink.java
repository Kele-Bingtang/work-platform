package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-19-12 00:19:27
 * @note 角色关联菜单
*/
@TableName("t_role_menu_link")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuLink extends BaseDO {
    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 菜单 ID
     */
    private String menuId;
    
    /**
     * 应用 ID
     */
    private String appId;

}