package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.po.TeamMember;
import cn.youngkbt.ag.system.model.vo.TeamMemberVO;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_team_member（团队成员表）」的数据库操作 Service
 */
public interface TeamMemberService extends IService<TeamMember> {

    /**
     * 查询团队成员
     * @param teamMemberDTO 团队成员信息
     * @param pageQuery 分页信息
     * @return 团队成员列表
     */
    TablePage<TeamMemberVO> listPage(TeamMemberDTO teamMemberDTO, PageQuery pageQuery);
    
    /**
     * 添加团队成员
     * @param teamMemberDTO 团队成员信息
     * @return 是否添加成功
     */
    boolean addTeamMember(TeamMemberDTO teamMemberDTO);

}
