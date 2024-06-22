package cn.youngkbt.uac.system.mapper;

import cn.youngkbt.uac.system.model.po.SysUserGroup;
import cn.youngkbt.uac.system.model.po.UserGroupLink;
import cn.youngkbt.uac.system.model.vo.link.UserBindSelectVO;
import cn.youngkbt.uac.system.model.vo.link.UserGroupBindSelectVO;
import cn.youngkbt.uac.system.model.vo.link.UserGroupLinkVO;
import cn.youngkbt.uac.system.model.vo.link.UserLinkVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 02:25:39
 * @note 针对表 t_user_group_link(用户关联用户组表)的数据库操作 Mapper
 */
public interface UserGroupLinkMapper extends BaseMapper<UserGroupLink> {

    List<UserGroupLinkVO> listUserGroupByUserId(@Param(Constants.WRAPPER) Wrapper<SysUserGroup> queryWrapper);

    IPage<UserLinkVO> listUserLinkByGroupId(@Param("page") Page<UserGroupLink> page, @Param(Constants.WRAPPER) Wrapper<UserGroupLink> queryWrapper);

    List<UserGroupBindSelectVO> selectWithDisabledByUserId(@Param("appId") String appId, @Param("userId") String userId);

    List<UserBindSelectVO> listWithDisabledByGroupId(@Param("userGroupId") String userGroupId);

}




