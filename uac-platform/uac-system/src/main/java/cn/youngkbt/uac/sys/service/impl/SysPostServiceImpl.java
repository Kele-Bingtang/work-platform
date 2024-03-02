package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysPostMapper;
import cn.youngkbt.uac.sys.mapper.UserPostLinkMapper;
import cn.youngkbt.uac.sys.model.dto.SysPostDto;
import cn.youngkbt.uac.sys.model.po.SysPost;
import cn.youngkbt.uac.sys.model.po.UserPostLink;
import cn.youngkbt.uac.sys.model.vo.SysPostVo;
import cn.youngkbt.uac.sys.service.SysPostService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    public SysPostVo queryById(Long id) {
        SysPost sysPost = baseMapper.selectById(id);
        Assert.nonNull(sysPost, "岗位不存在");
        return MapstructUtil.convert(sysPost, SysPostVo.class);
    }

    @Override
    public List<SysPostVo> queryListWithPage(SysPostDto sysPostDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysPost> wrapper = Wrappers.<SysPost>lambdaQuery()
                .eq(StringUtils.hasText(sysPostDto.getPostCode()), SysPost::getPostCode, sysPostDto.getPostCode())
                .eq(StringUtils.hasText(sysPostDto.getPostName()), SysPost::getPostName, sysPostDto.getPostName())
                .eq(Objects.nonNull(sysPostDto.getStatus()), SysPost::getStatus, sysPostDto.getStatus())
                .orderByAsc(SysPost::getOrderNum);

        List<SysPost> sysPostList;
        if (Objects.isNull(pageQuery)) {
            sysPostList = baseMapper.selectList(wrapper);
        } else {
            sysPostList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysPostList, SysPostVo.class);
    }

    @Override
    public boolean checkPostNameUnique(SysPostDto sysPostDto) {
        return baseMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostName, sysPostDto.getPostName())
                .ne(Objects.nonNull(sysPostDto.getPostId()), SysPost::getPostId, sysPostDto.getPostId()));
    }

    @Override
    public boolean checkPostCodeUnique(SysPostDto sysPostDto) {
        return baseMapper.exists(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getPostCode, sysPostDto.getPostCode())
                .ne(Objects.nonNull(sysPostDto.getPostId()), SysPost::getPostId, sysPostDto.getPostId()));
    }

    @Override
    public boolean checkPostExitUser(SysPostDto sysPostDto) {
        return userPostLinkMapper.exists(new LambdaQueryWrapper<UserPostLink>()
                .eq(UserPostLink::getPostId, sysPostDto.getPostId()));
    }

    @Override
    public boolean insertOne(SysPostDto sysPostDto) {
        SysPost sysPost = MapstructUtil.convert(sysPostDto, SysPost.class);
        return baseMapper.insert(sysPost) > 0;
    }

    @Override
    public boolean updateOne(SysPostDto sysPostDto) {
        SysPost sysPost = MapstructUtil.convert(sysPostDto, SysPost.class);
        return baseMapper.updateById(sysPost) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




