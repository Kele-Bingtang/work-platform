package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-31-12 00:31:01
 * @note 用户关联岗位
*/
@TableName("t_user_post_link")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPostLink extends BaseDO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 岗位 ID
     */
    private String postId;

    /**
     * 租户编号
     */
    private String tenantId;

}