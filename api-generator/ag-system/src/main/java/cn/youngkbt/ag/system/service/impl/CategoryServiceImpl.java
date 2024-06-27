package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.enums.ProjectMemberRole;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.CategoryMapper;
import cn.youngkbt.ag.system.model.dto.CategoryDTO;
import cn.youngkbt.ag.system.model.po.Category;
import cn.youngkbt.ag.system.model.vo.CategoryVO;
import cn.youngkbt.ag.system.service.CategoryService;
import cn.youngkbt.ag.system.service.ProjectMemberService;
import cn.youngkbt.ag.system.service.ServiceInfoService;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_category(目录表)」的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final ProjectMemberService projectMemberService;
    private final ServiceInfoService serviceInfoService;

    @Override
    public List<CategoryVO> listAll(CategoryDTO categoryDTO) {
        LambdaQueryWrapper<Category> wrapper = buildQueryWrapper(categoryDTO);
        List<Category> categoryList = baseMapper.selectList(wrapper);
        return MapstructUtil.convert(categoryList, CategoryVO.class);
    }

    private LambdaQueryWrapper<Category> buildQueryWrapper(CategoryDTO categoryDTO) {
        return Wrappers.<Category>lambdaQuery()
                .eq(StringUtil.hasText(categoryDTO.getTeamId()), Category::getTeamId, categoryDTO.getTeamId())
                .eq(StringUtil.hasText(categoryDTO.getCategoryCode()), Category::getCategoryCode, categoryDTO.getCategoryCode())
                .eq(StringUtil.hasText(categoryDTO.getCategoryName()), Category::getCategoryName, categoryDTO.getCategoryName())
                .eq(StringUtil.hasText(categoryDTO.getProjectId()), Category::getProjectId, categoryDTO.getProjectId())
                .orderByAsc(Category::getOrderNum);
    }

    @Override
    public boolean addCategory(CategoryDTO categoryDTO) {
        Category category = MapstructUtil.convert(categoryDTO, Category.class);
        return baseMapper.insert(category) > 0;
    }

    @Override
    public boolean editCategory(CategoryDTO categoryDTO) {
        Category category = MapstructUtil.convert(categoryDTO, Category.class);
        return baseMapper.updateById(category) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeCategory(String categoryId) {
        Category category = baseMapper.selectOne(Wrappers.<Category>lambdaQuery()
                .eq(Category::getCategoryId, categoryId));

        checkCategoryAllowed(category.getProjectId(), AgHelper.getUserId());

        // 删除所有接口
        serviceInfoService.removeAllServiceInfo(categoryId);

        return baseMapper.delete(Wrappers.<Category>lambdaQuery()
                .eq(Category::getCategoryId, categoryId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAllCategory(String projectId) {
        // 删除项目下所有接口
        serviceInfoService.removeAllServiceInfoByProjectId(projectId);
        
        return baseMapper.delete(Wrappers.<Category>lambdaQuery()
                .eq(Category::getProjectId, projectId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAllCategoryByTeamId(String teamId) {
        // 删除团队下所有接口
        serviceInfoService.removeAllServiceInfoByTeamId(teamId);
        
        return baseMapper.delete(Wrappers.<Category>lambdaQuery()
                .eq(Category::getTeamId, teamId)) > 0;
    }

    @Override
    public boolean checkCategoryCodeUnique(CategoryDTO categoryDTO) {
        return baseMapper.exists(Wrappers.<Category>lambdaQuery()
                .eq(Category::getCategoryCode, categoryDTO.getCategoryCode())
                .eq(Category::getProjectId, categoryDTO.getProjectId())
                .ne(Objects.nonNull(categoryDTO.getId()), Category::getId, categoryDTO.getId()));
    }

    @Override
    public void checkCategoryAllowed(String projectId, String userId) {
        if (!projectMemberService.checkMemberRole(projectId, userId, List.of(ProjectMemberRole.ADMIN.ordinal(), ProjectMemberRole.MEMBER.ordinal()))) {
            throw new ServiceException("用户没有分类操作权限");
        }
    }
}



