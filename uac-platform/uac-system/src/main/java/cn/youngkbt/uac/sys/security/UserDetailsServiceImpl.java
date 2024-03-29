package cn.youngkbt.uac.sys.security;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.core.exception.AuthException;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @note 2022/12/10 21:45
 * @note 该类是 Spring Security 自动调用，判断用户是否存在、有权限
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] loginInfo = username.split(":");
        SysUser user = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(TenantHelper.isEnable(), SysUser::getTenantId, loginInfo[0])
                .eq(SysUser::getUsername, TenantHelper.isEnable() ? loginInfo[1] : username));

        if (Objects.isNull(user)) {
            log.info("用户不存在");
            // 在 LoginFailureHandler 以 InternalAuthenticationServiceException 异常被捕获
            throw new AuthException("用户不存在");
        }

        if (ColumnConstant.STATUS_EXCEPTION.equals(user.getStatus())) {
            log.info("用户已被停用");
            throw new AuthException("用户已被停用");
        }

        SecurityUser securityUser = new SecurityUser(user.getUsername(), user.getPassword(), AuthorityUtils.NO_AUTHORITIES);
        securityUser.setUserId(user.getUserId());
        securityUser.setDeptId(user.getDeptId());
        securityUser.setTenantId(user.getTenantId());
        securityUser.setNickName(user.getNickname());
        securityUser.setEmail(user.getEmail());
        securityUser.setPhone(user.getPhone());
        securityUser.setSex(user.getSex());
        securityUser.setAvatar(user.getAvatar());
        securityUser.setRegisterTime(user.getRegisterTime());
        securityUser.setLoginDate(user.getLoginDate());
        securityUser.setLoginIp(user.getLoginIp());
        
        return securityUser;

        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        // List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // grantedAuthorities.add(new SimpleGrantedAuthority("Kele-Bingtang"));
        // return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}