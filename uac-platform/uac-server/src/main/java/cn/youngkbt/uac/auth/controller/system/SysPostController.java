package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysPostDTO;
import cn.youngkbt.uac.sys.model.vo.SysPostVO;
import cn.youngkbt.uac.sys.service.SysPostService;
import cn.youngkbt.uac.sys.service.UserPostLinkService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 17:33
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/post")
public class SysPostController {
    private final SysPostService sysPostService;
    private final UserPostLinkService userPostLinkService;

    @GetMapping("/{id}")
    @Operation(summary = "岗位列表查询", description = "通过主键查询岗位列表")
    public Response<SysPostVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysPostVO sysPostVo = sysPostService.listById(id);
        return HttpResult.ok(sysPostVo);
    }

    @GetMapping("/list")
    @Operation(summary = "岗位列表查询", description = "通过查询条件查询岗位列表")
    public Response<List<SysPostVO>> list(SysPostDTO sysPostDTO) {
        List<SysPostVO> sysPostVOList = sysPostService.queryList(sysPostDTO);
        return HttpResult.ok(sysPostVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "岗位列表查询", description = "通过查询条件查询岗位列表（支持分页）")
    public Response<TablePage<SysPostVO>> listPage(SysPostDTO sysPostDTO, PageQuery pageQuery) {
        TablePage<SysPostVO> tablePage = sysPostService.listPage(sysPostDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @PostMapping
    @Operation(summary = "新增岗位", description = "新增岗位")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysPostDTO sysPostDTO) {
        if (sysPostService.checkPostNameUnique(sysPostDTO)) {
            return HttpResult.failMessage("新增岗位「" + sysPostDTO.getPostName() + "」失败，岗位名称已存在");
        } else if (sysPostService.checkPostCodeUnique(sysPostDTO)) {
            return HttpResult.failMessage("新增岗位「" + sysPostDTO.getPostName() + "」失败，岗位编码已存在");
        }
        
        return HttpResult.ok(sysPostService.insertOne(sysPostDTO));
    }

    @PutMapping
    @Operation(summary = "修改岗位", description = "修改岗位")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysPostDTO sysPostDTO) {
        if (sysPostService.checkPostNameUnique(sysPostDTO)) {
            return HttpResult.failMessage("修改岗位「" + sysPostDTO.getPostName() + "」失败，岗位名称已存在");
        } else if (sysPostService.checkPostCodeUnique(sysPostDTO)) {
            return HttpResult.failMessage("修改岗位「" + sysPostDTO.getPostName() + "」失败，岗位编码已存在");
        } else if (ColumnConstant.STATUS_EXCEPTION.equals(sysPostDTO.getStatus())
                && sysPostService.checkPostExitUser(sysPostDTO)) {
            return HttpResult.failMessage("该岗位下存在已分配用户，不能禁用!");
        }

        return HttpResult.ok(sysPostService.updateOne(sysPostDTO));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除岗位", description = "通过主键批量删除岗位")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids, @RequestBody List<String> postIds) {
        if(userPostLinkService.checkPostExistUser(postIds)) {
            return HttpResult.failMessage("该岗位已绑定用户，不允许删除");
        }
        return HttpResult.ok(sysPostService.removeBatch(List.of(ids)));
    }
}
