package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.dto.TeamMemberWithProjectRoleDTO;
import cn.youngkbt.ag.system.model.vo.TeamMemberVO;
import cn.youngkbt.ag.system.permission.annotation.TeamAuthorize;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/23 00:10:28
 * @note
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/teamMember")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @GetMapping("/listAll")
    @Operation(summary = "团队成员查询", description = "查询团队成员")
    @TeamAuthorize(value = "#teamMemberDTO.getTeamId()", checkOwnerAndAdmin = true)
    public Response<List<TeamMemberVO>> listAll(@Validated(RestGroup.QueryGroup.class) TeamMemberDTO teamMemberDTO) {
        List<TeamMemberVO> teamMemberVOList = teamMemberService.listAll(teamMemberDTO);
        return HttpResult.ok(teamMemberVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "团队成员分页查询", description = "分页查询团队路由")
    @TeamAuthorize(value = "#teamMemberDTO.getTeamId()", checkOwnerAndAdmin = true)
    public Response<TablePage<TeamMemberVO>> listPage(@Validated(RestGroup.QueryGroup.class) TeamMemberDTO teamMemberDTO, PageQuery pageQuery) {
        TablePage<TeamMemberVO> teamMemberVOList = teamMemberService.listPage(teamMemberDTO, pageQuery);
        return HttpResult.ok(teamMemberVOList);
    }

    @PostMapping("/inviteMembers")
    @Operation(summary = "团队成员邀请", description = "邀请团队成员")
    public Response<Boolean> inviteMembers(@RequestBody List<TeamMemberDTO> teamMemberDTOList, String inviteUserId) {
        return HttpResult.ok(teamMemberService.addTeamMembers(teamMemberDTOList, inviteUserId));
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "团队退出", description = "退出团队")
    public Response<Boolean> leaveTeam(@PathVariable String teamId) {
        return HttpResult.ok(teamMemberService.leaveTeam(teamId));
    }

    @PutMapping
    @Operation(summary = "团队成员角色修改", description = "修改团队成员角色")
    @TeamAuthorize(value = "#teamMemberWithProjectRoleDTO.getTeamMember.getTeamId()", checkOwnerAndAdmin = true)
    public Response<Boolean> editTeamMemberWithProjectRole(@RequestBody TeamMemberWithProjectRoleDTO teamMemberWithProjectRoleDTO) {
        return HttpResult.ok(teamMemberService.editTeamMemberWithProjectRole(teamMemberWithProjectRoleDTO));
    }
}
