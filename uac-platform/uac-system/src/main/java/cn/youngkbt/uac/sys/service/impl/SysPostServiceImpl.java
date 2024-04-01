package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.SysPostMapper;
import cn.youngkbt.uac.sys.mapper.UserPostLinkMapper;
import cn.youngkbt.uac.sys.model.dto.SysPostDTO;
import cn.youngkbt.uac.sys.model.po.SysPost;
import cn.youngkbt.uac.sys.model.po.UserPostLink;
import cn.youngkbt.uac.sys.model.vo.SysPostVO;
import cn.youngkbt.uac.sys.service.SysPostService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_post(岗位表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    private final UserPostLinkMapper userPostLinkMapper;

    @Override
    public SysPostVO listById(Long id) {
        SysPost sysPost = baseMapper.selectById(id);
        Assert.nonNull(sysPost, "岗位不存在");
        return MapstructUtil.convert(sysPost, SysPostVO.class);
    }

    @Override
    public List<SysPostVO> queryList(SysPostDTO sysPostDTO) {
        LambdaQueryWrapper<SysPost> wrapper = buildQueryWrapper(sysPostDTO);
        List<SysPost> sysPostList = baseMapper.selectList(wrapper);

        return MapstructUtil.convert(sysPostList, SysPostVO.class);
    }

    @Override
    public TablePage<SysPostVO> listPage(SysPostDTO sysPostDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysPost> wrapper = buildQueryWrapper(sysPostDTO);
        Page<SysPost> sysPostPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);
        
        return TablePage.build(sysPostPage, SysPostVO.class);
    }

    private LambdaQueryWrapper<SysPost> buildQueryWrapper(SysPostDTO sysPostDTO) {
        return Wrappers.<SysPost>lambdaQuery()
                .eq(StringUtils.hasText(sysPostDTO.getPostCode()), SysPost::getPostCode, sysPostDTO.getPostCode())
                .eq(StringUtils.hasText(sysPostDTO.getPostName()), SysPost::getPostName, sysPostDTO.getPostName())
                .eq(Objects.nonNull(sysPostDTO.getStatus()), SysPost::getStatus, sysPostDTO.getStatus())
                .orderByAsc(SysPost::getOrderNum);
    }

    @Override
    public boolean checkPostNameUnique(SysPostDTO sysPostDTO) {
        return baseMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostName, sysPostDTO.getPostName())
                .ne(Objects.nonNull(sysPostDTO.getPostId()), SysPost::getPostId, sysPostDTO.getPostId()));
    }

    @Override
    public boolean checkPostCodeUnique(SysPostDTO sysPostDTO) {
        return baseMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostCode, sysPostDTO.getPostCode())
                .ne(Objects.nonNull(sysPostDTO.getPostId()), SysPost::getPostId, sysPostDTO.getPostId()));
    }

    @Override
    public boolean checkPostExitUser(SysPostDTO sysPostDTO) {
        return userPostLinkMapper.exists(new LambdaQueryWrapper<UserPostLink>()
                .eq(UserPostLink::getPostId, sysPostDTO.getPostId()));
    }

    @Override
    public boolean insertOne(SysPostDTO sysPostDTO) {
        SysPost sysPost = MapstructUtil.convert(sysPostDTO, SysPost.class);
        return baseMapper.insert(sysPost) > 0;
    }

    @Override
    public boolean updateOne(SysPostDTO sysPostDTO) {
        SysPost sysPost = MapstructUtil.convert(sysPostDTO, SysPost.class);
        return baseMapper.updateById(sysPost) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




