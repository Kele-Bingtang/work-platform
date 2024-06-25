package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.ag.system.model.vo.CategoryVO;
import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2024-05-23 01:03:21
 * @note 目录
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_category")
@AutoMapper(target = CategoryVO.class, reverseConvertGenerate = false)
public class Category extends BaseDO {

    /**
     * 分类 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String categoryId;

    /**
     * 目录编码
     */
    private String categoryCode;

    /**
     * 目录名称
     */
    private String categoryName;

    /**
     * 是否是主目录（0 不是 1 是）
     */
    private Integer isMain;

    /**
     * 项目 id
     */
    private String projectId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 团队 ID
     */
    private String teamId;
}