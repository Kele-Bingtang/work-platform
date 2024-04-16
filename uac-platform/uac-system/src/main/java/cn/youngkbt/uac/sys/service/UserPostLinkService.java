package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserPostLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserPostLink;
import cn.youngkbt.uac.sys.model.vo.UserPostLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_post_link(用户关联岗位表)】的数据库操作Service
 */
public interface UserPostLinkService extends IService<UserPostLink> {
    List<UserPostLinkVO> queryLinkByTenantId(UserPostLinkDTO userPostLinkDTO, PageQuery pageQuery);

    boolean checkUserExistPost(String userId);

    boolean checkPostExistUser(List<String> postIds);

    boolean addOneLink(UserPostLinkDTO userPostLinkDTO);

    boolean updateOneLink(UserPostLinkDTO userPostLinkDTO);

    boolean removeOneLink(Long id);
}
