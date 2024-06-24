package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.CategoryDTO;
import cn.youngkbt.ag.system.model.po.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_category（目录表）」的数据库操作 Service
 */
public interface CategoryService extends IService<Category> {

    /**
     * 新增目录
     *
     * @param categoryDTO 目录信息
     * @return 是否新增成功
     */
    boolean addCategory(CategoryDTO categoryDTO);

    /**
     * 删除所有目录
     *
     * @param projectId 目录 ID
     * @return 是否删除成功
     */
    boolean removeAllCategory(String projectId);
}
