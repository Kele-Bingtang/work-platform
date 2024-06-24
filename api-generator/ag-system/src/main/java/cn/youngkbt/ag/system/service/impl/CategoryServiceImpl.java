package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.model.dto.CategoryDTO;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.ag.system.model.po.Category;
import cn.youngkbt.ag.system.service.CategoryService;
import cn.youngkbt.ag.system.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_category(目录表)」的数据库操作Service实现
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public boolean addCategory(CategoryDTO categoryDTO) {
        Category category = MapstructUtil.convert(categoryDTO, Category.class);
        return baseMapper.insert(category) > 0;
    }

    @Override
    public boolean removeAllCategory(String projectId) {
        return baseMapper.delete(Wrappers.<Category>lambdaQuery()
                .eq(Category::getProjectId, projectId)) > 0;
    }
}




