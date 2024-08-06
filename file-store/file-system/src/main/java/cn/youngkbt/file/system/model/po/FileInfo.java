package cn.youngkbt.file.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 附件信息表
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_file_info")
@Data
@Accessors(chain = true)
public class FileInfo extends BaseDO {
    /**
     * 应用系统标识
     */
    private String appId;

    /**
     * 应用系统模块
     */
    private String appModule;

    /**
     * 附件唯一标识
     */
    private String fileKey;

    /**
     * 源附件名称
     */
    private String fileName;

    /**
     * 附件存储路径
     */
    private String filePath;

    /**
     * 附件类型
     */
    private String fileType;

    /**
     * 附件大小（KB）
     */
    private Long fileSize;

    /**
     * 失效时间
     */
    private Integer expireTime;

    @Serial
    private static final long serialVersionUID = 1L;
}