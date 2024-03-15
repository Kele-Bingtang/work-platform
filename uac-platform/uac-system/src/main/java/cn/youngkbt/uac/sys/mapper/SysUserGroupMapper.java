package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindUserVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupListVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 02:25:17
 * @note 针对表 t_sys_user_group(用户组表)的数据库操作 Mapper
 */
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {

    List<UserGroupListVO> selectByUserId(@Param(Constants.WRAPPER) Wrapper<SysUserGroup> queryWrapper);

    List<UserGroupBindUserVO> selectWithDisabledByUserId(@Param("appId") String appId, @Param("userId") String userId);
}




