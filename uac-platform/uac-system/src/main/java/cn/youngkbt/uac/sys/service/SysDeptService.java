package cn.youngkbt.uac.sys.service;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDeptDto;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import cn.youngkbt.uac.sys.utils.DeptTreeUtil;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dept(部门表)】的数据库操作Service
 */
public interface SysDeptService extends IService<SysDept> {

    SysDeptVo queryById(Long id);

    List<SysDeptVo> queryListWithPage(SysDeptDto sysDeptDto, PageQuery pageQuery);

    List<Tree<String>> selectDeptTreeList(SysDeptDto sysDeptDto);

    List<DeptTree> buildDeptTreeTable(SysDeptDto sysDeptDto);
    
    SysDeptVo queryParentDeptByDeptId(String deptId);
    
    List<String> queryDeptNamesByIds(List<String> ids);
    
    Long queryChildrenDeptCountById(String deptId);
    
    boolean hasChild(String deptId);
    
    boolean checkDeptExistUser(String deptId);

    boolean checkDeptNameUnique(SysDeptDto sysDeptDto);
    
    Integer getDeptUserCount(String deptId);
    
    boolean insertOne(SysDeptDto sysDeptDto);

    boolean updateOne(SysDeptDto sysDeptDto);

    boolean removeOne(String deptId);

    boolean removeBatch(List<Long> ids);


}
