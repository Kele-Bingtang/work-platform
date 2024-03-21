package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysRoleMapper;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.po.SysRole;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleBindUserVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkInfoVO;
import cn.youngkbt.uac.sys.model.vo.link.UserRoleListVO;
import cn.youngkbt.uac.sys.service.SysRoleService;
import cn.youngkbt.uac.sys.service.UserRoleLinkService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    private final UserRoleLinkService userRoleLinkService;

    @Override
    public SysRoleVO listById(Long id) {
        SysRole sysRole = baseMapper.selectById(id);
        Assert.nonNull(sysRole, "角色不存在");
        return MapstructUtil.convert(sysRole, SysRoleVO.class);
    }

    @Override
    public List<SysRoleVO> listWithPage(SysRoleDTO sysRoleDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.<SysRole>lambdaQuery()
                .eq(StringUtils.hasText(sysRoleDto.getRoleId()), SysRole::getRoleId, sysRoleDto.getRoleId())
                .eq(StringUtils.hasText(sysRoleDto.getRoleCode()), SysRole::getRoleCode, sysRoleDto.getRoleCode())
                .eq(StringUtils.hasText(sysRoleDto.getAppId()), SysRole::getAppId, sysRoleDto.getAppId())
                .eq(Objects.nonNull(sysRoleDto.getStatus()), SysRole::getStatus, sysRoleDto.getStatus())
                .orderByAsc(SysRole::getOrderNum);

        List<SysRole> sysRoleList;
        if (Objects.isNull(pageQuery)) {
            sysRoleList = baseMapper.selectList(wrapper);
        } else {
            sysRoleList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysRoleList, SysRoleVO.class);
    }

    @Override
    public List<UserRoleListVO> listRoleListByUserId(String appId, String userId) {
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        wrapper.eq("turl.is_deleted", ColumnConstant.NON_DELETED)
                .eq("tsr.app_id", appId)
                .eq("turl.user_id", userId);
        return baseMapper.selectByUserId(wrapper);
    }

    @Override
    public List<RoleBindUserVO> listRoleListWithDisabledByUserId(String appId, String userId) {
        return baseMapper.selectWithDisabledByUserId(appId, userId);
    }

    @Override
    public List<UserLinkInfoVO> listUserLinkByRoleId(String roleId) {
        return baseMapper.listUserLinkByRoleId(roleId);
    }

    @Override
    public List<SysRoleVO> listWithDisabledByGroupId(String userGroupId) {
        return baseMapper.listWithDisabledByGroupId(userGroupId);
    }

    @Override
    public boolean checkRoleCodeUnique(SysRoleDTO sysRoleDto) {
        return baseMapper.exists(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleCode, sysRoleDto.getRoleCode())
                .ne(Objects.nonNull(sysRoleDto.getRoleId()), SysRole::getRoleId, sysRoleDto.getRoleId()));
    }

    @Override
    public boolean checkRoleNameUnique(SysRoleDTO sysRoleDto) {
        return baseMapper.exists(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, sysRoleDto.getRoleName())
                .ne(Objects.nonNull(sysRoleDto.getRoleId()), SysRole::getRoleId, sysRoleDto.getRoleId()));
    }

    @Override
    public boolean insertOne(SysRoleDTO sysRoleDto) {
        SysRole sysRole = MapstructUtil.convert(sysRoleDto, SysRole.class);
        return baseMapper.insert(sysRole) > 0;
    }

    @Override
    public boolean updateOne(SysRoleDTO sysRoleDto) {
        SysRole sysRole = MapstructUtil.convert(sysRoleDto, SysRole.class);
        return baseMapper.updateById(sysRole) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}




