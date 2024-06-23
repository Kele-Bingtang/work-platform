package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.vo.TeamMemberVO;
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
    public Response<List<TeamMemberVO>> listAll(@Validated(RestGroup.QueryGroup.class) TeamMemberDTO teamMemberDTO) {
        List<TeamMemberVO> teamMemberVOList = teamMemberService.listAll(teamMemberDTO);

        return HttpResult.ok(teamMemberVOList);

    }
    
    @GetMapping("/listPage")
    @Operation(summary = "团队成员分页查询", description = "分页查询团队路由")
    public Response<TablePage<TeamMemberVO> > listPage(@Validated(RestGroup.QueryGroup.class) TeamMemberDTO teamMemberDTO, PageQuery pageQuery) {
        TablePage<TeamMemberVO> teamMemberVOList = teamMemberService.listPage(teamMemberDTO, pageQuery);
        
        return HttpResult.ok(teamMemberVOList);
    }

    @DeleteMapping("/{teamId}")
    @Operation(summary = "团队退出", description = "退出团队")
    public Response<Boolean> leaveTeam(@PathVariable String teamId) {
        return HttpResult.ok(teamMemberService.leaveTeam(teamId));
    }
    
}
