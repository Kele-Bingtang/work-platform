package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserGroupRoleGroupLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserGroupRoleGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupRoleGroupLinkVo;
import cn.youngkbt.uac.sys.service.UserGroupRoleGroupLinkService;
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
 * @note 针对表【t_user_group_role_group_link(用户组关联角色组表)】的数据库操作Service实现
 */
@Service
public class UserGroupRoleGroupLinkServiceImpl extends ServiceImpl<UserGroupRoleGroupLinkMapper, UserGroupRoleGroupLink> implements UserGroupRoleGroupLinkService {

    @Override
    public List<UserGroupRoleGroupLinkVo> queryLinkByAppId(UserGroupRoleGroupLinkDto userGroupRoleGroupLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<UserGroupRoleGroupLink> wrapper = Wrappers.<UserGroupRoleGroupLink>lambdaQuery()
                .eq(UserGroupRoleGroupLink::getAppId, userGroupRoleGroupLinkDto.getAppId())
                .eq(StringUtil.hasText(userGroupRoleGroupLinkDto.getUserGroupId()), UserGroupRoleGroupLink::getUserGroupId, userGroupRoleGroupLinkDto.getUserGroupId())
                .eq(StringUtil.hasText(userGroupRoleGroupLinkDto.getRoleGroupId()), UserGroupRoleGroupLink::getRoleGroupId, userGroupRoleGroupLinkDto.getRoleGroupId())
                .orderByAsc(UserGroupRoleGroupLink::getId);

        List<UserGroupRoleGroupLink> userGroupRoleGroupLinkList;
        if (Objects.isNull(pageQuery)) {
            userGroupRoleGroupLinkList = baseMapper.selectList(wrapper);
        } else {
            userGroupRoleGroupLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(userGroupRoleGroupLinkList, UserGroupRoleGroupLinkVo.class);
    }

    @Override
    public Boolean checkUserGroupExistRoleGroup(String roleGroupId) {
        return baseMapper.exists(Wrappers.<UserGroupRoleGroupLink>lambdaQuery()
                .eq(UserGroupRoleGroupLink::getRoleGroupId, roleGroupId));
    }

    @Override
    public Boolean checkRoleGroupExistUserGroup(String userGroupId) {
        return baseMapper.exists(Wrappers.<UserGroupRoleGroupLink>lambdaQuery()
                .eq(UserGroupRoleGroupLink::getUserGroupId, userGroupId));
    }

    @Override
    public Boolean addOneLink(UserGroupRoleGroupLinkDto userGroupRoleGroupLinkDto) {
        UserGroupRoleGroupLink userGroupRoleGroupLink = MapstructUtil.convert(userGroupRoleGroupLinkDto, UserGroupRoleGroupLink.class);
        return baseMapper.insert(userGroupRoleGroupLink) > 0;
    }

    @Override
    public Boolean updateOneLink(UserGroupRoleGroupLinkDto userGroupRoleGroupLinkDto) {
        UserGroupRoleGroupLink userGroupRoleGroupLink = MapstructUtil.convert(userGroupRoleGroupLinkDto, UserGroupRoleGroupLink.class);
        return baseMapper.updateById(userGroupRoleGroupLink) > 0;
    }

    @Override
    public Boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




