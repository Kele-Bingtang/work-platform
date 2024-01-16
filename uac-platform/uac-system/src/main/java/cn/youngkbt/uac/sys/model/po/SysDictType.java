package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SysDictType extends BaseDO {
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
     * 租户编号
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

}