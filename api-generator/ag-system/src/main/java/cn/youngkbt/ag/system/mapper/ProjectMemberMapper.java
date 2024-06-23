package cn.youngkbt.ag.system.mapper;

import cn.youngkbt.ag.system.model.po.ProjectMember;
import cn.youngkbt.ag.system.model.vo.ProjectMemberVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-23-22 00:23:55
 * @note 针对表「t_project_member（项目成员表）」的数据库操作 Mapper
 */
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {

    List<ProjectMemberVO> listProjectRole(@Param(Constants.WRAPPER) Wrapper<ProjectMember> queryWrapper);
}




