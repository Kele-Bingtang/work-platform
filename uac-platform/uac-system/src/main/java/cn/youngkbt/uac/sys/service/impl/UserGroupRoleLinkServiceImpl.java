package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserGroupRoleLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserGroupRoleLinkDto;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupRoleLinkVo;
import cn.youngkbt.uac.sys.service.UserGroupRoleLinkService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_role_link(用户组关联角色表)】的数据库操作Service实现
 */
@Service
public class UserGroupRoleLinkServiceImpl extends ServiceImpl<UserGroupRoleLinkMapper, UserGroupRoleLink> implements UserGroupRoleLinkService {

    @Override
    public List<UserGroupRoleLinkVo> queryLinkByAppId(UserGroupRoleLinkDto userGroupRoleLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<UserGroupRoleLink> wrapper = Wrappers.<UserGroupRoleLink>lambdaQuery()
                .eq(UserGroupRoleLink::getAppId, userGroupRoleLinkDto.getAppId())
                .eq(StringUtil.hasText(userGroupRoleLinkDto.getUserGroupId()), UserGroupRoleLink::getUserGroupId, userGroupRoleLinkDto.getUserGroupId())
                .eq(StringUtil.hasText(userGroupRoleLinkDto.getRoleId()), UserGroupRoleLink::getRoleId, userGroupRoleLinkDto.getRoleId())
                .orderByAsc(UserGroupRoleLink::getId);

        List<UserGroupRoleLink> userGroupRoleLinkList;
        if (Objects.isNull(pageQuery)) {
            userGroupRoleLinkList = baseMapper.selectList(wrapper);
        } else {
            userGroupRoleLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(userGroupRoleLinkList, UserGroupRoleLinkVo.class);
    }

    @Override
    public boolean checkUserGroupExistRole(String userGroupId) {
        return baseMapper.exists(Wrappers.<UserGroupRoleLink>lambdaQuery()
                .eq(UserGroupRoleLink::getUserGroupId, userGroupId));
    }

    @Override
    public boolean checkRoleExistUserGroup(String roleId) {
        return baseMapper.exists(Wrappers.<UserGroupRoleLink>lambdaQuery()
                .eq(UserGroupRoleLink::getRoleId, roleId));
    }

    @Override
    public boolean addOneLink(UserGroupRoleLinkDto userGroupRoleLinkDto) {
        UserGroupRoleLink userGroupRoleLink = MapstructUtil.convert(userGroupRoleLinkDto, UserGroupRoleLink.class);
        return baseMapper.insert(userGroupRoleLink) > 0;
    }

    @Override
    public boolean updateOneLink(UserGroupRoleLinkDto userGroupRoleLinkDto) {
        UserGroupRoleLink userGroupRoleLink = MapstructUtil.convert(userGroupRoleLinkDto, UserGroupRoleLink.class);
        return baseMapper.updateById(userGroupRoleLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




