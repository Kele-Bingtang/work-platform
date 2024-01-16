package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.RoleGroupLinkVo;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-18-12 00:18:20
 * @note 角色关联角色组
*/
@TableName("t_role_group_link")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = RoleGroupLinkVo.class, reverseConvertGenerate = false)
public class RoleGroupLink extends BaseDO {

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 应用 ID
     */
    private String appId;

}