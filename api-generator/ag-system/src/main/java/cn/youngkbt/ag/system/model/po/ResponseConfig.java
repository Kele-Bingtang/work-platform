package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.ag.system.model.vo.ResponseConfigVO;
import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseNoLogicDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-03-23 01:03:56
 * @note 响应配置
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_response_config")
@Data
@AutoMapper(target = ResponseConfigVO.class, reverseConvertGenerate = false)
public class ResponseConfig extends BaseNoLogicDO {
    /**
     * 响应配置 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String responseId;

    /**
     * 响应码
     */
    private String code;
    /**
     * 父级响应 ID
     */
    private String parentId;

    /**
     * 字段名
     */
    private String description;
    
    /**
     * 响应码描述
     */
    private List<String> jsonCol;

    /**
     * 目录 ID
     */
    private String categoryId;

    /**
     * 服务 ID
     */
    private String serviceId;

    /**
     * 项目 ID
     */
    private String projectId;

    /**
     * 团队 ID
     */
    private String teamId;

}