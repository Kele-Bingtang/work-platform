package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 19:35
 * @note
 */
@Data
public class SysUserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别（0 保密 1 男 2 女）
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态（0 离线 1 在线）
     */
    private Integer userStatus;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 最后登录 IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 部门 ID
     */
    private String deptId;
}
