package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Kele-Bingtang
 * @date 2023-21-12 00:21:11
 * @note 部门信息
*/
@Data
public class SysDeptVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 父级部门 ID
     */
    private String parentId;

    /**
     * 父级部门名字
     */
    private String parentName;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 部门图标
     */
    private String icon;

    /**
     * 部门显示顺序
     */
    private Integer orderNum;

    /**
     * 部门用户数量
     */
    private Integer userCount;

    /**
     * 部门负责人
     */
    private String leader;

    /**
     * 负责电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门介绍
     */
    private String intro;

    /**
     * 部门层级
     */
    private Integer level;

}