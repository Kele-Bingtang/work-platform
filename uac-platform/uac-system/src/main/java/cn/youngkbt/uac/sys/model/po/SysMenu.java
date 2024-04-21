package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.SysMenuVO;
import com.baomidou.mybatisplus.annotation.*;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Kele-Bingtang
 * @date 2023-23-12 00:23:08
 * @note 菜单
 */
@TableName("t_sys_menu")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysMenuVO.class, reverseConvertGenerate = false)
public class SysMenu extends BaseDO {
    /**
     * 菜单 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String menuId;

    /**
     * 父级菜单 ID
     */
    private String parentId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 路由参数
     */
    private String param;

    /**
     * 图标
     */
    private String icon;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 显示状态（0 隐藏 1 显示）
     */
    private Integer visible;

    /**
     * 是否缓存（0 不缓存 1 缓存）
     */
    private Integer isCache;

    /**
     * 是否为外链（0 否 1 是）
     */
    private Integer isFrame;

    /**
     * 菜单前端额外配置
     */
    private String meta;

    /**
     * 菜单介绍
     */
    private String intro;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

}