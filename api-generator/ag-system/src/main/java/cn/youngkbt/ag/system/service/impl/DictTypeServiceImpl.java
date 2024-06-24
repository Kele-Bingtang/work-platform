package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.constant.CacheNameConstant;
import cn.youngkbt.ag.system.mapper.DictTypeMapper;
import cn.youngkbt.ag.system.model.dto.DictTypeDTO;
import cn.youngkbt.ag.system.model.po.DictType;
import cn.youngkbt.ag.system.model.vo.DictTypeVO;
import cn.youngkbt.ag.system.service.DictDataService;
import cn.youngkbt.ag.system.service.DictTypeService;
import cn.youngkbt.cache.helper.CacheHelper;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_dict_type(字典类型表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    private final DictDataService dictDataService;

    @Override
    public List<DictTypeVO> listAll(DictTypeDTO dictTypeDTO) {
        LambdaQueryWrapper<DictType> wrapper = buildQueryWrapper(dictTypeDTO);
        List<DictType> dictTypeList = baseMapper.selectList(wrapper);

        return MapstructUtil.convert(dictTypeList, DictTypeVO.class);
    }

    @Override
    public TablePage<DictTypeVO> listPage(DictTypeDTO dictTypeDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<DictType> wrapper = buildQueryWrapper(dictTypeDTO);
        Page<DictType> dictTypePage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(dictTypePage, DictTypeVO.class);
    }

    private LambdaQueryWrapper<DictType> buildQueryWrapper(DictTypeDTO dictTypeDTO) {
        return Wrappers.<DictType>lambdaQuery()
                .eq(StringUtil.hasText(dictTypeDTO.getDictName()), DictType::getDictName, dictTypeDTO.getDictName())
                .eq(StringUtil.hasText(dictTypeDTO.getDictCode()), DictType::getDictCode, dictTypeDTO.getDictCode())
                .orderByAsc(DictType::getDictId);
    }

    @Override
    public boolean insertOne(DictTypeDTO dictTypeDTO) {
        DictType dictType = MapstructUtil.convert(dictTypeDTO, DictType.class);
        return baseMapper.insert(dictType) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOne(DictTypeDTO dictTypeDTO) {
        DictType newDictType = MapstructUtil.convert(dictTypeDTO, DictType.class);
        // 同步更新 dictData 的 dictCode
        DictType oldDictType = baseMapper.selectById(dictTypeDTO.getId());
        dictDataService.updateDictCode(oldDictType.getDictCode(), newDictType.getDictCode());
        return baseMapper.updateById(newDictType) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        List<DictType> dictTypeList = baseMapper.selectBatchIds(ids);

        boolean result = baseMapper.deleteBatchIds(ids) > 0;

        for (DictType DictType : dictTypeList) {
            // 删除存储的数据
            CacheHelper.evict(CacheNameConstant.SYS_DICT, DictType.getDictCode());
        }

        return result;
    }

    @Override
    public boolean checkDictCodeUnique(DictTypeDTO dictTypeDTO) {
        return baseMapper.exists(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getDictCode, dictTypeDTO.getDictCode())
                .ne(Objects.nonNull(dictTypeDTO.getId()), DictType::getId, dictTypeDTO.getId()));
    }
}




