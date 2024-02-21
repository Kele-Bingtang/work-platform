package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysDeptMapper;
import cn.youngkbt.uac.sys.model.dto.SysDeptDto;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import cn.youngkbt.uac.sys.service.SysDeptService;
import cn.youngkbt.uac.sys.utils.DeptTreeUtil;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dept(部门表)】的数据库操作Service实现
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public SysDeptVo queryById(Long id) {
        SysDept sysDept = baseMapper.selectById(id);
        Assert.nonNull(sysDept, "部门不存在");
        return MapstructUtil.convert(sysDept, SysDeptVo.class);
    }

    @Override
    public List<SysDeptVo> queryListWithPage(SysDeptDto sysDeptDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDept> wrapper = buildQueryWrapper(sysDeptDto);

        List<SysDept> sysDeptList;
        if (Objects.isNull(pageQuery)) {
            sysDeptList = baseMapper.selectList(wrapper);
        } else {
            sysDeptList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysDeptList, SysDeptVo.class);
    }

    @Override
    public List<Tree<String>> selectDeptTreeList(SysDeptDto sysDeptDto) {
        // 查询正常状态的部门
        sysDeptDto.setStatus(ColumnConstant.STATUS_NORMAL);
        LambdaQueryWrapper<SysDept> wrapper = buildQueryWrapper(sysDeptDto);
        List<SysDept> sysDeptList = baseMapper.selectList(wrapper);
        return buildDeptTree(sysDeptList);
    }

    private LambdaQueryWrapper<SysDept> buildQueryWrapper(SysDeptDto sysDeptDto) {
        return Wrappers.<SysDept>lambdaQuery()
                .eq(StringUtils.hasText(sysDeptDto.getDeptId()), SysDept::getDeptId, sysDeptDto.getDeptId())
                .eq(StringUtils.hasText(sysDeptDto.getParentId()), SysDept::getParentId, sysDeptDto.getParentId())
                .eq(StringUtils.hasText(sysDeptDto.getDeptName()), SysDept::getDeptName, sysDeptDto.getDeptName())
                .eq(Objects.nonNull(sysDeptDto.getStatus()), SysDept::getStatus, sysDeptDto.getStatus());
    }

    /**
     * 构建前端所需要下拉树结构
     */
    private List<Tree<String>> buildDeptTree(List<SysDept> sysDeptList) {
        if (CollUtil.isEmpty(sysDeptList)) {
            return Collections.emptyList();
        }

        return TreeUtil.build(sysDeptList, "0", TreeNodeConfig.DEFAULT_CONFIG.setNameKey("label"), (treeNode, tree) ->
                tree.setId(treeNode.getDeptId())
                        .setParentId(treeNode.getParentId())
                        .setName(treeNode.getDeptName())
                        .setWeight(treeNode.getOrderNum())
                        .putExtra("icon", treeNode.getIcon()));
    }

    @Override
    public List<DeptTree> buildDeptTreeTable(SysDeptDto sysDeptDto) {
        // 查询正常状态的部门
        sysDeptDto.setStatus(ColumnConstant.STATUS_NORMAL);
        LambdaQueryWrapper<SysDept> wrapper = buildQueryWrapper(sysDeptDto);
        List<SysDept> sysDeptList = baseMapper.selectList(wrapper);
        List<DeptTree> sysDeptVoList = MapstructUtil.convert(sysDeptList, DeptTree.class);
        return DeptTreeUtil.build(sysDeptVoList);
    }

    /**
     * 通过一个部门 ID 查询其父部门名字
     */
    @Override
    public SysDeptVo queryParentDeptByDeptId(String deptId) {
        SysDept sysDept = baseMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getDeptId, deptId));
        if (Objects.isNull(sysDept)) {
            return null;
        }
        SysDept parentDept = baseMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getDeptName)
                .eq(SysDept::getDeptId, sysDept.getParentId()));
        SysDeptVo sysDeptVo = MapstructUtil.convert(sysDept, SysDeptVo.class);

        sysDeptVo.setDeptName(parentDept.getDeptName());
        return sysDeptVo;
    }

    /**
     * 通过部门 id 查询其部门名字
     */
    @Override
    public List<String> queryDeptNamesByIds(List<String> ids) {
        List<SysDept> sysDeptList = baseMapper.selectList(Wrappers.<SysDept>lambdaQuery()
                .in(SysDept::getDeptId, ids));

        return ListUtil.newArrayList(sysDeptList, SysDept::getDeptName, String.class);
    }

    /**
     * 通过部门 id 查询其子部门数量（正常状态）
     */
    @Override
    public Long queryChildrenDeptCountById(String deptId) {
        return baseMapper.selectCount(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getStatus, ColumnConstant.STATUS_NORMAL)
                .apply("FIND_IN_SET({0}, ancestors) > 0", deptId));
    }

    /**
     * 是否存在子部门
     */
    @Override
    public boolean hasChild(String deptId) {
        return baseMapper.exists(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getParentId, deptId));
    }

    /**
     * 校验部门是否用用户
     */
    @Override
    public boolean checkDeptExistUser(String deptId) {
        return baseMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getDeptId, deptId)).getUserCount() > 0;
    }

    /**
     * 校验部门名称是否唯一
     */
    @Override
    public boolean checkDeptNameUnique(SysDeptDto sysDeptDto) {
        return baseMapper.exists(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getDeptName, sysDeptDto.getDeptName())
                .eq(SysDept::getParentId, sysDeptDto.getParentId())
                .ne(Objects.nonNull(sysDeptDto.getDeptId()), SysDept::getDeptId, sysDeptDto.getDeptId()));
    }

    /**
     * 获取部门及其子部门的用户数量
     */
    @Override
    public Integer getDeptUserCount(String deptId) {
        List<SysDept> deptList = baseMapper.selectList(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getDeptId, deptId)
                .or()
                .apply("FIND_IN_SET({0}, ancestors) > 0", deptId));

        Integer userCount = 0;

        for (SysDept dept : deptList) {
            userCount += dept.getUserCount();
        }
        return userCount;
    }

    @Override
    public boolean insertOne(SysDeptDto sysDeptDto) {
        SysDept sysDept = MapstructUtil.convert(sysDeptDto, SysDept.class);
        
        if (Objects.nonNull(sysDeptDto.getParentId())) {
            SysDept dept = baseMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                    .eq(SysDept::getDeptId, sysDeptDto.getParentId()));

            // 如果父节点不为正常状态,则不允许新增子节点
            if (!ColumnConstant.STATUS_NORMAL.equals(dept.getStatus())) {
                throw new ServiceException("部门停用，不允许新增");
            }

            dept.setAncestors(dept.getAncestors() + StringUtil.SEPARATOR + dept.getParentId());
            return baseMapper.insert(sysDept) > 0;
        }

        sysDept.setParentId("0");
        return baseMapper.insert(sysDept) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOne(SysDeptDto sysDeptDto) {
        SysDept sysDept = MapstructUtil.convert(sysDeptDto, SysDept.class);

        // 如果更新为启用状态，则上级上级所有部门都启用
        if (ColumnConstant.STATUS_NORMAL.equals(sysDept.getStatus()) && StringUtil.hasText(sysDept.getAncestors()) && !"0".equals(sysDept.getAncestors())) {
            updateParentDeptStatusNormal(sysDept);
        }

        // 如果迁移某个部门到其他父部门，则不仅更新该部门，还更新其子部门的 ancestors
        SysDept oldDept = baseMapper.selectById(sysDept.getId());
        if (!sysDept.getParentId().equals(oldDept.getParentId())) {
            SysDept newParentDept = baseMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                    .eq(SysDept::getParentId, sysDept.getParentId()));
            Assert.nonNull(newParentDept, "父部门不存在，无法转移");
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            sysDept.setAncestors(newAncestors);
            // 将子部门的 ancestors 更新
            updateDeptChildren(sysDept.getDeptId(), newAncestors, oldAncestors);
        }
        return baseMapper.updateById(sysDept) > 0;
    }

    /**
     * 修改该部门的父级部门状态
     */
    private void updateParentDeptStatusNormal(SysDept sysDept) {
        String ancestors = sysDept.getAncestors();
        List<String> deptIdList = Arrays.asList(ancestors.split(","));
        baseMapper.update(null,
                Wrappers.<SysDept>lambdaUpdate()
                        .set(SysDept::getStatus, ColumnConstant.STATUS_NORMAL)
                        .in(SysDept::getDeptId, deptIdList));
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    private void updateDeptChildren(String deptId, String newAncestors, String oldAncestors) {
        // 查询某个 deptId 的子部门，只需要查询 ancestors 有该 deptId 即可
        List<SysDept> childrenList = baseMapper.selectList(Wrappers.<SysDept>lambdaQuery()
                .apply("FIND_IN_SET({0}, ancestors) > 0", deptId));

        List<SysDept> list = ListUtil.newArrayList(childrenList, child -> {
            SysDept dept = new SysDept();
            dept.setDeptId(child.getDeptId());
            dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            return dept;
        });

        if (!CollectionUtils.isEmpty(list)) {
            Db.updateBatchById(list);
        }
    }

    @Override
    public boolean removeOne(String deptId) {
        return baseMapper.delete(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getDeptId, deptId)) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




