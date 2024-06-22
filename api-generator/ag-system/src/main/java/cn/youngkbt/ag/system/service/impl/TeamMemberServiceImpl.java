package cn.youngkbt.ag.system.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_team_member（团队成员表）」的数据库操作 Service 实现
 */
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {

    @Override
    public TablePage<TeamMemberVO> listPage(TeamMemberDTO teamMemberDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<TeamMember> wrapper = buildQueryWrapper(teamMemberDTO);

        Page<TeamMember> teamMemberPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(teamMemberPage, TeamMemberVO.class);
    }

    private LambdaQueryWrapper<TeamMember> buildQueryWrapper(TeamMemberDTO teamMemberDTO) {
        return Wrappers.<TeamMember>lambdaQuery()
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
}




