package cn.youngkbt.uac.sys.model.vo;

import cn.youngkbt.uac.sys.model.bo.TreeBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2023-23-12 00:23:08
 * @note 菜单
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuVO extends TreeBO<SysMenuVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 菜单 ID
     */
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
     * 菜单地址前缀
     */
    private String pathPrefix;

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
     * 应用 ID
     */
    private String appId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}