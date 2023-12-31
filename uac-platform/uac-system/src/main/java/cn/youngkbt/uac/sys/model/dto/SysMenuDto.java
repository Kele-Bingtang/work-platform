package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023-23-12 00:23:08
 * @note 菜单
*/
@Data
@AutoMapper(target = SysMenu.class)
public class SysMenuDto {
    /**
     * 主键
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    private String id;
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
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过 {max} 个字符")
    private String menuName;

    /**
     * 菜单地址
     */
    @Size(min = 0, max = 200, message = "路由地址不能超过 {max} 个字符")
    private String path;

    /**
     * 查询参数
     */
    private String param;

    /**
     * 图标
     */
    private String icon;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 组件路径
     */
    @Size(min = 0, max = 200, message = "组件路径不能超过 {max} 个字符")
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
     * 状态（0 异用 1 正常）
     */
    private Integer status;

}