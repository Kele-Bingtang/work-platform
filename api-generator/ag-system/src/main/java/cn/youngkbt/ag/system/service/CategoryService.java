package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.CategoryDTO;
import cn.youngkbt.ag.system.model.po.Category;
import cn.youngkbt.ag.system.model.vo.CategoryVO;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_category（目录表）」的数据库操作 Service
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据条件查询目录列表
     *
     * @param categoryDTO 目录信息
     * @return 目录列表
     */
    List<CategoryVO> listAll(CategoryDTO categoryDTO);

    /**
     * 根据条件查询目录列表（分页）
     *
     * @param categoryDTO 目录信息
     * @param pageQuery   分页信息
     * @return 目录列表
     */
    TablePage<CategoryVO> listPage(CategoryDTO categoryDTO, PageQuery pageQuery);

    /**
     * 新增目录
     *
     * @param categoryDTO 目录信息
     * @return 是否新增成功
     */
    boolean addCategory(CategoryDTO categoryDTO);

    /**
     * 修改目录
     *
     * @param categoryDTO 目录信息
     * @return 是否修改成功
     */
    boolean editCategory(CategoryDTO categoryDTO);

    /**
     * 删除目录
     *
     * @param categoryId 目录 ID
     * @return 是否删除成功
     */
    boolean removeCategory(String categoryId);

    /**
     * 根据项目名删除所有目录
     *
     * @param projectId 目录 ID
     * @return 是否删除成功
     */
    boolean removeAllCategory(String projectId);

    /**
     * 根据团队 ID 删除所有目录
     *
     * @param teamId 团队 ID
     * @return 是否删除成功
     */
    boolean removeAllCategoryByTeamId(String teamId);

    /**
     * 校验目录编码是否唯一
     *
     * @param categoryDTO 目录信息
     * @return 是否唯一
     */
    boolean checkCategoryCodeUnique(CategoryDTO categoryDTO);
    
}
