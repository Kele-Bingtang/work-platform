package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 02:25:27
 * @note 针对表 t_sys_user(用户信息表)的数据库操作 Mapper
*/
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    SecurityUser selectTenantUserByUsername(@Param("tenantId") String tenantId, @Param("username") String username);

    SecurityUser selectUserByUsername(@Param("username") String username);
    
    List<SysUserVO> selectListWithPage(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

    List<UserBindSelectVO> listWithDisabledByGroupId(@Param("userGroupId") String userGroupId);

}




