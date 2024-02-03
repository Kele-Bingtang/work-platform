package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.uac.sys.model.po.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 02:25:27
 * @note 针对表 t_sys_user(用户信息表)的数据库操作 Mapper
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    SecurityUser selectTenantUserByUsername(@Param("tenantId") String tenantId, @Param("username") String username);

    SecurityUser selectUserByUsername(@Param("username") String username);
}




