package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysDictDataMapper;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDto;
import cn.youngkbt.uac.sys.model.po.SysDictData;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVo;
import cn.youngkbt.uac.sys.service.SysDictDataService;
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
 * @note 针对表【t_sys_dict_data(字典数据表)】的数据库操作Service实现
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public SysDictDataVo queryById(Long id) {
        SysDictData sysDictData = baseMapper.selectById(id);
        Assert.nonNull(sysDictData, "数据不存在");
        return MapstructUtil.convert(sysDictData, SysDictDataVo.class);
    }

    @Override
    public List<SysDictDataVo> queryListWithPage(SysDictDataDto sysDictDataDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.<SysDictData>lambdaQuery()
                .eq(Objects.nonNull(sysDictDataDto.getDictSort()), SysDictData::getDictSort, sysDictDataDto.getDictSort())
                .eq(StringUtils.hasText(sysDictDataDto.getDictCode()), SysDictData::getDictCode, sysDictDataDto.getDictCode())
                .orderByAsc(SysDictData::getDictSort);

        List<SysDictData> sysDictDataDtoList;
        if (Objects.isNull(pageQuery)) {
            sysDictDataDtoList = baseMapper.selectList(wrapper);
        } else {
            sysDictDataDtoList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysDictDataDtoList, SysDictDataVo.class);
    }

    @Override
    public Boolean insertOne(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = MapstructUtil.convert(sysDictDataDto, SysDictData.class);
        return baseMapper.insert(sysDictData) > 0;
    }

    @Override
    public Boolean updateOne(SysDictDataDto sysDictDataDto) {
        SysDictData sysDictData = MapstructUtil.convert(sysDictDataDto, SysDictData.class);
        return baseMapper.updateById(sysDictData) > 0;
    }

    @Override
    public Boolean removeOne(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




