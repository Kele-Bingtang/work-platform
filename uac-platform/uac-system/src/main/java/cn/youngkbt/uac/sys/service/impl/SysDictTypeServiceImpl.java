package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysDictTypeMapper;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDto;
import cn.youngkbt.uac.sys.model.po.SysDictType;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVo;
import cn.youngkbt.uac.sys.service.SysDictTypeService;
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
 * @note 针对表【t_sys_dict_type(字典类型表)】的数据库操作Service实现
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public SysDictTypeVo queryById(Long id) {
        SysDictType sysDictType = baseMapper.selectById(id);
        Assert.nonNull(sysDictType, "字典不存在");
        return MapstructUtil.convert(sysDictType, SysDictTypeVo.class);
    }

    @Override
    public List<SysDictTypeVo> queryListWithPage(SysDictTypeDto sysDictTypeDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.<SysDictType>lambdaQuery()
                .eq(StringUtils.hasText(sysDictTypeDto.getDictName()), SysDictType::getDictName, sysDictTypeDto.getDictName())
                .eq(StringUtils.hasText(sysDictTypeDto.getDictCode()), SysDictType::getDictCode, sysDictTypeDto.getDictCode())
                .orderByAsc(SysDictType::getDictId);

        List<SysDictType> sysDictTypeList;
        if (Objects.isNull(pageQuery)) {
            sysDictTypeList = baseMapper.selectList(wrapper);
        } else {
            sysDictTypeList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysDictTypeList, SysDictTypeVo.class);
    }

    @Override
    public Boolean insertOne(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = MapstructUtil.convert(sysDictTypeDto, SysDictType.class);
        return baseMapper.insert(sysDictType) > 0;
    }

    @Override
    public Boolean updateOne(SysDictTypeDto sysDictTypeDto) {
        SysDictType sysDictType = MapstructUtil.convert(sysDictTypeDto, SysDictType.class);
        return baseMapper.updateById(sysDictType) > 0;
    }

    @Override
    public Boolean removeOne(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




