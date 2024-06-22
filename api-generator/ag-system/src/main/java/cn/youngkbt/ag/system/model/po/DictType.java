package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.ag.system.model.vo.DictTypeVO;
import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Kele-Bingtang
 * @date 2023-12-12 00:22:14
 * @note 字典类型
*/
@TableName("t_dict_type")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = DictTypeVO.class, reverseConvertGenerate = false)
public class DictType extends BaseDO {
    /**
     * 字典 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
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
     * 是否开启级联（0 不开启，1 开启）
     */
    private Integer isCascade;

    /**
     * 介绍
     */
    private String description;

}