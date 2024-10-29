package cn.youngkbt.integrate.system.model.bo;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemConfigBO {
    /**
     * 主键
     **/
    private Integer id;
    /**
     * 唯一标识符
     **/
    private String appId;
    /**
     * 来源系统名称
     **/
    private String sourceName;
    /**
     * 来源系统接口根地址
     **/
    private String sourceUrl;
    /**
     * 目标系统名称
     **/
    private String targetName;
    /**
     * 目标系统根地址
     **/
    private String targetUrl;
    /**
     * 调用 Source 系统请求方法：GET、POST 等
     **/
    private String sourceMethod;
    /**
     * 调用 Target 系统请求方法：GET、POST 等
     **/
    private String targetMethod;
    /**
     * 传给来源系统的参数 * 格式：[{"name": "", "type": "normal", "value": ""}, {"name": "", "type": "dynamic", "value": ""}] * type 有 normal 和 dynamic 两种 * normal：value 是字符串 * dynamic：value 是时间动态参数，如 -2 就是基于当前时间减 2 天，+2 或 2 基于当前时间加 2天
     */
    private String sourceReqParams;
    /**
     * 来源系统认证的参数
     **/
    private String sourceAuthParams;
    /**
     * 来源系统系统认证地址
     **/
    private String sourceAuthUrl;
    /**
     * 传给来源系统的参数 * 格式：[{"name": "", "type": "normal", "value": ""}, {"name": "", "type": "dynamic", "value": ""}] * type 有 normal 和 dynamic 两种 * normal：value 是字符串 * dynamic：value 是时间动态参数，如 -2 就是基于当前时间减 2 天，+2 或 2 基于当前时间加 2天
     */
    private String targetReqParams;
    /**
     * 目标系统认证的参数
     **/
    private String targetAuthParams;
    /**
     * 目标系统系统认证地址
     **/
    private String targetAuthUrl;
    /**
     * 从 Source 截取数据给 Target
     **/
    private String interceptField;
    /**
     * 发送消息人
     **/
    private String mailTo;
    /**
     * 消息发送类型：EM 邮件、SM 短信、OA WeLink
     **/
    private String mailType;
    /**
     * 系统状态
     **/
    private Integer status;
    /**
     * 来源系统查询范围参数，格式：xx - xxx，其中 xx 是开始时间，xxx 是结束时间
     **/
    private String rangeParams;
}