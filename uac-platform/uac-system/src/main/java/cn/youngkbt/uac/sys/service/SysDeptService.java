package cn.youngkbt.uac.sys.service;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDeptDTO;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVO;
import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dept(部门表)】的数据库操作Service
 */
public interface SysDeptService extends IService<SysDept> {

    SysDeptVO listById(Long id);

    List<SysDeptVO> queryListWithPage(SysDeptDTO sysDeptDto, PageQuery pageQuery);

    List<Tree<String>> listDeptTreeList(SysDeptDTO sysDeptDto);

    List<DeptTree> listDeptTreeTable(SysDeptDTO sysDeptDto);
    
    SysDeptVO listParentDeptByDeptId(String deptId);
    
    List<String> listDeptNamesByIds(List<String> ids);
    
    Long queryChildrenDeptCountById(String deptId);
    
    boolean hasChild(String deptId);
    
    boolean checkDeptExistUser(String deptId);

    boolean checkDeptNameUnique(SysDeptDTO sysDeptDto);
    
    Integer getDeptUserCount(String deptId);
    
    boolean insertOne(SysDeptDTO sysDeptDto);

    boolean updateOne(SysDeptDTO sysDeptDto);

    boolean removeOne(Long id);

}
