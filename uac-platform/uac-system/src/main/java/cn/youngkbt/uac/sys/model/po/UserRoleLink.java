package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.UserRoleLinkVO;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-31-12 00:31:33
 * @note 用户关联角色
*/
@TableName("t_user_role_link")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserRoleLinkVO.class, reverseConvertGenerate = false)
public class UserRoleLink extends BaseDO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 应用 ID
     */
    private String appId;

}