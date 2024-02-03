package cn.youngkbt.uac.auth.model.vo;

import cn.youngkbt.security.domain.LoginUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author Kele-Bingtang
 * @date 2024/1/16 23:35
 * @note
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 用户基本信息
     */
    private LoginUser user;
    /**
     * 菜单权限
     */
    private Set<String> permissions;

    /**
     * 角色权限
     */
    private Set<String> roles;
}
