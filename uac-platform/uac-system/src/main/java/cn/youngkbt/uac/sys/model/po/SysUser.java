package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023-27-12 00:27:03
 * @note 用户信息
 */
@TableName("t_sys_user")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUserVO.class, reverseConvertGenerate = false)
public class SysUser extends BaseDO {
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
     * 租户编号
     */
    private String tenantId;

    /**
     * 部门 ID
     */
    private String deptId;
    
}