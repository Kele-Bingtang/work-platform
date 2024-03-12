package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-22-12 00:22:14
 * @note 字典类型
*/
@TableName("t_sys_dict_type")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDictTypeVO.class, reverseConvertGenerate = false)
public class SysDictType extends BaseDO {
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
     * 租户编号
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

}