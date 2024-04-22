package cn.youngkbt.uac.sys.service;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysDeptDTO;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dept(部门表)】的数据库操作Service
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 根据条件查询部门列表
     *
     * @param sysDeptDTO 查询条件
     * @return 部门列表
     */
    List<SysDeptVO> queryList(SysDeptDTO sysDeptDTO);

    /**
     * 根据条件查询部门列表
     *
     * @param sysDeptDTO 查询条件
     * @param pageQuery  分页参数
     * @return 部门列表
     */
    TablePage<SysDeptVO> listPage(SysDeptDTO sysDeptDTO, PageQuery pageQuery);

    /**
     * 根据条件查询部门下拉框列表
     *
     * @param sysDeptDTO 查询条件
     * @return 部门下拉框列表
     */
    List<Tree<String>> listDeptTreeList(SysDeptDTO sysDeptDTO);

    /**
     * 根据条件查询部门树结构列表
     *
     * @param sysDeptDTO 查询条件
     * @return 部门树结构列表
     */
    List<SysDeptVO> listDeptTreeTable(SysDeptDTO sysDeptDTO);

    /**
     * 根据部门 ID 查询父部门
     *
     * @param deptId 部门 ID
     * @return 父部门
     */
    SysDeptVO listParentDeptByDeptId(String deptId);

    /**
     * 根据部门 ID 查询部门名称
     *
     * @param ids 部门 ID
     * @return 部门名称
     */
    List<String> listDeptNamesByIds(List<String> ids);

    /**
     * 根据部门 ID 查询子部门数量
     *
     * @param deptId 部门 ID
     * @return 子部门数量
     */
    Long listChildrenDeptCountById(String deptId);

    /**
     * 判断部门是否有子部门
     *
     * @param deptId 部门 ID
     * @return 是否有子部门
     */
    boolean hasChild(String deptId);

    /**
     * 判断部门存在用户
     *
     * @param deptId 部门 ID
     * @return 是否存在用户
     */
    boolean checkDeptExistUser(String deptId);

    /**
     * 判断部门名称是否唯一
     *
     * @param sysDeptDTO 部门信息
     * @return 是否唯一
     */
    boolean checkDeptNameUnique(SysDeptDTO sysDeptDTO);

    /**
     * 查询部门下用户数量
     *
     * @param deptId 部门 ID
     * @return 用户数量
     */
    Integer getDeptUserCount(String deptId);

    /**
     * 新增部门
     *
     * @param sysDeptDTO 部门信息
     * @return 是否成功
     */
    boolean insertOne(SysDeptDTO sysDeptDTO);

    /**
     * 修改部门
     *
     * @param sysDeptDTO 部门信息
     * @return 是否成功
     */
    boolean updateOne(SysDeptDTO sysDeptDTO);

    /**
     * 删除部门
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean removeOne(Long id);

}
