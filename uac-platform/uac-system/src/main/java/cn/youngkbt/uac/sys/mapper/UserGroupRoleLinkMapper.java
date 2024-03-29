package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import cn.youngkbt.uac.sys.model.vo.link.RoleBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkRoleVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-26-12 02:26:00
 * @note 针对表 t_user_group_role_link(用户组关联角色表)的数据库操作 Mapper
 */
public interface UserGroupRoleLinkMapper extends BaseMapper<UserGroupRoleLink> {

    List<UserGroupLinkRoleVO> listRoleLinkByGroupId(@Param(Constants.WRAPPER) Wrapper<UserGroupLink> queryWrapper);

    List<UserGroupLinkVO> listUserGroupByRoleId(@Param("roleId") String roleId);

    List<RoleBindSelectVO> listWithDisabledByGroupId(@Param("userGroupId") String userGroupId);

    List<UserGroupBindSelectVO> listWithDisabledByRoleId(@Param("roleId") String roleId);
    
}




