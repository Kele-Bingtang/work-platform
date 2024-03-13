package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserGroupLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserGroupLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupLinkVO;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
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
 * @note 针对表【t_user_group_link(用户关联用户组表)】的数据库操作Service实现
 */
@Service
public class UserGroupLinkServiceImpl extends ServiceImpl<UserGroupLinkMapper, UserGroupLink> implements UserGroupLinkService {

    @Override
    public List<UserGroupLinkVO> queryLinkByTenantId(UserGroupLinkDTO userGroupLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<UserGroupLink> wrapper = Wrappers.<UserGroupLink>lambdaQuery()
                .eq(StringUtil.hasText(userGroupLinkDto.getUserId()), UserGroupLink::getUserId, userGroupLinkDto.getUserId())
                .eq(StringUtil.hasText(userGroupLinkDto.getUserGroupId()), UserGroupLink::getUserGroupId, userGroupLinkDto.getUserGroupId())
                .orderByAsc(UserGroupLink::getId);

        List<UserGroupLink> userGroupLinkList;
        if (Objects.isNull(pageQuery)) {
            userGroupLinkList = baseMapper.selectList(wrapper);
        } else {
            userGroupLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(userGroupLinkList, UserGroupLinkVO.class);
    }

    @Override
    public boolean checkUserExistUserGroup(String userId) {
        return baseMapper.exists(Wrappers.<UserGroupLink>lambdaQuery()
                .eq(UserGroupLink::getUserId, userId));
    }

    @Override
    public boolean checkUserGroupExistUser(String userGroupId) {
        return baseMapper.exists(Wrappers.<UserGroupLink>lambdaQuery()
                .eq(UserGroupLink::getUserGroupId, userGroupId));
    }

    @Override
    public boolean addOneLink(UserGroupLinkDTO userGroupLinkDto) {
        UserGroupLink userGroupLink = MapstructUtil.convert(userGroupLinkDto, UserGroupLink.class);
        return baseMapper.insert(userGroupLink) > 0;
    }

    @Override
    public boolean updateOneLink(UserGroupLinkDTO userGroupLinkDto) {
        UserGroupLink userGroupLink = MapstructUtil.convert(userGroupLinkDto, UserGroupLink.class);
        return baseMapper.updateById(userGroupLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




