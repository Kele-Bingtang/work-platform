package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserRoleLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserRoleLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserRoleLinkVO;
import cn.youngkbt.uac.sys.service.UserRoleLinkService;
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
 * @note 针对表【t_user_role_link(用户关联角色表)】的数据库操作Service实现
 */
@Service
public class UserRoleLinkServiceImpl extends ServiceImpl<UserRoleLinkMapper, UserRoleLink> implements UserRoleLinkService {

    @Override
    public List<UserRoleLinkVO> queryLinkByAppId(UserRoleLinkDTO userRoleLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<UserRoleLink> wrapper = Wrappers.<UserRoleLink>lambdaQuery()
                .eq(UserRoleLink::getAppId, userRoleLinkDto.getAppId())
                .eq(StringUtil.hasText(userRoleLinkDto.getUserId()), UserRoleLink::getUserId, userRoleLinkDto.getUserId())
                .eq(StringUtil.hasText(userRoleLinkDto.getRoleId()), UserRoleLink::getRoleId, userRoleLinkDto.getRoleId())
                .orderByAsc(UserRoleLink::getId);

        List<UserRoleLink> userRoleLinkList;
        if (Objects.isNull(pageQuery)) {
            userRoleLinkList = baseMapper.selectList(wrapper);
        } else {
            userRoleLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(userRoleLinkList, UserRoleLinkVO.class);
    }

    @Override
    public boolean checkUserExistRole(String userId) {
        return baseMapper.exists(Wrappers.<UserRoleLink>lambdaQuery()
                .eq(UserRoleLink::getUserId, userId));
    }

    @Override
    public boolean checkRoleExistUser(String roleId) {
        return baseMapper.exists(Wrappers.<UserRoleLink>lambdaQuery()
                .eq(UserRoleLink::getRoleId, roleId));
    }

    @Override
    public boolean checkUserExistRoles(String userId, List<String> roleIds) {
        return baseMapper.exists(Wrappers.<UserRoleLink>lambdaQuery()
                .eq(UserRoleLink::getUserId, userId)
                .in(UserRoleLink::getRoleId, roleIds));
    }

    @Override
    public boolean addOneLink(UserRoleLinkDTO userRoleLinkDto) {
        UserRoleLink userRoleLink = MapstructUtil.convert(userRoleLinkDto, UserRoleLink.class);
        return baseMapper.insert(userRoleLink) > 0;
    }

    @Override
    public boolean updateOneLink(UserRoleLinkDTO userRoleLinkDto) {
        UserRoleLink userRoleLink = MapstructUtil.convert(userRoleLinkDto, UserRoleLink.class);
        return baseMapper.updateById(userRoleLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




