package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024-04-23 01:04:49
 * @note 用户
*/
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_user")
@Data
public class User extends BaseDO {

    /**
     * 用户 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
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
    private LocalDateTime registerTime;


    /**
     * 最后登录 IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;
}