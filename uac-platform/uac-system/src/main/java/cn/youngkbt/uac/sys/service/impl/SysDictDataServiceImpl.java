package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.SysDictDataMapper;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDTO;
import cn.youngkbt.uac.sys.model.po.SysDictData;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVO;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import cn.youngkbt.uac.sys.utils.TreeBuildUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
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
    public List<SysDictDataVO> queryList(SysDictDataDTO sysDictDataDTO) {
        LambdaQueryWrapper<SysDictData> wrapper = buildQueryWrapper(sysDictDataDTO);
        List<SysDictData> sysDictData = baseMapper.selectList(wrapper);
        
        
        return MapstructUtil.convert(sysDictData, SysDictDataVO.class);
    }

    @Override
    public TablePage<SysDictDataVO> listPage(SysDictDataDTO sysDictDataDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictData> wrapper = buildQueryWrapper(sysDictDataDTO);
        Page<SysDictData> sysDictDataPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);
        
        return TablePage.build(sysDictDataPage, SysDictDataVO.class);
    }
    
    private LambdaQueryWrapper<SysDictData> buildQueryWrapper(SysDictDataDTO sysDictDataDTO) {
        return Wrappers.<SysDictData>lambdaQuery()
                .eq(Objects.nonNull(sysDictDataDTO.getDictSort()), SysDictData::getDictSort, sysDictDataDTO.getDictSort())
                .eq(StringUtils.hasText(sysDictDataDTO.getDictLabel()), SysDictData::getDictLabel, sysDictDataDTO.getDictCode())
                .eq(StringUtils.hasText(sysDictDataDTO.getAppId()), SysDictData::getAppId, sysDictDataDTO.getAppId())
                .eq(StringUtils.hasText(sysDictDataDTO.getDictCode()), SysDictData::getDictCode, sysDictDataDTO.getDictCode())
                .orderByAsc(SysDictData::getDictSort);
    }

    @Override
    public boolean insertOne(SysDictDataDTO sysDictDataDTO) {
        SysDictData sysDictData = MapstructUtil.convert(sysDictDataDTO, SysDictData.class);
        
        if(StringUtil.hasText(sysDictDataDTO.getParentId())) {
            return baseMapper.insert(sysDictData) > 0;
        }

        sysDictDataDTO.setParentId("0");
        return baseMapper.insert(sysDictData) > 0;
    }

    @Override
    @Transactional
    public boolean updateOne(SysDictDataDTO sysDictDataDTO) {
        SysDictData sysDictData = MapstructUtil.convert(sysDictDataDTO, SysDictData.class);
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

    @Override
    public List<Tree<String>> listDataTreeList(SysDictDataDTO sysDictDataDTO) {
        LambdaQueryWrapper<SysDictData> wrapper = buildQueryWrapper(sysDictDataDTO);
        List<SysDictData> sysDictData = baseMapper.selectList(wrapper);

        if (CollUtil.isEmpty(sysDictData)) {
            return Collections.emptyList();
        }

        return TreeBuildUtil.build(sysDictData, "0", TreeNodeConfig.DEFAULT_CONFIG.setIdKey("value").setNameKey("label"), (treeNode, tree) ->
                tree.setId(treeNode.getDataId())
                        .setParentId(treeNode.getParentId())
                        .setName(treeNode.getDictLabel())
                        .setWeight(treeNode.getDictSort()));
    }

    @Override
    public List<SysDictDataVO> listDataTreeTable(SysDictDataDTO sysDictDataDTO) {
        LambdaQueryWrapper<SysDictData> wrapper = buildQueryWrapper(sysDictDataDTO);
        List<SysDictData> sysDictData = baseMapper.selectList(wrapper);
        List<SysDictDataVO> sysDictDataVOList = MapstructUtil.convert(sysDictData, SysDictDataVO.class);

        return TreeBuildUtil.build(sysDictDataVOList, SysDictDataVO::getDataId);
    }

    @Override
    public boolean checkDictDataUnique(SysDictDataDTO sysDictDataDTO) {
        return baseMapper.exists(Wrappers.<SysDictData>lambdaQuery()
                .eq(SysDictData::getDictValue, sysDictDataDTO.getDictValue())
                .eq(SysDictData::getParentId, sysDictDataDTO.getParentId())
                .ne(Objects.nonNull(sysDictDataDTO.getDataId()), SysDictData::getDataId, sysDictDataDTO.getDataId()));
    }
}




