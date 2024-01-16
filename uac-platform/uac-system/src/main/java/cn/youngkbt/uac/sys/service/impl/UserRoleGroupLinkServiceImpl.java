package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserRoleGroupLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserRoleGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.UserRoleGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserRoleGroupLinkVo;
import cn.youngkbt.uac.sys.service.UserRoleGroupLinkService;
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
 * @note 针对表【t_user_role_group_link(用户关联角色组表)】的数据库操作Service实现
 */
@Service
public class UserRoleGroupLinkServiceImpl extends ServiceImpl<UserRoleGroupLinkMapper, UserRoleGroupLink> implements UserRoleGroupLinkService {

    @Override
    public List<UserRoleGroupLinkVo> queryLinkByAppId(UserRoleGroupLinkDto userRoleGroupLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<UserRoleGroupLink> wrapper = Wrappers.<UserRoleGroupLink>lambdaQuery()
                .eq(UserRoleGroupLink::getAppId, userRoleGroupLinkDto.getAppId())
                .eq(StringUtil.hasText(userRoleGroupLinkDto.getUserId()), UserRoleGroupLink::getUserId, userRoleGroupLinkDto.getUserId())
                .eq(StringUtil.hasText(userRoleGroupLinkDto.getRoleGroupId()), UserRoleGroupLink::getRoleGroupId, userRoleGroupLinkDto.getRoleGroupId())
                .orderByAsc(UserRoleGroupLink::getId);

        List<UserRoleGroupLink> userRoleGroupLinkList;
        if (Objects.isNull(pageQuery)) {
            userRoleGroupLinkList = baseMapper.selectList(wrapper);
        } else {
            userRoleGroupLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(userRoleGroupLinkList, UserRoleGroupLinkVo.class);
    }

    @Override
    public Boolean checkUserExistRoleGroup(String userId) {
        return baseMapper.exists(Wrappers.<UserRoleGroupLink>lambdaQuery()
                .eq(UserRoleGroupLink::getUserId, userId));
    }

    @Override
    public Boolean checkRoleGroupExistUser(String roleGroupId) {
        return baseMapper.exists(Wrappers.<UserRoleGroupLink>lambdaQuery()
                .eq(UserRoleGroupLink::getRoleGroupId, roleGroupId));
    }

    @Override
    public Boolean addOneLink(UserRoleGroupLinkDto userRoleGroupLinkDto) {
        UserRoleGroupLink userRoleGroupLink = MapstructUtil.convert(userRoleGroupLinkDto, UserRoleGroupLink.class);
        return baseMapper.insert(userRoleGroupLink) > 0;
    }

    @Override
    public Boolean updateOneLink(UserRoleGroupLinkDto userRoleGroupLinkDto) {
        UserRoleGroupLink userRoleGroupLink = MapstructUtil.convert(userRoleGroupLinkDto, UserRoleGroupLink.class);
        return baseMapper.updateById(userRoleGroupLink) > 0;
    }

    @Override
    public Boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




