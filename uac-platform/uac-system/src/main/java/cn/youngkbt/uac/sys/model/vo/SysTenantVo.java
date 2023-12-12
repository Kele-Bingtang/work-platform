package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023-26-12 00:26:16
 * @note 租户表
*/
@Data
public class SysTenantVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Long id;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 统一社会信用代码
     */
    private String licenseNumber;

    /**
     * 企业名
     */
    private String tenantName;

    /**
     * 企业图标
     */
    private String icon;

    /**
     * 企业所在地
     */
    private String address;

    /**
     * 企业用户数量（-1 不限制）
     */
    private Integer userCountCapacity;

    /**
     * 用户实际数量
     */
    private Integer userCount;

    /**
     * 企业创始人
     */
    private String founder;

    /**
     * 企业介绍
     */
    private String intro;

    /**
     * 联系人
     */
    private String contactUserName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 企业域名
     */
    private String domain;

    /**
     * 父级租户 ID
     */
    private String parentTenantId;

    /**
     * 租户过期时间（-1 无限期）
     */
    private Date expireTime;
}