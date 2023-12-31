package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysApp;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023-20-12 00:20:10
 * @note 应用信息
 */
@Data
@Builder
@AutoMapper(target = SysApp.class)
public class SysAppDto {
    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    public Long id;
    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 应用码
     */
    @NotBlank(message = "应用码不能为空", groups = {RestGroup.AddGroup.class})
    private String appCode;

    /**
     * 应用名
     */
    @NotBlank(message = "应用名不能为空", groups = {RestGroup.AddGroup.class})
    private String appName;

    /**
     * 应用介绍
     */
    private String intro;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 客户端 ID
     */
    @NotBlank(message = "客户端 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String clientId;

    /**
     * 状态（0 异用 1 正常）
     */
    private Integer status;

}