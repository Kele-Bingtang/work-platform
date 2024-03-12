package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysDictTypeMapper;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDTO;
import cn.youngkbt.uac.sys.model.po.SysDictType;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVO;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import cn.youngkbt.uac.sys.service.SysDictTypeService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dict_type(字典类型表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    private final SysDictDataService sysDictDataService;

    @Override
    public SysDictTypeVO listById(Long id) {
        SysDictType sysDictType = baseMapper.selectById(id);
        Assert.nonNull(sysDictType, "字典不存在");
        return MapstructUtil.convert(sysDictType, SysDictTypeVO.class);
    }

    @Override
    public List<SysDictTypeVO> queryListWithPage(SysDictTypeDTO sysDictTypeDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.<SysDictType>lambdaQuery()
                .eq(StringUtils.hasText(sysDictTypeDto.getDictName()), SysDictType::getDictName, sysDictTypeDto.getDictName())
                .eq(StringUtils.hasText(sysDictTypeDto.getDictCode()), SysDictType::getDictCode, sysDictTypeDto.getDictCode())
                .eq(StringUtils.hasText(sysDictTypeDto.getAppId()), SysDictType::getAppId, sysDictTypeDto.getAppId())
                .orderByAsc(SysDictType::getDictId);

        List<SysDictType> sysDictTypeList;
        if (Objects.isNull(pageQuery)) {
            sysDictTypeList = baseMapper.selectList(wrapper);
        } else {
            sysDictTypeList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysDictTypeList, SysDictTypeVO.class);
    }

    @Override
    public boolean insertOne(SysDictTypeDTO sysDictTypeDto) {
        SysDictType sysDictType = MapstructUtil.convert(sysDictTypeDto, SysDictType.class);
        return baseMapper.insert(sysDictType) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOne(SysDictTypeDTO sysDictTypeDto) {
        SysDictType newSysDictType = MapstructUtil.convert(sysDictTypeDto, SysDictType.class);
        // 同步更新 dictData d的 dictCode
        SysDictType oldDictType = baseMapper.selectById(sysDictTypeDto.getId());
        sysDictDataService.updateDictCode(oldDictType.getDictCode(), newSysDictType.getDictCode());
        return baseMapper.updateById(newSysDictType) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




