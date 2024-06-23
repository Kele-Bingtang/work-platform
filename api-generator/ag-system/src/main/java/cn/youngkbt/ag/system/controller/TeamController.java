package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.TeamDTO;
import cn.youngkbt.ag.system.model.vo.router.RouterVO;
import cn.youngkbt.ag.system.service.TeamService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 13:56:52
 * @note
 */
@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/listMyAllTeamRoute")
    @Operation(summary = "团队路由查询", description = "查询团队路由")
    public Response<List<RouterVO>> listMyAllTeamRoute() {
        List<RouterVO> routerVOList = teamService.listMyAllTeamRoute();
        return HttpResult.ok(routerVOList);
    }

    @PostMapping
    @Operation(summary = "团队新增", description = "新增团队")
    public Response<Boolean> addTeam(@Validated(RestGroup.AddGroup.class) @RequestBody TeamDTO teamDTO) {
        return HttpResult.ok(teamService.addTeam(teamDTO));
    }

    @PutMapping
    @Operation(summary = "团队编辑", description = "编辑团队")
    public Response<Boolean> editTeam(@Validated(RestGroup.EditGroup.class) @RequestBody TeamDTO teamDTO) {
        return HttpResult.ok(teamService.editTeam(teamDTO));
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "团队解散", description = "解散团队")
    public Response<Boolean> removeTeam(@PathVariable String teamId) {
        return HttpResult.ok(teamService.removeTeam(teamId));
    }

    @PostMapping("/transferOwner/{teamId}/{userId}/{username}")
    @Operation(summary = "团队负责人移交", description = "移交团队负责人")
    public Response<Boolean> transferOwner(@PathVariable String teamId, @PathVariable String userId, @PathVariable String username) {
        boolean result = teamService.transferOwner(teamId, userId, username);
        return HttpResult.ok(result);
    }

}
