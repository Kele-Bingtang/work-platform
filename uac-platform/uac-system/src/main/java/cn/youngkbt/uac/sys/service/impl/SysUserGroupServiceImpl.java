package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysUserGroupMapper;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindUserVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupListVO;
import cn.youngkbt.uac.sys.service.SysUserGroupService;
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
    public List<UserGroupListVO> listUserGroupByUserId(String appId, String userId) {
        QueryWrapper<SysUserGroup> wrapper = Wrappers.query();
        wrapper.eq("tugl.is_deleted", ColumnConstant.NON_DELETED)
                .eq("tsug.app_id", appId)
                .eq("tugl.user_id", userId);
        return baseMapper.selectByUserId(wrapper);
    }

    @Override
    public List<UserGroupBindUserVO> listUserGroupWithDisabledByUserId(String appId, String userId) {
        return baseMapper.selectWithDisabledByUserId(appId, userId);
    }


    @Override
    public boolean checkUserGroupNameUnique(SysUserGroupDTO sysUserGroupDto) {
        return baseMapper.exists(Wrappers.<SysUserGroup>lambdaQuery()
                .eq(SysUserGroup::getGroupName, sysUserGroupDto.getGroupName())
                .ne(Objects.nonNull(sysUserGroupDto.getGroupId()), SysUserGroup::getGroupId, sysUserGroupDto.getGroupId()));
    }

    @Override
    public boolean insertOne(SysUserGroupDTO sysUserGroupDto) {
        SysUserGroup userGroup = MapstructUtil.convert(sysUserGroupDto, SysUserGroup.class);
        return baseMapper.insert(userGroup) > 0;
    }

    @Override
    public boolean updateOne(SysUserGroupDTO sysUserGroupDto) {
        SysUserGroup userGroup = MapstructUtil.convert(sysUserGroupDto, SysUserGroup.class);
        return baseMapper.updateById(userGroup) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}




