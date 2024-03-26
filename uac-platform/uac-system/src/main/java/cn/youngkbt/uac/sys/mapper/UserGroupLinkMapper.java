package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 02:25:39
 * @note 针对表 t_user_group_link(用户关联用户组表)的数据库操作 Mapper
 */
public interface UserGroupLinkMapper extends BaseMapper<UserGroupLink> {

    List<UserGroupLinkVO> listUserGroupByUserId(@Param(Constants.WRAPPER) Wrapper<SysUserGroup> queryWrapper);

    List<UserLinkVO> listUserLinkByGroupId(@Param(Constants.WRAPPER) Wrapper<UserGroupLink> queryWrapper);

    List<UserGroupBindSelectVO> selectWithDisabledByUserId(@Param("appId") String appId, @Param("userId") String userId);

    List<UserBindSelectVO> listWithDisabledByGroupId(@Param("userGroupId") String userGroupId);

}




