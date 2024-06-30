package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.mapper.DataSourceMapper;
import cn.youngkbt.ag.system.model.dto.DataSourceDTO;
import cn.youngkbt.ag.system.model.po.DataSource;
import cn.youngkbt.ag.system.model.vo.DataSourceVO;
import cn.youngkbt.ag.system.service.DataSourceService;
import cn.youngkbt.core.exception.ServerException;
import cn.youngkbt.datasource.helper.DataSourceHelper;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author admin
 * @date 2024-06-30 21:00:59
 * @note 针对表「t_data_source（数据源配置表）」的数据库操作 Service 实现
 */
@Service
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Override
    public TablePage<DataSourceVO> listPage(DataSourceDTO dataSourceDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<DataSource> dataSourceLambdaQueryWrapper = buildQueryWrapper(dataSourceDTO);
        Page<DataSource> dataSourcePage = baseMapper.selectPage(pageQuery.buildPage(), dataSourceLambdaQueryWrapper);
        TablePage<DataSourceVO> dataSourceVOTablePage = TablePage.build(dataSourcePage, DataSourceVO.class);
        dataSourceVOTablePage.getList().forEach(dataSource -> {
            dataSource.setDataSourceTypeDriveClass(Arrays.asList(dataSource.getDataSourceType(), dataSource.getDriverClassName()));
        });
        return dataSourceVOTablePage;
    }

    @Override
    public List<DataSourceVO> listSelect(String teamId) {
        List<DataSource> dataSourceList = baseMapper.selectList(Wrappers.<DataSource>lambdaQuery()
                .select(DataSource::getDataSourceId, DataSource::getDataSourceName)
                .eq(DataSource::getTeamId, teamId));

        return MapstructUtil.convert(dataSourceList, DataSourceVO.class);
    }

    private LambdaQueryWrapper<DataSource> buildQueryWrapper(DataSourceDTO dataSourceDTO) {
        return Wrappers.<DataSource>lambdaQuery()
                .eq(StringUtil.hasText(dataSourceDTO.getTeamId()), DataSource::getTeamId, dataSourceDTO.getTeamId())
                .eq(StringUtil.hasText(dataSourceDTO.getDataSourceName()), DataSource::getDataSourceName, dataSourceDTO.getDataSourceName())
                .eq(StringUtil.hasText(dataSourceDTO.getDataSourceType()), DataSource::getDataSourceType, dataSourceDTO.getDataSourceType())
                .eq(Objects.nonNull(dataSourceDTO.getStatus()), DataSource::getStatus, dataSourceDTO.getStatus())
                .orderByDesc(DataSource::getCreateTime);
    }

    @Override
    public Boolean addDataSource(DataSourceDTO dataSourceDTO) {
        DataSource dateSource = MapstructUtil.convert(dataSourceDTO, DataSource.class);
        if (Objects.isNull(dateSource.getDataSourceType())) {
            try {
                URI uri = new URI(dateSource.getJdbcUrl().replace("jdbc:", ""));
                dateSource.setDataSourceType(uri.getScheme());
            } catch (URISyntaxException e) {
                throw new ServerException("JDBC URL 格式不正确");
            }
        }
        String driverClass = DataSourceHelper.getDriverClass(dataSourceDTO.getDataSourceType());
        if (StringUtil.hasEmpty(dataSourceDTO.getDriverClassName())) {
            dateSource.setDriverClassName(driverClass);
        }

        return baseMapper.insert(dateSource) > 0;
    }

    @Override
    public Boolean editDataSource(DataSourceDTO dataSourceDTO) {
        DataSource dateSource = MapstructUtil.convert(dataSourceDTO, DataSource.class);
        if (Objects.isNull(dateSource.getDataSourceType())) {
            try {
                URI uri = new URI(dateSource.getJdbcUrl().replace("jdbc:", ""));
                dateSource.setDataSourceType(uri.getScheme());
            } catch (URISyntaxException e) {
                throw new ServerException("JDBC URL 格式不正确");
            }
        }
        String driverClass = DataSourceHelper.getDriverClass(dataSourceDTO.getDataSourceType());
        if (StringUtil.hasEmpty(dataSourceDTO.getDriverClassName())) {
            dateSource.setDriverClassName(driverClass);
        }

        return baseMapper.updateById(dateSource) > 0;
    }

    @Override
    public Boolean removeDataSource(String dataSourceId) {
        return baseMapper.delete(Wrappers.<DataSource>lambdaQuery()
                .eq(DataSource::getDataSourceId, dataSourceId)) > 0;
    }

    @Override
    public boolean checkDataSourceNameUnique(DataSourceDTO dataSourceDTO) {
        return baseMapper.exists(Wrappers.<DataSource>lambdaQuery()
                .eq(DataSource::getDataSourceName, dataSourceDTO.getDataSourceName())
                .ne(Objects.nonNull(dataSourceDTO.getId()), DataSource::getId, dataSourceDTO.getId()));
    }

    @Override
    public boolean testConnect(DataSourceDTO dataSourceDTO) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(dataSourceDTO.getUsername());
        hikariDataSource.setPassword(dataSourceDTO.getPassword());
        hikariDataSource.setJdbcUrl(dataSourceDTO.getJdbcUrl());
        if (StringUtil.hasEmpty(dataSourceDTO.getDriverClassName())) {
            hikariDataSource.setDriverClassName(DataSourceHelper.getDriverClass(dataSourceDTO.getDataSourceType()));
        }
        return DataSourceHelper.connect(hikariDataSource);
    }
}


