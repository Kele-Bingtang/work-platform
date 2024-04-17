package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.SysDictTypeMapper;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDTO;
import cn.youngkbt.uac.sys.model.po.SysDictType;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVO;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import cn.youngkbt.uac.sys.service.SysDictTypeService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
    public List<SysDictTypeVO> queryList(SysDictTypeDTO sysDictTypeDTO) {
        LambdaQueryWrapper<SysDictType> wrapper = buildQueryWrapper(sysDictTypeDTO);
        List<SysDictType> sysDictTypeList = baseMapper.selectList(wrapper);

        return MapstructUtil.convert(sysDictTypeList, SysDictTypeVO.class);
    }

    @Override
    public TablePage<SysDictTypeVO> listPage(SysDictTypeDTO sysDictTypeDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictType> wrapper = buildQueryWrapper(sysDictTypeDTO);
        Page<SysDictType> sysDictTypePage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysDictTypePage, SysDictTypeVO.class);
    }

    private LambdaQueryWrapper<SysDictType> buildQueryWrapper(SysDictTypeDTO sysDictTypeDTO) {
        return Wrappers.<SysDictType>lambdaQuery()
                .eq(StringUtils.hasText(sysDictTypeDTO.getDictName()), SysDictType::getDictName, sysDictTypeDTO.getDictName())
                .eq(StringUtils.hasText(sysDictTypeDTO.getDictCode()), SysDictType::getDictCode, sysDictTypeDTO.getDictCode())
                .eq(StringUtils.hasText(sysDictTypeDTO.getAppId()), SysDictType::getAppId, sysDictTypeDTO.getAppId())
                .orderByAsc(SysDictType::getDictId);
    }

    @Override
    public boolean insertOne(SysDictTypeDTO sysDictTypeDTO) {
        SysDictType sysDictType = MapstructUtil.convert(sysDictTypeDTO, SysDictType.class);
        return baseMapper.insert(sysDictType) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOne(SysDictTypeDTO sysDictTypeDTO) {
        SysDictType newSysDictType = MapstructUtil.convert(sysDictTypeDTO, SysDictType.class);
        // 同步更新 dictData 的 dictCode
        SysDictType oldDictType = baseMapper.selectById(sysDictTypeDTO.getId());
        sysDictDataService.updateDictCode(oldDictType.getDictCode(), newSysDictType.getDictCode());
        return baseMapper.updateById(newSysDictType) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean checkAppExitDictType(List<String> appIds) {
        return baseMapper.exists(Wrappers.<SysDictType>lambdaQuery()
                .in(SysDictType::getAppId, appIds));
    }

    @Override
    public boolean checkDictCodeUnique(SysDictTypeDTO sysDictTypeDTO) {
        return baseMapper.exists(Wrappers.<SysDictType>lambdaQuery()
                .eq(SysDictType::getDictCode, sysDictTypeDTO.getDictCode())
                .ne(StringUtils.hasText(sysDictTypeDTO.getDictId()), SysDictType::getDictId, sysDictTypeDTO.getDictId()));
    }
}




