package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2023-22-12 00:22:14
 * @note 字典类型
*/
@Data
public class SysDictTypeVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Long id;
    /**
     * 字典主键
     */
    private String dictId;
    /**
     * 字典类型
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}