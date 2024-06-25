package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.ag.system.model.vo.ResponseConfigVO;
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
 * @date 2024-03-23 01:03:56
 * @note 响应配置
*/
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_response_config")
@Data
@AutoMapper(target = ResponseConfigVO.class, reverseConvertGenerate = false)
public class ResponseConfig extends BaseDO {
    /**
     * 响应配置 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String responseId;

    /**
     * 响应格式
     */
    private String responseJson;

    /**
     * 如果是一笔数据，是否以对象/数组形式返回（0 对象 1 数组），如果是多多笔数据，一定是以数组返回
     */
    private Integer responseArray;

    /**
     * 项目 ID
     */
    private String projectId;

    /**
     * 服务 ID
     */
    private Long serviceId;
}