package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysUserGroupMapper;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.extra.UserGroupBindUserVO;
import cn.youngkbt.uac.sys.service.SysUserGroupService;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
import cn.youngkbt.utils.ListUtil;
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
 * @note 针对表【t_sys_user_group(用户组表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements SysUserGroupService {

    private final UserGroupLinkService userGroupLinkService;

    @Override
    public List<SysUserGroupVO> list(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysUserGroup> wrapper = Wrappers.<SysUserGroup>lambdaQuery()
                .eq(StringUtils.hasText(sysUserGroupDTO.getGroupName()), SysUserGroup::getGroupName, sysUserGroupDTO.getGroupName())
                .eq(StringUtils.hasText(sysUserGroupDTO.getAppId()), SysUserGroup::getAppId, sysUserGroupDTO.getAppId())
                .orderByAsc(SysUserGroup::getCreateTime);

        List<SysUserGroup> sysUserGroupList;
        if (Objects.isNull(pageQuery)) {
            sysUserGroupList = baseMapper.selectList(wrapper);
        } else {
            sysUserGroupList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysUserGroupList, SysUserGroupVO.class);
    }

    @Override
    public List<SysUserGroupVO> listUserGroupByUserId(String appId, String userId) {
        QueryWrapper<SysUserGroup> wrapper = Wrappers.query();
        wrapper.eq("tsug.app_id", appId)
                .eq("tugl.user_id", userId);
        List<SysUserGroup> sysUserGroupList = baseMapper.selectByUserId(wrapper);
        return MapstructUtil.convert(sysUserGroupList, SysUserGroupVO.class);
    }

    @Override
    public List<UserGroupBindUserVO> listUserGroupWithDisabledByUserId(String appId, String userId) {
        return baseMapper.selectWithDisabledByUserId(appId, userId);
    }

    @Override
    public boolean addUserToGroups(UserLinkUserGroupDTO userLinkUserGroupDTO) {
        List<String> userGroupIds = userLinkUserGroupDTO.getUserGroupIds();

        List<UserGroupLink> userGroupLinkList = ListUtil.newArrayList(userGroupIds, userGroupId ->
                        new UserGroupLink().setUserGroupId(userGroupId)
                                .setUserId(userLinkUserGroupDTO.getUserId())
                                .setValidFrom(userLinkUserGroupDTO.getValidFrom())
                                .setExpireOn(userLinkUserGroupDTO.getExpireOn())
                                .setAppId(userLinkUserGroupDTO.getAppId())
                , UserGroupLink.class);

        return userGroupLinkService.saveBatch(userGroupLinkList);
    }
}




