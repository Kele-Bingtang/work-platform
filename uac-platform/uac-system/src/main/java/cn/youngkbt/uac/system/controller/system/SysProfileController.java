package cn.youngkbt.uac.system.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.idempotent.annotation.PreventRepeatSubmit;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.uac.core.helper.UacHelper;
import cn.youngkbt.uac.core.log.annotation.OperateLog;
import cn.youngkbt.uac.core.log.enums.BusinessType;
import cn.youngkbt.uac.system.model.dto.SysUserDTO;
import cn.youngkbt.uac.system.model.dto.profile.ProfileInfoDTO;
import cn.youngkbt.uac.system.model.dto.profile.UserPasswordDTO;
import cn.youngkbt.uac.system.model.po.SysUser;
import cn.youngkbt.uac.system.service.SysUserService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/4/28 00:42:24
 * @note
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user/profile")
public class SysProfileController {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;

    @PutMapping
    @OperateLog(title = "个人信息", businessType = BusinessType.UPDATE)
    @PreventRepeatSubmit
    public Response<Boolean> updateProfileInfo(@Validated @RequestBody ProfileInfoDTO profileInfoDTO) {
        LoginUser loginUser = UacHelper.getLoginUser();
        if (Objects.isNull(loginUser)) {
            return HttpResult.failMessage("用户信息失效");
        }
        SysUserDTO sysUserDTO = MapstructUtil.convert(profileInfoDTO, SysUserDTO.class);
        sysUserDTO.setId(loginUser.getId());
        sysUserDTO.setUserId(loginUser.getUserId());
        if (StringUtil.hasText(sysUserDTO.getPhone()) && sysUserService.checkPhoneUnique(sysUserDTO)) {
            return HttpResult.failMessage("修改用户「" + sysUserDTO.getUsername() + "」失败，手机号「" + sysUserDTO.getPhone() + "」已存在");
        }
        if (StringUtil.hasText(sysUserDTO.getEmail()) && sysUserService.checkEmailUnique(sysUserDTO)) {
            return HttpResult.failMessage("修改用户「" + sysUserDTO.getUsername() + "」失败，邮箱账号「" + sysUserDTO.getEmail() + "」已存在");
        }
        boolean result = sysUserService.editUser(sysUserDTO);
        return HttpResult.ok(result);
    }

    @PutMapping("/updatePassword")
    @OperateLog(title = "个人信息", businessType = BusinessType.UPDATE)
    @PreventRepeatSubmit
    public Response<Boolean> updatePassword(@Validated @RequestBody UserPasswordDTO userPasswordDTO) {
        LoginUser loginUser = UacHelper.getLoginUser();
        if (Objects.isNull(loginUser)) {
            return HttpResult.failMessage("用户未登录");
        }
        SysUser sysUser = sysUserService.getById(loginUser.getId());
        if (!passwordEncoder.matches(userPasswordDTO.getOldPassword(), sysUser.getPassword())) {
            return HttpResult.failMessage("修改密码失败，旧密码错误");
        }

        if (passwordEncoder.matches(userPasswordDTO.getNewPassword(), sysUser.getPassword())) {
            return HttpResult.failMessage("新密码不能与旧密码相同");
        }

        boolean result = sysUserService.updatePassword(loginUser.getUserId(), passwordEncoder.encode(userPasswordDTO.getNewPassword()));
        return HttpResult.ok(result);
    }
}
