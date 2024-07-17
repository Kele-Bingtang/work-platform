package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.TeamDTO;
import cn.youngkbt.ag.system.model.po.Team;
import cn.youngkbt.ag.system.model.vo.TeamRouteVO;
import cn.youngkbt.ag.system.model.vo.router.RouterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_team（团队表）」的数据库操作 Service
 */
public interface TeamService extends IService<Team> {

    /**
     * 获取我的团队路由
     *
     * @return 团队路由
     */
    List<RouterVO> listMyAllTeamRoute();

    /**
     * 获取我的所有团队
     *
     * @return 团队信息
     */
    List<TeamRouteVO> listMyAll();

    /**
     * 添加一个团队
     *
     * @param teamDTO 团队信息
     * @return 是否添加成功
     */
    boolean addTeam(TeamDTO teamDTO);

    /**
     * 编辑一个团队
     *
     * @param teamDTO 团队信息
     * @return 是否编辑成功
     */
    Boolean editTeam(TeamDTO teamDTO);

    /**
     * 解散团队
     *
     * @param teamId 团队 ID
     * @return 是否删除成功
     */
    Boolean removeTeam(String teamId);

    /**
     * 移交团队负责人
     *
     * @param teamId   团队 ID
     * @param userId   接收人 ID
     * @param username 接收人
     * @return 是否删除成功
     */
    boolean transferOwner(String teamId, String userId, String username);

    /**
     * 检查团队名称是否唯一
     *
     * @param teamDTO 团队信息
     * @return 是否唯一
     */
    boolean checkTeamNameUnique(TeamDTO teamDTO);

}
