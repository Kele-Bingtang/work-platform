package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.RoleDeptLinkMapper;
import cn.youngkbt.uac.sys.model.dto.RoleDeptLinkDTO;
import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import cn.youngkbt.uac.sys.model.vo.RoleDeptLinkVO;
import cn.youngkbt.uac.sys.service.RoleDeptLinkService;
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
 * @note 针对表【t_role_dept_link(角色关联部门表)】的数据库操作Service实现
 */
@Service
public class RoleDeptLinkServiceImpl extends ServiceImpl<RoleDeptLinkMapper, RoleDeptLink> implements RoleDeptLinkService {

    @Override
    public List<RoleDeptLinkVO> queryLinkByAppId(RoleDeptLinkDTO roleDeptLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<RoleDeptLink> wrapper = Wrappers.<RoleDeptLink>lambdaQuery()
                .eq(RoleDeptLink::getAppId, roleDeptLinkDto.getAppId())
                .eq(StringUtil.hasText(roleDeptLinkDto.getRoleId()), RoleDeptLink::getRoleId, roleDeptLinkDto.getRoleId())
                .eq(StringUtil.hasText(roleDeptLinkDto.getDeptId()), RoleDeptLink::getDeptId, roleDeptLinkDto.getDeptId())
                .orderByAsc(RoleDeptLink::getId);

        List<RoleDeptLink> roleDeptLinkList;
        if (Objects.isNull(pageQuery)) {
            roleDeptLinkList = baseMapper.selectList(wrapper);
        } else {
            roleDeptLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(roleDeptLinkList, RoleDeptLinkVO.class);
    }

    @Override
    public boolean checkDeptExistRole(String deptId) {
        return baseMapper.exists(Wrappers.<RoleDeptLink>lambdaQuery()
                .eq(RoleDeptLink::getDeptId, deptId));
    }

    @Override
    public boolean checkRoleExistDept(String roleId) {
        return baseMapper.exists(Wrappers.<RoleDeptLink>lambdaQuery()
                .eq(RoleDeptLink::getRoleId, roleId));
    }

    @Override
    public boolean addOneLink(RoleDeptLinkDTO roleDeptLinkDto) {
        RoleDeptLink roleDeptLink = MapstructUtil.convert(roleDeptLinkDto, RoleDeptLink.class);
        return baseMapper.insert(roleDeptLink) > 0;
    }

    @Override
    public boolean updateOneLink(RoleDeptLinkDTO roleDeptLinkDto) {
        RoleDeptLink roleDeptLink = MapstructUtil.convert(roleDeptLinkDto, RoleDeptLink.class);
        return baseMapper.updateById(roleDeptLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




