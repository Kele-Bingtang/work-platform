package cn.youngkbt.uac.system.service.impl;

import cn.youngkbt.uac.system.mapper.RoleDeptLinkMapper;
import cn.youngkbt.uac.system.model.dto.link.RoleLinkDeptDTO;
import cn.youngkbt.uac.system.model.po.RoleDeptLink;
import cn.youngkbt.uac.system.model.po.SysDept;
import cn.youngkbt.uac.system.service.RoleDeptLinkService;
import cn.youngkbt.utils.ListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_dept_link(角色关联部门表)】的数据库操作Service实现
 */
@Service
public class RoleDeptLinkServiceImpl extends ServiceImpl<RoleDeptLinkMapper, RoleDeptLink> implements RoleDeptLinkService {

    @Override
    public List<SysDept> listDeptListByRoleId(String roleId, String appId) {
        return baseMapper.listDeptListByRoleId(roleId, appId);
    }

    @Override
    public Boolean addDeptsToRole(RoleLinkDeptDTO roleLinkDeptDTO, boolean removeLink) {
        
        if (removeLink) {
            // 删除角色与部门关联
            baseMapper.delete(Wrappers.<RoleDeptLink>lambdaQuery()
                    .eq(RoleDeptLink::getRoleId, roleLinkDeptDTO.getRoleId()));
        }
        List<String> selectedDeptIds = roleLinkDeptDTO.getSelectedDeptIds();

        List<RoleDeptLink> roleDeptLinkList = ListUtil.newArrayList(selectedDeptIds, deptId ->
                        new RoleDeptLink().setDeptId(deptId)
                                .setRoleId(roleLinkDeptDTO.getRoleId())
                                .setAppId(roleLinkDeptDTO.getAppId())
                , RoleDeptLink.class);

        return Db.saveBatch(roleDeptLinkList);
    }
}




