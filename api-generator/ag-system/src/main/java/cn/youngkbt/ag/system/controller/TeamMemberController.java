package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.vo.TeamMemberVO;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @RequestMapping("/listPage")
    public Response<TablePage<TeamMemberVO> > listPage(@Validated(RestGroup.QueryGroup.class) TeamMemberDTO teamMemberDTO, PageQuery pageQuery) {
        TablePage<TeamMemberVO> teamMemberVOList = teamMemberService.listPage(teamMemberDTO, pageQuery);
        
        return HttpResult.ok(teamMemberVOList);
        
    }
}
