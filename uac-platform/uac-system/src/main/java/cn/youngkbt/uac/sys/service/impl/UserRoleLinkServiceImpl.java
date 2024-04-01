package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserRoleLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserRoleLinkDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkRoleDTO;
import cn.youngkbt.uac.sys.model.po.SysRole;
import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserRoleLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import cn.youngkbt.uac.sys.service.UserRoleLinkService;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
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
    public List<UserRoleLinkVO> queryLinkByAppId(UserRoleLinkDTO userRoleLinkDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<UserRoleLink> wrapper = Wrappers.<UserRoleLink>lambdaQuery()
                .eq(UserRoleLink::getAppId, userRoleLinkDTO.getAppId())
                .eq(StringUtil.hasText(userRoleLinkDTO.getUserId()), UserRoleLink::getUserId, userRoleLinkDTO.getUserId())
                .eq(StringUtil.hasText(userRoleLinkDTO.getRoleId()), UserRoleLink::getRoleId, userRoleLinkDTO.getRoleId())
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
    public boolean addUserToRoles(UserLinkRoleDTO userLinkRoleDTO) {
        List<String> roleIds = userLinkRoleDTO.getRoleIds();

        List<UserRoleLink> userRoleLinkList = ListUtil.newArrayList(roleIds, roleId ->
                        new UserRoleLink().setRoleId(roleId)
                                .setUserId(userLinkRoleDTO.getUserId())
                                .setValidFrom(userLinkRoleDTO.getValidFrom())
                                .setExpireOn(userLinkRoleDTO.getExpireOn())
                                .setAppId(userLinkRoleDTO.getAppId())
                , UserRoleLink.class);

        return Db.saveBatch(userRoleLinkList);
    }

    @Override
    public boolean updateOne(UserRoleLinkDTO userRoleLinkDTO) {
        UserRoleLink userRoleLink = MapstructUtil.convert(userRoleLinkDTO, UserRoleLink.class);
        return baseMapper.updateById(userRoleLink) > 0;
    }

    @Override
    public boolean removeUserFromRole(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<UserLinkVO> listUserLinkByRoleId(String roleId) {
        QueryWrapper<UserRoleLink> queryWrapper = Wrappers.query();
        queryWrapper.eq("tsu.is_deleted", 0)
                .eq("turl.role_id", roleId);
        
        return baseMapper.listUserLinkByRoleId(queryWrapper);
    }

    @Override
    public List<RoleLinkVO> listRoleLinkByUserId(String appId, String userId) {
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        wrapper.eq("turl.is_deleted", ColumnConstant.NON_DELETED)
                .eq("tsr.app_id", appId)
                .eq("turl.user_id", userId);
        return baseMapper.listRoleLinkByUserId(wrapper);
    }

    @Override
    public List<RoleBindSelectVO> listWithDisabledByUserId(String appId, String userId) {
        return baseMapper.selectWithDisabledByUserId(appId, userId);
    }

    @Override
    public List<UserBindSelectVO> listWithDisabledByRoleId(String roleId) {
        return baseMapper.listWithDisabledByRoleId(roleId);
    }
}




