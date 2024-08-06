package cn.youngkbt.file.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 附件信息日志表
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_file_opera_log")
@Data
public class FileOperaLog extends BaseDO {
    /**
     * 日志 ID
     */
    private String operaId;

    /**
     * 应用系统标识
     */
    private String appId;

    /**
     * 附件唯一标识
     */
    private String fileKey;

    /**
     * 操作类型（0 上传 1 下载）
     */
    private String operateType;

    /**
     * 操作用户
     */
    private String operateUser;

    /**
     * 主机地址
     */
    private String operaIp;

    /**
     * 操作地点
     */
    private String operaLocation;

    /**
     * 消耗时间
     */
    private Integer costTime;

    /**
     * 备注
     */
    private String remark;

    @Serial
    private static final long serialVersionUID = 1L;
}