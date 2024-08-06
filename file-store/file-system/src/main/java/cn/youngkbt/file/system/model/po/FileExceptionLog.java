package cn.youngkbt.file.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 异常日志表
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_file_exception_log")
@Data
public class FileExceptionLog extends BaseDO {
    /**
     * IP
     */
    private String ip;

    /**
     * 链接
     */
    private String requestUrl;

    /**
     * 操作用户
     */
    private String operateUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 异常内容
     */
    private String content;

    @Serial
    private static final long serialVersionUID = 1L;
}