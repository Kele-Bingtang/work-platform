package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.UserGroupLinkVo;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-30-12 00:30:03
 * @note 用户关联用户组
*/
@TableName("t_user_group_link")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserGroupLinkVo.class, reverseConvertGenerate = false)
public class UserGroupLink extends BaseDO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户组 UID
     */
    private String userGroupId;

    /**
     * 租户编号
     */
    private String tenantId;

}