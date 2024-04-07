package cn.youngkbt.uac.sys.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录
 * @TableName t_sys_opera_log
 */
@Data
@AutoMapper(target = SysOperaLogVO.class, reverseConvertGenerate = false)
public class SysOperaLogVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 日志 ID
     */
    private String operaId;

    /**
     * 业务类型（0 其它 1 新增 2 修改 3 删除）
     */
    private Integer businessType;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作类别（0 其它 1 后台用户 2 手机端用户）
     */
    private Integer operatorType;

    /**
     * 操作人员
     */
    private String operaName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求 URL
     */
    private String operaUrl;

    /**
     * 主机地址
     */
    private String operaIp;

    /**
     * 操作地点
     */
    private String operaLocation;

    /**
     * 请求参数
     */
    private String operaParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 状态（0 异常 1 正常 ）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 消耗时间
     */
    private Integer costTime;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 菜单 ID
     */
    private String menuId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}