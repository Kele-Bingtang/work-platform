package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.RoleDeptLinkDTO;
import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import cn.youngkbt.uac.sys.model.vo.RoleDeptLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_dept_link(角色关联部门表)】的数据库操作Service
 */
public interface RoleDeptLinkService extends IService<RoleDeptLink> {
    List<RoleDeptLinkVO> queryLinkByAppId(RoleDeptLinkDTO roleDeptLinkDTO, PageQuery pageQuery);

    boolean checkDeptExistRole(String deptId);

    boolean checkRoleExistDept(String roleId);

    boolean addOneLink(RoleDeptLinkDTO roleDeptLinkDTO);

    boolean updateOneLink(RoleDeptLinkDTO roleDeptLinkDTO);

    boolean removeOneLink(Long id);
}
