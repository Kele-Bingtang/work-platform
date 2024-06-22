package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2024-06-23 01:07:11
 * @note 用户项目关联表
*/
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_user_project_link")
@Data
public class UserProjectLink extends BaseDO {
    /**
     * 用户名
     */
    private String userId;

    /**
     * 项目 ID
     */
    private Long projectId;

    /**
     * 项目角色（1 管理员 2 普通成员 3 只读成员 4 禁止访问）
     */
    private Integer projectRole;

    /**
     * 0 项目创建者 1 项目加入者
     */
    private Integer belongType;

    /**
     * 团队 ID
     */
    private String teamId;
}