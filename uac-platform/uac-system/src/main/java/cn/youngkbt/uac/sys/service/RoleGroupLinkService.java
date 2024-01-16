package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.RoleGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.RoleGroupLink;
import cn.youngkbt.uac.sys.model.vo.RoleGroupLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_group_link(角色关联角色组表)】的数据库操作Service
 */
public interface RoleGroupLinkService extends IService<RoleGroupLink> {
    List<RoleGroupLinkVo> queryLinkByAppId(RoleGroupLinkDto roleGroupLinkDto, PageQuery pageQuery);

    Boolean checkRoleExistRoleGroup(String roleId);

    Boolean checkRoleGroupExistRole(String roleGroupId);

    Boolean addOneLink(RoleGroupLinkDto roleGroupLinkDto);

    Boolean updateOneLink(RoleGroupLinkDto roleGroupLinkDto);

    Boolean removeOneLink(Long id);
}
