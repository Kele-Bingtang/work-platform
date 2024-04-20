package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.dto.link.UserLinkPostDTO;
import cn.youngkbt.uac.sys.model.po.SysPost;
import cn.youngkbt.uac.sys.model.po.UserPostLink;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_post_link(用户关联岗位表)】的数据库操作Service
 */
public interface UserPostLinkService extends IService<UserPostLink> {
    
    List<SysPost> listPostByUserId(String userId);

    boolean checkPostExistUser(List<String> postIds);
    
    boolean addPostsToUser(UserLinkPostDTO userLinkPostDTO);
}
