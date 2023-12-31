package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.SysUserBO;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.sys.mapper.SysUserMapper;
import cn.youngkbt.uac.sys.model.dto.SysUserDto;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysUserVo;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public SysUserVo queryById(Long id) {
        SysUser sysUser = baseMapper.selectById(id);
        Assert.nonNull(sysUser, "用户不存在");
        return MapstructUtil.convert(sysUser, SysUserVo.class);
    }

    @Override
    public List<SysUserVo> queryListWithPage(SysUserDto sysUserDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(StringUtils.hasText(sysUserDto.getUserId()), SysUser::getTenantId, sysUserDto.getUserId())
                .eq(Objects.nonNull(sysUserDto.getUsername()), SysUser::getUsername, sysUserDto.getUsername())
                .eq(Objects.nonNull(sysUserDto.getStatus()), SysUser::getStatus, sysUserDto.getStatus())
                .orderByAsc(SysUser::getId);

        List<SysUser> sysUserList;
        if (Objects.isNull(pageQuery)) {
            sysUserList = baseMapper.selectList(wrapper);
        } else {
            sysUserList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysUserList, SysUserVo.class);
    }

    @Override
    public Boolean insertOne(SysUserDto sysUserDto) {
        SysUser sysUser = MapstructUtil.convert(sysUserDto, SysUser.class);
        return baseMapper.insert(sysUser) > 0;
    }

    @Override
    public Boolean updateOne(SysUserDto sysUserDto) {
        SysUser sysUser = MapstructUtil.convert(sysUserDto, SysUser.class);
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public Boolean removeOne(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
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
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setLoginIp(ServletUtil.getClientIp(loginInfoEvent.getRequest()));
        user.setLoginDate(new Date());
        user.setUpdateBy(userId);

        baseMapper.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, userId));
    }
}




