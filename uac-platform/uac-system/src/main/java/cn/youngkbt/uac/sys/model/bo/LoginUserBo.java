package cn.youngkbt.uac.sys.model.bo;

import cn.youngkbt.mp.base.SysUserBO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2024/1/21 23:10
 * @note
 */
@Data
@AutoMapper(target = SysUserBO.class)
public class LoginUserBo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0 未知 1 男 2 女）
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 注册时间
     */
    private Date registerTime;
}
