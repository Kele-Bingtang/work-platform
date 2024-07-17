package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.ProjectDTO;
import cn.youngkbt.ag.system.model.po.Project;
import cn.youngkbt.ag.system.model.vo.ProjectVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_project（项目表）」的数据库操作 Service
 */
public interface ProjectService extends IService<Project> {

    /**
     * 通过项目 ID 查询一笔项目数据
     *
     * @param projectId 项目 ID
     * @return 项目信息
     */
    ProjectVO getByProjectId(String projectId);

    /**
     * 查询个人的所有项目
     *
     * @param projectDTO 查询条件
     * @return 个人的所有项目
     */
    List<ProjectVO> listProject(ProjectDTO projectDTO);

    /**
     * 添加项目
     *
     * @param projectDTO 项目信息
     * @return 是否添加成功
     */
    boolean addProject(ProjectDTO projectDTO);

    /**
     * 添加项目
     *
     * @param projectDTO 项目信息
     * @return 是否添加成功
     */
    boolean editProject(ProjectDTO projectDTO);

    /**
     * 删除项目
     *
     * @param projectId 项目 ID
     * @return 是否删除成功
     */
    boolean removeProject(String projectId);

    /**
     * 删除团队下的所有项目
     *
     * @param teamId 团队 ID
     * @return 是否删除成功
     */
    boolean removeAllProject(String teamId);

    /**
     * 检查项目名是否唯一
     *
     * @param projectDTO 项目信息
     * @return 是否唯一
     */
    boolean checkProjectNameUnique(ProjectDTO projectDTO);

    /**
     * 项目转移
     *
     * @param projectId 项目 ID
     * @param teamId    团队 ID
     * @return 是否转移成功
     */
    boolean transferProject(String projectId, String teamId);
}
