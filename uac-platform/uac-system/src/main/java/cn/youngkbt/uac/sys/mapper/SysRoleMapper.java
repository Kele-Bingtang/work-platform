package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.SysRole;
import cn.youngkbt.uac.sys.model.vo.link.RoleBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleLinkVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 02:24:55
 * @note 针对表 t_sys_role(应用角色信息表)的数据库操作 Mapper
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<RoleLinkVO> selectByUserId(@Param(Constants.WRAPPER) QueryWrapper<SysRole> wrapper);

    List<RoleBindSelectVO> selectWithDisabledByUserId(@Param("appId") String appId, @Param("userId") String userId);

    List<UserLinkVO> listUserLinkByRoleId(@Param("roleId") String roleId);

    List<RoleBindSelectVO> listWithDisabledByGroupId(@Param("userGroupId") String userGroupId);
}




