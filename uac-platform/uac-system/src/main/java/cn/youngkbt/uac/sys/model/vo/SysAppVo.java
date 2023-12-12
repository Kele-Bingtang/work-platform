package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-20-12 00:20:10
 * @note 应用信息
 */
@Data
public class SysAppVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Long id;
    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 应用码
     */
    private String appCode;

    /**
     * 应用名
     */
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
     * 授权类型
     */
    private List<String> grantTypeList;

}