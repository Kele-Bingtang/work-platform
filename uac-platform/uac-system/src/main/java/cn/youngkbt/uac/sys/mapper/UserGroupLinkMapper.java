package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 02:25:39
 * @note 针对表 t_user_group_link(用户关联用户组表)的数据库操作 Mapper
*/
public interface UserGroupLinkMapper extends BaseMapper<UserGroupLink> {

    List<UserLinkInfoVO> listUserLinkByGroupId(@Param("userGroupId") String userGroupId);
}




