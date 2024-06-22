package cn.youngkbt.uac.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.system.model.vo.UserRoleLinkVO;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023-31-12 00:31:33
 * @note 用户关联角色
 */
@TableName("t_user_role_link")
@Data
@Accessors(chain = true)
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