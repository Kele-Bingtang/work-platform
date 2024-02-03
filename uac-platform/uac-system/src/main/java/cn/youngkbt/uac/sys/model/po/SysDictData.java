package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVo;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-21-12 00:21:40
 * @note 字典数据
*/
@TableName("t_sys_dict_data")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDictDataVo.class, reverseConvertGenerate = false)
public class SysDictData extends BaseDO {
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
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

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
     * 租户编号
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 字典编码
     */
    private String dictCode;

}