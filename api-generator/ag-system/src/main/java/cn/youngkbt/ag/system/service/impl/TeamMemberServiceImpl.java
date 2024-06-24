package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.TeamMemberMapper;
import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.po.TeamMember;
import cn.youngkbt.ag.system.model.vo.TeamMemberVO;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_team_member（团队成员表）」的数据库操作 Service 实现
 */
@Service
@Slf4j
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {

    @Override
    public List<TeamMemberVO> listAll(TeamMemberDTO teamMemberDTO) {
        LambdaQueryWrapper<TeamMember> wrapper = buildQueryWrapper(teamMemberDTO);
        List<TeamMember> teamMemberList = baseMapper.selectList(wrapper);

        return MapstructUtil.convert(teamMemberList, TeamMemberVO.class);
    }

    @Override
    public TablePage<TeamMemberVO> listPage(TeamMemberDTO teamMemberDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<TeamMember> wrapper = buildQueryWrapper(teamMemberDTO);

        Page<TeamMember> teamMemberPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(teamMemberPage, TeamMemberVO.class);
    }

    private LambdaQueryWrapper<TeamMember> buildQueryWrapper(TeamMemberDTO teamMemberDTO) {
        return Wrappers.<TeamMember>lambdaQuery()
                .eq(StringUtil.hasText(teamMemberDTO.getTeamId()), TeamMember::getTeamId, teamMemberDTO.getTeamId())
                .eq(StringUtil.hasText(teamMemberDTO.getUsername()), TeamMember::getUsername, teamMemberDTO.getUsername())
                .eq(StringUtil.hasText(teamMemberDTO.getNickname()), TeamMember::getNickname, teamMemberDTO.getNickname())
                .eq(Objects.nonNull(teamMemberDTO.getTeamRole()), TeamMember::getTeamRole, teamMemberDTO.getTeamRole())
                .eq(Objects.nonNull(teamMemberDTO.getStatus()), TeamMember::getStatus, teamMemberDTO.getStatus())
                .orderByAsc(TeamMember::getTeamRole);
    }

    @Override
    public boolean addTeamMember(TeamMemberDTO teamMemberDTO) {
        TeamMember teamMember = MapstructUtil.convert(teamMemberDTO, TeamMember.class);
        return baseMapper.insert(teamMember) > 0;
    }

    @Override
    public boolean editTeamMemberRole(String teamId, String userId, int ordinal) {
        return baseMapper.update(new TeamMember(), Wrappers.<TeamMember>lambdaUpdate()
                .eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId)
                .set(TeamMember::getTeamRole, ordinal)) > 0;
    }

    @Override
    public boolean leaveTeam(String teamId) {
        String userId = AgHelper.getUserId();
        if (StringUtil.hasEmpty(userId)) {
            log.error("无法获取登录的用户信息");
            return false;
        }
        return baseMapper.delete(Wrappers.<TeamMember>lambdaQuery()
                .eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId)) > 0;
    }

    @Override
    public boolean removeAllTeamMember(String teamId) {
        return baseMapper.delete(Wrappers.<TeamMember>lambdaQuery()
                .eq(TeamMember::getTeamId, teamId)) > 0;
    }

    @Override
    public boolean checkMemberExist(String teamId, String userId) {
        return baseMapper.exists(Wrappers.<TeamMember>lambdaQuery()
                .eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId));
    }

    @Override
    public boolean checkMemberRole(String teamId, String userId, List<Integer> ordinal) {
        return baseMapper.exists(Wrappers.<TeamMember>lambdaQuery()
                .eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, userId)
                .in(TeamMember::getTeamRole, ordinal));
    }
}
