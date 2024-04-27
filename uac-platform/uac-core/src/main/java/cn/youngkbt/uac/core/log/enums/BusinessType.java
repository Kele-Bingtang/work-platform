package cn.youngkbt.uac.core.log.enums;

/**
 * @author Kele-Bingtang
 * @date 2024-21-27 16:21:41
 * @note 业务操作类型
*/
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 清空数据
     */
    CLEAN,
}
