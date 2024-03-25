package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-26-12 02:26:30
 * @note 针对表 t_user_role_link(用户关联角色表)的数据库操作 Mapper
 */
public interface UserRoleLinkMapper extends BaseMapper<UserRoleLink> {

    List<UserLinkVO> listUserLinkByRoleId(@Param(Constants.WRAPPER) Wrapper<UserRoleLink> queryWrapper);
}




