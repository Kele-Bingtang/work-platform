package cn.youngkbt.uac.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.system.model.vo.RoleMenuLinkVO;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Kele-Bingtang
 * @date 2023-19-12 00:19:27
 * @note 角色关联菜单
*/
@TableName("t_role_menu_link")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RoleMenuLinkVO.class, reverseConvertGenerate = false)
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
     * 租户编号
     */
    private String tenantId;
    
    /**
     * 应用 ID
     */
    private String appId;

}