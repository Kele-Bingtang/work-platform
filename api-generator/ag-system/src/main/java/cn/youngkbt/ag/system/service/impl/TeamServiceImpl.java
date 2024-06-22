package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.enums.BelongType;
import cn.youngkbt.ag.core.enums.TeamMemberRole;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.TeamMapper;
import cn.youngkbt.ag.system.model.dto.TeamDTO;
import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.po.Team;
import cn.youngkbt.ag.system.model.vo.TeamRouteVO;
import cn.youngkbt.ag.system.model.vo.router.Meta;
import cn.youngkbt.ag.system.model.vo.router.RouterVO;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.ag.system.service.TeamService;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_team（团队表）」的数据库操作 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    private final TeamMemberService teamMemberService;

    @Override
    public List<RouterVO> listMyAllTeamRoute() {
        // 模拟用户
        String userId = AgHelper.getUserId();
        List<TeamRouteVO> teamRouteVOList = baseMapper.listMyAllTeam(userId);

        // 构建一级菜单
        RouterVO oneRouterVO = new RouterVO()
                .setPath("/team")
                .setName("Team")
                .setMeta(new Meta()
                        .setTitle("团队管理")
                        .setIcon("tdesign:user-circle")
                        .setAlwaysShowRoot(true)
                );

        // 构建二级菜单
        List<RouterVO> routerVOList = new ArrayList<>();


        teamRouteVOList.forEach(item -> {
            String teamRoleLabel = "";
            Integer teamRoleValue = item.getTeamRole();
            for (TeamMemberRole teamMemberRole : TeamMemberRole.values()) {
                if (teamMemberRole.ordinal() == teamRoleValue) {
                    teamRoleLabel = teamMemberRole.getLabel();
                }
            }


            RouterVO routerVO = new RouterVO()
                    .setPath(item.getTeamId())
                    .setName(item.getTeamName())
                    .setComponent("/team/index")
                    .setMeta(new Meta()
                            .setTitle(item.getTeamName())
                            .setHideInTab(true)
                            .setParams(Map.of("teamId", item.getTeamId(), "teamName", item.getTeamName(), "teamRole", teamRoleLabel))
                    );

            routerVOList.add(routerVO);
        });

        oneRouterVO.setChildren(routerVOList);
        return List.of(oneRouterVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeam(TeamDTO teamDTO) {
        LoginUser loginUser = AgHelper.getLoginUser();
        if (Objects.isNull(loginUser)) {
            throw new ServiceException("用户未登录");
        }
        Team team = MapstructUtil.convert(teamDTO, Team.class);
        team.setOwnerName(loginUser.getUsername());
        // ID 为自增，因此不需要设置
        team.setId(null);
        // 新增团队
        int result = baseMapper.insert(team);

        // 新增团队成员
        TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setTeamRole(TeamMemberRole.OWNER.ordinal())
                .setUserId(loginUser.getUserId())
                .setUsername(loginUser.getUsername())
                .setTeamId(team.getTeamId())
                .setBelongType(BelongType.CREATE.ordinal());

        teamMemberService.addTeamMember(teamMemberDTO);

        return result > 0;
    }
}




