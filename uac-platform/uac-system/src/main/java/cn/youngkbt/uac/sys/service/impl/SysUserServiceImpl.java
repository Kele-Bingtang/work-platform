package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.sys.mapper.SysUserMapper;
import cn.youngkbt.mp.base.SysUserBO;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.ServletUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.core.event.LoginInfoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_user(用户信息表)】的数据库操作Service实现
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUserBO selectTenantUserByUsername(String tenantId, String username) {
        return baseMapper.selectTenantUserByUsername(tenantId, username);
    }

    @Override
    public SysUserBO selectUserByUsername(String username) {
        return baseMapper.selectUserByUsername(username);
    }

    /**
     * 登录成功的信息记录
     */
    @Async
    @EventListener
    public void updateUserStatus(LoginInfoEvent loginInfoEvent) {
        if (!loginInfoEvent.getStatus().equals(AuthConstant.LOGIN_SUCCESS)) {
            return;
        }
        // 登录成功则往下走
        String userId = loginInfoEvent.getUserId();
        SysUser user = SysUser.builder()
                .userId(userId)
                .loginIp(ServletUtil.getClientIp(loginInfoEvent.getRequest()))
                .loginDate(new Date())
                .build();

        user.setUpdateBy(userId);
        baseMapper.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, userId));
    }
}




