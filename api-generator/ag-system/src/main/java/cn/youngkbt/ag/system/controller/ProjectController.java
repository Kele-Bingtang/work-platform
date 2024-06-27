package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.ProjectDTO;
import cn.youngkbt.ag.system.model.vo.ProjectVO;
import cn.youngkbt.ag.system.service.ProjectService;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/24 22:33:45
 * @note 项目相关接口
 */
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;
    private final TeamMemberService teamMemberService;

    @GetMapping("/listProjectBySecretKey/{secretKey}")
    @Operation(summary = "通过密钥查询一条项目数据", description = "通过密钥查询一条项目数据")
    public Response<ProjectVO> listProjectBySecretKey(@NotBlank(message = "无效参数") @PathVariable String secretKey) {
        ProjectVO project = projectService.listProjectBySecretKey(secretKey);
        return HttpResult.ok(project);
    }

    @GetMapping("/list")
    @Operation(summary = "个人项目列表查询", description = "查询个人项目列表")
    public Response<List<ProjectVO>> listProject(@Validated(RestGroup.QueryGroup.class) ProjectDTO projectDTO) {
        List<ProjectVO> projectVOList = projectService.listProject(projectDTO);
        return HttpResult.ok(projectVOList);
    }

    @PostMapping
    @Operation(summary = "项目新增", description = "新增项目")
    public Response<Boolean> addProject(@Validated(RestGroup.AddGroup.class) @RequestBody ProjectDTO projectDTO) {
        if (projectService.checkProjectNameUnique(projectDTO)) {
            return HttpResult.failMessage("新增项目「" + projectDTO.getProjectName() + "」失败，项目名称已存在");
        }

        boolean result = projectService.addProject(projectDTO);
        return HttpResult.okOrFail(result);
    }

    @PutMapping
    @Operation(summary = "项目编辑", description = "编辑项目")
    public Response<Boolean> editProject(@Validated(RestGroup.EditGroup.class) @RequestBody ProjectDTO projectDTO) {
        if (projectService.checkProjectNameUnique(projectDTO)) {
            return HttpResult.failMessage("编辑项目「" + projectDTO.getProjectName() + "」失败，项目名称已存在");
        }

        boolean result = projectService.editProject(projectDTO);
        return HttpResult.okOrFail(result);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "项目删除", description = "删除项目")
    public Response<Boolean> removeProject(@NotBlank(message = "无效参数") @PathVariable String projectId) {
        boolean result = projectService.removeProject(projectId);
        return HttpResult.okOrFail(result);
    }
}