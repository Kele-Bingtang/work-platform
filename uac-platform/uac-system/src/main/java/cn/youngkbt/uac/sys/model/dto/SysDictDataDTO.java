package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysDictData;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023-21-12 00:21:40
 * @note 字典数据
*/
@Data
@AutoMapper(target = SysDictData.class, reverseConvertGenerate = false)
public class SysDictDataDTO {
    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    public Long id;
    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过 {max} 个字符")
    private String dictLabel;

    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过 {max} 个字符")
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
    @NotBlank(message = "字典编码不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过 {max} 个字符")
    private String dictCode;
    
}