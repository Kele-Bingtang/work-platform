package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.TeamDTO;
import cn.youngkbt.ag.system.model.po.Team;
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
     * @return 团队路由
     */
    List<RouterVO> listMyAllTeamRoute();

    /**
     * 添加一个团队
     * @param teamDTO 团队信息
     * @return 是否添加成功
     */
    boolean addTeam(TeamDTO teamDTO);
}
