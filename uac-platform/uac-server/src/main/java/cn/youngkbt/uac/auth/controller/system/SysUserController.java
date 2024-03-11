package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
import cn.youngkbt.uac.sys.model.dto.SysUserDto;
import cn.youngkbt.uac.sys.model.vo.SysUserVo;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.StringUtil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 19:58
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("/{id}")
    public Response<SysUserVo> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysUserVo sysUserVo = sysUserService.listById(id);
        return HttpResult.ok(sysUserVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysUserVo>> list(SysUserDto sysUserDto, PageQuery pageQuery) {
        List<SysUserVo> sysUserVoList = sysUserService.queryListWithPage(sysUserDto, pageQuery);
        return HttpResult.ok(sysUserVoList);
    }
    
    @GetMapping("/rolePostList")
    public Response<RolePostVo> rolePostList() {
        RolePostVo rolePostVo = sysUserService.rolePostList();
        return HttpResult.ok(rolePostVo);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysUserDto sysUserDto) {
        if (sysUserService.checkUserNameUnique(sysUserDto)) {
            return HttpResult.failMessage("新增用户「" + sysUserDto.getUsername() + "」失败，登录账号已存在");
        } else if (StringUtil.hasText(sysUserDto.getPhone()) && sysUserService.checkPhoneUnique(sysUserDto)) {
            return HttpResult.failMessage("新增用户「" + sysUserDto.getUsername() + "」失败，手机号码已存在");
        } else if (StringUtil.hasText(sysUserDto.getEmail()) && sysUserService.checkEmailUnique(sysUserDto)) {
            return HttpResult.failMessage("新增用户「" + sysUserDto.getUsername() + "」失败，邮箱账号已存在");
        }
        
        return HttpResult.ok(sysUserService.insertOne(sysUserDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysUserDto sysUserDto) {
        if (sysUserService.checkUserNameUnique(sysUserDto)) {
            return HttpResult.failMessage("修改用户「" + sysUserDto.getUsername() + "」失败，登录账号已存在");
        } else if (StringUtil.hasText(sysUserDto.getPhone()) && sysUserService.checkPhoneUnique(sysUserDto)) {
            return HttpResult.failMessage("修改用户「" + sysUserDto.getUsername() + "」失败，手机号码已存在");
        } else if (StringUtil.hasText(sysUserDto.getEmail()) && sysUserService.checkEmailUnique(sysUserDto)) {
            return HttpResult.failMessage("修改用户「" + sysUserDto.getUsername() + "」失败，邮箱账号已存在");
        }
        
        return HttpResult.ok(sysUserService.updateOne(sysUserDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysUserService.removeBatch(List.of(ids)));
    }
}
