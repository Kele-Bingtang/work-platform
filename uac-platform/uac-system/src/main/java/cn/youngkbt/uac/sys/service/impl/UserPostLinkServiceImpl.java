package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.UserPostLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserPostLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserPostLink;
import cn.youngkbt.uac.sys.model.vo.UserPostLinkVO;
import cn.youngkbt.uac.sys.service.UserPostLinkService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_post_link(用户关联岗位表)】的数据库操作Service实现
 */
@Service
public class UserPostLinkServiceImpl extends ServiceImpl<UserPostLinkMapper, UserPostLink> implements UserPostLinkService {

    @Override
    public List<UserPostLinkVO> queryLinkByTenantId(UserPostLinkDTO userPostLinkDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<UserPostLink> wrapper = Wrappers.<UserPostLink>lambdaQuery()
                .eq(StringUtil.hasText(userPostLinkDTO.getUserId()), UserPostLink::getUserId, userPostLinkDTO.getUserId())
                .eq(StringUtil.hasText(userPostLinkDTO.getPostId()), UserPostLink::getPostId, userPostLinkDTO.getPostId())
                .orderByAsc(UserPostLink::getId);

        List<UserPostLink> userPostLinkList;
        if (Objects.isNull(pageQuery)) {
            userPostLinkList = baseMapper.selectList(wrapper);
        } else {
            userPostLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(userPostLinkList, UserPostLinkVO.class);
    }

    @Override
    public boolean checkUserExistPost(String userId) {
        return baseMapper.exists(Wrappers.<UserPostLink>lambdaQuery()
                .eq(UserPostLink::getUserId, userId));
    }

    @Override
    public boolean checkPostExistUser(List<String> postIds) {
        return baseMapper.exists(Wrappers.<UserPostLink>lambdaQuery()
                .in(UserPostLink::getPostId, postIds));
    }

    @Override
    public boolean addOneLink(UserPostLinkDTO userPostLinkDTO) {
        UserPostLink userPostLink = MapstructUtil.convert(userPostLinkDTO, UserPostLink.class);
        return baseMapper.insert(userPostLink) > 0;
    }

    @Override
    public boolean updateOneLink(UserPostLinkDTO userPostLinkDTO) {
        UserPostLink userPostLink = MapstructUtil.convert(userPostLinkDTO, UserPostLink.class);
        return baseMapper.updateById(userPostLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




