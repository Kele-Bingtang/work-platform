package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysDeptMapper;
import cn.youngkbt.uac.sys.model.dto.SysDeptDto;
import cn.youngkbt.uac.sys.model.po.SysDept;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import cn.youngkbt.uac.sys.service.SysDeptService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dept(部门表)】的数据库操作Service实现
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public SysDeptVo queryById(Long id) {
        SysDept sysDept = baseMapper.selectById(id);
        Assert.nonNull(sysDept, "部门不存在");
        return MapstructUtil.convert(sysDept, SysDeptVo.class);
    }

    @Override
    public List<SysDeptVo> queryListWithPage(SysDeptDto sysDeptDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDept> wrapper = Wrappers.<SysDept>lambdaQuery()
                .eq(StringUtils.hasText(sysDeptDto.getDeptId()), SysDept::getDeptId, sysDeptDto.getDeptId())
                .eq(StringUtils.hasText(sysDeptDto.getParentId()), SysDept::getParentId, sysDeptDto.getParentId())
                .eq(StringUtils.hasText(sysDeptDto.getDeptName()), SysDept::getDeptName, sysDeptDto.getDeptName())
                .eq(Objects.nonNull(sysDeptDto.getStatus()), SysDept::getStatus, sysDeptDto.getStatus());

        List<SysDept> sysDeptList;
        if (Objects.isNull(pageQuery)) {
            sysDeptList = baseMapper.selectList(wrapper);
        } else {
            sysDeptList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysDeptList, SysDeptVo.class);
    }

    @Override
    public Boolean insertOne(SysDeptDto sysDeptDto) {
        SysDept sysDept = MapstructUtil.convert(sysDeptDto, SysDept.class);
        return baseMapper.insert(sysDept) > 0;
    }

    @Override
    public Boolean updateOne(SysDeptDto sysDeptDto) {
        SysDept sysDept = MapstructUtil.convert(sysDeptDto, SysDept.class);
        return baseMapper.updateById(sysDept) > 0;
    }

    @Override
    public Boolean removeOne(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




