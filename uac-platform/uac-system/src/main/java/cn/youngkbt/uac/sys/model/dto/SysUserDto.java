package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 19:35
 * @note
 */
@Data
@AutoMapper(target = SysUser.class)
public class SysUserDto {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过 {max} 个字符")
    private String username;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过 {max} 个字符")
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过 {max} 个字符")
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

    /**
     * 状态（0 异用 1 正常）
     */
    private Integer status;
}
