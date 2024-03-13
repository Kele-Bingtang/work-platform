package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysPostDTO;
import cn.youngkbt.uac.sys.model.vo.SysPostVO;
import cn.youngkbt.uac.sys.service.SysPostService;
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

    @GetMapping("/{id}")
    @Operation(summary = "岗位列表查询", description = "通过主键查询岗位列表")
    public Response<SysPostVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysPostVO sysPostVo = sysPostService.listById(id);
        return HttpResult.ok(sysPostVo);
    }

    @GetMapping("/list")
    @Operation(summary = "岗位列表查询", description = "通过查询条件查询岗位列表（支持分页）")
    public Response<List<SysPostVO>> list(SysPostDTO sysPostDto, PageQuery pageQuery) {
        List<SysPostVO> sysPostVOList = sysPostService.listWithPage(sysPostDto, pageQuery);
        return HttpResult.ok(sysPostVOList);
    }

    @PostMapping
    @Operation(summary = "新增岗位", description = "新增岗位")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysPostDTO sysPostDto) {
        if (sysPostService.checkPostNameUnique(sysPostDto)) {
            return HttpResult.failMessage("新增岗位「" + sysPostDto.getPostName() + "」失败，岗位名称已存在");
        } else if (sysPostService.checkPostCodeUnique(sysPostDto)) {
            return HttpResult.failMessage("新增岗位「" + sysPostDto.getPostName() + "」失败，岗位编码已存在");
        }
        
        return HttpResult.ok(sysPostService.insertOne(sysPostDto));
    }

    @PutMapping
    @Operation(summary = "修改岗位", description = "修改岗位")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysPostDTO sysPostDto) {
        if (sysPostService.checkPostNameUnique(sysPostDto)) {
            return HttpResult.failMessage("修改岗位「" + sysPostDto.getPostName() + "」失败，岗位名称已存在");
        } else if (sysPostService.checkPostCodeUnique(sysPostDto)) {
            return HttpResult.failMessage("修改岗位「" + sysPostDto.getPostName() + "」失败，岗位编码已存在");
        } else if (ColumnConstant.STATUS_EXCEPTION.equals(sysPostDto.getStatus())
                && sysPostService.checkPostExitUser(sysPostDto)) {
            return HttpResult.failMessage("该岗位下存在已分配用户，不能禁用!");
        }

        return HttpResult.ok(sysPostService.updateOne(sysPostDto));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除岗位", description = "通过主键批量删除岗位")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysPostService.removeBatch(List.of(ids)));
    }
}
