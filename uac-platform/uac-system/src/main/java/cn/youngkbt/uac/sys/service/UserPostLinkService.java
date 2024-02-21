package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserPostLinkDto;
import cn.youngkbt.uac.sys.model.po.UserPostLink;
import cn.youngkbt.uac.sys.model.vo.UserPostLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_post_link(用户关联岗位表)】的数据库操作Service
 */
public interface UserPostLinkService extends IService<UserPostLink> {
    List<UserPostLinkVo> queryLinkByTenantId(UserPostLinkDto userPostLinkDto, PageQuery pageQuery);

    boolean checkUserExistPost(String userId);

    boolean checkPostGroupExistUser(String postId);

    boolean addOneLink(UserPostLinkDto userPostLinkDto);

    boolean updateOneLink(UserPostLinkDto userPostLinkDto);

    boolean removeOneLink(Long id);
}
