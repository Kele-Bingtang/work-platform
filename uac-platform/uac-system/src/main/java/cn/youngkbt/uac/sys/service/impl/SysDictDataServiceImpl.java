package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysDictDataMapper;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDTO;
import cn.youngkbt.uac.sys.model.po.SysDictData;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVO;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public SysDictDataVO listById(Long id) {
        SysDictData sysDictData = baseMapper.selectById(id);
        Assert.nonNull(sysDictData, "数据不存在");
        return MapstructUtil.convert(sysDictData, SysDictDataVO.class);
    }

    @Override
    public List<SysDictDataVO> listWithPage(SysDictDataDTO sysDictDataDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.<SysDictData>lambdaQuery()
                .eq(Objects.nonNull(sysDictDataDto.getDictSort()), SysDictData::getDictSort, sysDictDataDto.getDictSort())
                .eq(StringUtils.hasText(sysDictDataDto.getDictLabel()), SysDictData::getDictLabel, sysDictDataDto.getDictCode())
                .eq(StringUtils.hasText(sysDictDataDto.getAppId()), SysDictData::getAppId, sysDictDataDto.getAppId())
                .eq(StringUtils.hasText(sysDictDataDto.getDictCode()), SysDictData::getDictCode, sysDictDataDto.getDictCode())
                .orderByAsc(SysDictData::getDictSort);

        List<SysDictData> sysDictDataDtoList;
        if (Objects.isNull(pageQuery)) {
            sysDictDataDtoList = baseMapper.selectList(wrapper);
        } else {
            sysDictDataDtoList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysDictDataDtoList, SysDictDataVO.class);
    }

    @Override
    public boolean insertOne(SysDictDataDTO sysDictDataDto) {
        SysDictData sysDictData = MapstructUtil.convert(sysDictDataDto, SysDictData.class);
        return baseMapper.insert(sysDictData) > 0;
    }

    @Override
    @Transactional
    public boolean updateOne(SysDictDataDTO sysDictDataDto) {
        SysDictData sysDictData = MapstructUtil.convert(sysDictDataDto, SysDictData.class);
        return baseMapper.updateById(sysDictData) > 0;
    }

    @Override
    public boolean updateDictCode(String oldDictCode, String newDictCode) {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictCode(newDictCode);
        return baseMapper.update(sysDictData, Wrappers.<SysDictData>lambdaUpdate()
                .eq(SysDictData::getDictCode, oldDictCode)) > 1;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




