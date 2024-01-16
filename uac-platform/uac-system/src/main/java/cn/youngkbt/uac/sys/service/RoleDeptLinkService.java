package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.RoleDeptLinkDto;
import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import cn.youngkbt.uac.sys.model.vo.RoleDeptLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_dept_link(角色关联部门表)】的数据库操作Service
 */
public interface RoleDeptLinkService extends IService<RoleDeptLink> {
    List<RoleDeptLinkVo> queryLinkByAppId(RoleDeptLinkDto roleDeptLinkDto, PageQuery pageQuery);

    Boolean checkDeptExistRole(String deptId);

    Boolean checkRoleExistDept(String roleId);

    Boolean addOneLink(RoleDeptLinkDto roleDeptLinkDto);

    Boolean updateOneLink(RoleDeptLinkDto roleDeptLinkDto);

    Boolean removeOneLink(Long id);
}
