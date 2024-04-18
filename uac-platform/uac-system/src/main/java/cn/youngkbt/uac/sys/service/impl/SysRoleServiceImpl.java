package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.SysRoleMapper;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.po.*;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import cn.youngkbt.uac.sys.service.*;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_role(应用角色信息表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final RoleMenuLinkService roleMenuLinkService;
    private final RoleDeptLinkService roleDeptLinkService;
    private final UserRoleLinkService userRoleLinkService;
    private final UserGroupRoleLinkService userGroupRoleLinkService;

    @Override
    public SysRoleVO listById(Long id) {
        SysRole sysRole = baseMapper.selectById(id);
        Assert.nonNull(sysRole, "角色不存在");
        return MapstructUtil.convert(sysRole, SysRoleVO.class);
    }

    @Override
    public List<SysRoleVO> queryList(SysRoleDTO sysRoleDTO) {
        LambdaQueryWrapper<SysRole> wrapper = buildQueryWrapper(sysRoleDTO);
        List<SysRole> sysRoleList = baseMapper.selectList(wrapper);

        return MapstructUtil.convert(sysRoleList, SysRoleVO.class);
    }

    @Override
    public TablePage<SysRoleVO> listPage(SysRoleDTO sysRoleDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysRole> wrapper = buildQueryWrapper(sysRoleDTO);
        Page<SysRole> sysRolePage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysRolePage, SysRoleVO.class);
    }

    private LambdaQueryWrapper<SysRole> buildQueryWrapper(SysRoleDTO sysRoleDTO) {
        return Wrappers.<SysRole>lambdaQuery()
                .eq(StringUtils.hasText(sysRoleDTO.getRoleId()), SysRole::getRoleId, sysRoleDTO.getRoleId())
                .eq(StringUtils.hasText(sysRoleDTO.getRoleCode()), SysRole::getRoleCode, sysRoleDTO.getRoleCode())
                .eq(StringUtils.hasText(sysRoleDTO.getAppId()), SysRole::getAppId, sysRoleDTO.getAppId())
                .eq(Objects.nonNull(sysRoleDTO.getStatus()), SysRole::getStatus, sysRoleDTO.getStatus())
                .orderByAsc(SysRole::getOrderNum);

    }

    @Override
    public boolean checkRoleCodeUnique(SysRoleDTO sysRoleDTO) {
        return baseMapper.exists(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleCode, sysRoleDTO.getRoleCode())
                .ne(Objects.nonNull(sysRoleDTO.getRoleId()), SysRole::getRoleId, sysRoleDTO.getRoleId()));
    }

    @Override
    public boolean checkRoleNameUnique(SysRoleDTO sysRoleDTO) {
        return baseMapper.exists(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, sysRoleDTO.getRoleName())
                .ne(Objects.nonNull(sysRoleDTO.getRoleId()), SysRole::getRoleId, sysRoleDTO.getRoleId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOne(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = MapstructUtil.convert(sysRoleDTO, SysRole.class);
        baseMapper.insert(sysRole);
        
        sysRoleDTO.setRoleId(sysRole.getRoleId());
        return roleMenuLinkService.addMenusToRole(sysRoleDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOne(SysRoleDTO sysRoleDTO) {
        SysRole sysRole = MapstructUtil.convert(sysRoleDTO, SysRole.class);
        baseMapper.updateById(sysRole);
        // 删除角色与菜单关联
        roleMenuLinkService.remove(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getRoleId, sysRoleDTO.getRoleId()));
        return roleMenuLinkService.addMenusToRole(sysRoleDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBatch(List<Long> ids, List<String> roleIds) {
        // 删除角色与菜单关联
        roleMenuLinkService.remove(Wrappers.<RoleMenuLink>lambdaQuery().in(RoleMenuLink::getRoleId, roleIds));
        // 删除角色与部门关联
        roleDeptLinkService.remove(Wrappers.<RoleDeptLink>lambdaQuery().in(RoleDeptLink::getRoleId, roleIds));
        // 删除角色与用户绑定
        userRoleLinkService.remove(Wrappers.<UserRoleLink>lambdaQuery().in(UserRoleLink::getRoleId, roleIds));
        // 删除角色与用户组绑定
        userGroupRoleLinkService.remove(Wrappers.<UserGroupRoleLink>lambdaQuery().in(UserGroupRoleLink::getRoleId, roleIds));
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean checkAppExitRole(List<String> appIds) {
        return baseMapper.exists(Wrappers.<SysRole>lambdaQuery()
                .in(SysRole::getAppId, appIds));
    }
}




