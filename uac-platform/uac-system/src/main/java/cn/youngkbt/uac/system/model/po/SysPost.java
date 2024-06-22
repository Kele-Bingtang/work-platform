package cn.youngkbt.uac.system.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.system.model.vo.SysPostVO;
import com.baomidou.mybatisplus.annotation.*;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 00:24:19
 * @note 岗位信息
*/
@TableName("t_sys_post")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysPostVO.class, reverseConvertGenerate = false)
public class SysPost extends BaseDO {

    /**
     * 岗位 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String postId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 岗位用户数量
     */
    private Integer userCount;

    /**
     * 岗位介绍
     */
    private String intro;

    /**
     * 租户编号
     */
    private String tenantId;

}