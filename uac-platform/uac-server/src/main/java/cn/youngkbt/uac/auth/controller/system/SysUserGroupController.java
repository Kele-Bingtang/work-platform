package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.extra.UserGroupBindUserVO;
import cn.youngkbt.uac.sys.service.SysUserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/3/12 23:59
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userGroup")
public class SysUserGroupController {

    private final SysUserGroupService sysUserGroupService;

    @GetMapping("/list")
    public Response<List<SysUserGroupVO>> list(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery) {
        List<SysUserGroupVO> sysUserGroupVOList = sysUserGroupService.list(sysUserGroupDTO, pageQuery);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("/listByUserId/{appId}/{userId}")
    public Response<List<SysUserGroupVO>> listUserIdGroup(@PathVariable String appId, @PathVariable String userId,  PageQuery pageQuery) {
        List<SysUserGroupVO> sysUserGroupVOList = sysUserGroupService.listUserGroupByUserId(appId, userId, pageQuery);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("listWithDisabledByUserId/{appId}/{userId}")
    public Response<List<UserGroupBindUserVO>> listUserGroupNotUserId(@PathVariable String appId, @PathVariable String userId) {
        List<UserGroupBindUserVO> sysUserGroupVOList = sysUserGroupService.listUserGroupWithDisabledByUserId(appId, userId);
        return HttpResult.ok(sysUserGroupVOList);
    }

}
