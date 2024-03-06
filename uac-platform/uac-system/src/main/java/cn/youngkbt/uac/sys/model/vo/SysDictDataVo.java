package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2023-21-12 00:21:40
 * @note 字典数据
*/
@Data
public class SysDictDataVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Long id;
    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * tag 标签：el-tag | el-check-tag
     */
    private String tagEl;

    /**
     * tag 类型：primary | success | info | warning | danger
     */
    private String tagType;

    /**
     * tag 主题：dark | light | plain
     */
    private String tagEffect;

    /**
     * tag 其他属性
     */
    private String tagAttributes;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;

    /**
     * 父级字典编码
     */
    private String parentDictCode;

    /**
     * 父级字典键值
     */
    private String parentCodeValue;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}