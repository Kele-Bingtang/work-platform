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
     * 根据项目的密钥查询项目信息
     *
     * @return 项目信息
     */
    ProjectVO listProjectBySecretKey(String secretKey);

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
     * 检查是否有项目权限操作
     *
     * @param teamId  团队 ID
     * @param project  项目 ID
     * @param userId  用户 ID
     */
    void checkProjectAllowed(String teamId, String project, String userId);

    /**
     * 检查项目名是否唯一
     *
     * @param projectDTO 项目信息
     * @return 是否唯一
     */
    boolean checkProjectNameUnique(ProjectDTO projectDTO);
    
}
