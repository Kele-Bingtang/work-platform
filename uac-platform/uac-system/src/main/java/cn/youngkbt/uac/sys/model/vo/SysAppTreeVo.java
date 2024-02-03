package cn.youngkbt.uac.sys.model.vo;

import cn.youngkbt.uac.sys.model.po.SysApp;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Kele-Bingtang
 * @date 2024/1/29 0:04
 * @note
 */
@Data
@AutoMapper(target = SysApp.class, convertGenerate = false)
public class SysAppTreeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 应用名
     */
    private String appName;

}
