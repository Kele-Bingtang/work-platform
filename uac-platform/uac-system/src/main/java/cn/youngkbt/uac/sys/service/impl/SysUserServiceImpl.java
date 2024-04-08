package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.sys.mapper.SysDeptMapper;
import cn.youngkbt.uac.sys.mapper.SysUserMapper;
import cn.youngkbt.uac.sys.model.dto.SysPostDTO;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysPostVO;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
import cn.youngkbt.uac.sys.service.SysPostService;
import cn.youngkbt.uac.sys.service.SysRoleService;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_user(用户信息表)】的数据库操作Service实现
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${default.password}")
    private String password;

    private final SysDeptMapper sysDeptMapper;
    private final SysPostService sysPostService;
    private final SysRoleService sysRoleService;

    @Override
    public SecurityUser selectTenantUserByUsername(String tenantId, String username) {
        return baseMapper.selectTenantUserByUsername(tenantId, username);
    }

    @Override
    public SecurityUser selectUserByUsername(String username) {
        return baseMapper.selectUserByUsername(username);
    }

    @Override
    public SysUserVO listById(Long id) {
        SysUser sysUser = baseMapper.selectById(id);
        Assert.nonNull(sysUser, "用户不存在");
        return MapstructUtil.convert(sysUser, SysUserVO.class);
    }

    @Override
    public List<SysUserVO> queryList(SysUserDTO sysUserDTO) {
        QueryWrapper<SysUser> wrapper = buildQueryWrapper(sysUserDTO);
        return baseMapper.queryList(wrapper);
    }

    @Override
    public TablePage<SysUserVO> listPage(SysUserDTO sysUserDTO, PageQuery pageQuery) {
        QueryWrapper<SysUser> wrapper = buildQueryWrapper(sysUserDTO);
        IPage<SysUserVO> sysUserVOIPage = baseMapper.selectListWithPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysUserVOIPage);
    }

    private QueryWrapper<SysUser> buildQueryWrapper(SysUserDTO sysUserDTO) {
        QueryWrapper<SysUser> wrapper = Wrappers.query();
        wrapper.eq("su.is_deleted", ColumnConstant.NON_DELETED)
                .eq(StringUtils.hasText(sysUserDTO.getUserId()), "su.user_id", sysUserDTO.getUserId())
                .like(StringUtils.hasText(sysUserDTO.getUsername()), "su.username", sysUserDTO.getUsername())
                .like(StringUtils.hasText(sysUserDTO.getPhone()), "su.phone", sysUserDTO.getPhone())
                .like(StringUtils.hasText(sysUserDTO.getEmail()), "su.email", sysUserDTO.getEmail())
                .eq(Objects.nonNull(sysUserDTO.getStatus()), "su.status", sysUserDTO.getStatus())
                .and(StringUtils.hasText(sysUserDTO.getDeptId()), c -> {
                    // 查出 deptId 所对应的部门及其子部门 ID 信息
                    List<SysDept> sysDeptList = sysDeptMapper.selectList(Wrappers.<SysDept>lambdaQuery()
                            .select(SysDept::getDeptId)
                            .apply("FIND_IN_SET({0}, ancestors) > 0", sysUserDTO.getDeptId()));
                    // 获取所有子部门 ID
                    List<String> deptIds = sysDeptList.stream().map(SysDept::getDeptId).filter(Objects::nonNull).collect(Collectors.toList());
                    // 加上当前部门
                    deptIds.add(sysUserDTO.getDeptId());
                    c.in("su.dept_id", deptIds);
                })
                .orderByAsc("su.id");

        return wrapper;
    }

    @Override
    public boolean checkUserNameUnique(SysUserDTO sysUserDTO) {
        return baseMapper.exists(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, sysUserDTO.getUsername())
                .ne(Objects.nonNull(sysUserDTO.getUserId()), SysUser::getUserId, sysUserDTO.getUserId()));
    }

    @Override
    public boolean checkPhoneUnique(SysUserDTO sysUserDTO) {
        return baseMapper.exists(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getPhone, sysUserDTO.getPhone())
                .ne(Objects.nonNull(sysUserDTO.getUserId()), SysUser::getUserId, sysUserDTO.getUserId()));
    }

    @Override
    public boolean checkEmailUnique(SysUserDTO sysUserDTO) {
        return baseMapper.exists(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getEmail, sysUserDTO.getEmail())
                .ne(Objects.nonNull(sysUserDTO.getUserId()), SysUser::getUserId, sysUserDTO.getUserId()));
    }

    @Override
    public RolePostVo rolePostList() {
        List<SysPostVO> sysPostVOList = sysPostService.queryList(new SysPostDTO());
        List<SysRoleVO> sysRoleVOList = sysRoleService.queryList(new SysRoleDTO());

        RolePostVo rolePostVo = new RolePostVo();
        rolePostVo.setPostList(sysPostVOList)
                .setRoleList(sysRoleVOList);

        return rolePostVo;
    }

    @Override
    public boolean insertOne(SysUserDTO sysUserDTO) {
        SysUser sysUser = MapstructUtil.convert(sysUserDTO, SysUser.class);
        sysUser.setRegisterTime(new Date());
        if (Objects.isNull(sysUser.getPassword())) {
            sysUser.setPassword(new BCryptPasswordEncoder().encode(password));
        }
        return baseMapper.insert(sysUser) > 0;
    }

    @Override
    public boolean updateOne(SysUserDTO sysUserDTO) {
        SysUser sysUser = MapstructUtil.convert(sysUserDTO, SysUser.class);
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public boolean updateOneByUserId(SysUserDTO sysUserDTO) {
        SysUser sysUser = MapstructUtil.convert(sysUserDTO, SysUser.class);
        return baseMapper.update(sysUser, Wrappers.<SysUser>lambdaUpdate()
                .eq(SysUser::getUsername, sysUserDTO.getUsername())) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
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
        user.setLoginTime(new Date());
        user.setUpdateBy(userId);

        baseMapper.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, userId));
    }
}




